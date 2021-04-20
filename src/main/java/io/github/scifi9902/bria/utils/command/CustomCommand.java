package io.github.scifi9902.bria.utils.command;

import io.github.scifi9902.bria.utils.CC;
import io.github.scifi9902.bria.utils.command.annotation.Command;
import io.github.scifi9902.bria.utils.command.annotation.Optional;
import io.github.scifi9902.bria.utils.command.converter.IConverter;
import io.github.scifi9902.bria.utils.command.data.CommandData;
import io.github.scifi9902.bria.utils.command.data.SubCommandData;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
public class CustomCommand extends org.bukkit.command.Command implements TabCompleter {

    private final Command command;

    private final CommandData commandData;

    private final Map<Integer, Parameter> parameterMap = new ConcurrentHashMap<>();

    public CustomCommand(CommandData commandData) {
        super(commandData.getCommand().label());
        this.commandData = commandData;
        this.command = commandData.getCommand();
        if (command.aliases().length > 0) {
            this.setAliases(Arrays.asList(command.aliases()));
        }
    }

    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String label, String[] strings) {
        String[] args;
        Method method;
        Object obj;
        String permission;

        String subName = null;

        if (strings.length >= 1 && !this.commandData.getSubCommands().isEmpty() && this.commandData.getSubCommands().stream().anyMatch(subCommand -> subCommand.getSubCommand().label().equalsIgnoreCase(strings[0]) || Arrays.stream(subCommand.getSubCommand().aliases()).anyMatch(s -> s.equalsIgnoreCase(strings[0])))) {
            args = Arrays.copyOfRange(strings, 1, strings.length);
            SubCommandData subCommandData = this.commandData.getSubCommands().stream().filter(subCommandData1 -> subCommandData1.getSubCommand().label().equalsIgnoreCase(strings[0]) || Arrays.stream(subCommandData1.getSubCommand().aliases()).anyMatch(s -> s.equalsIgnoreCase(strings[0]))).findFirst().orElse(null);

            if (subCommandData != null) {
                obj = subCommandData.getObject();
                method = subCommandData.getMethod();
                subName = subCommandData.getSubCommand().label();
                permission = subCommandData.getSubCommand().permission();
            }

            else {
                obj = commandData.getObject();
                method = commandData.getMethod();
                permission = command.permission();
            }

        }

        else {
            obj = commandData.getObject();
            method = commandData.getMethod();
            args = strings;
            permission = command.permission();
        }


        if (method.getParameters()[0].getType().equals(Player.class) && !(sender instanceof Player)) {
            sender.sendMessage(CC.chat("&cOnly players may execute this command"));
            return true;
        }

        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(CC.chat("&cNo permission."));
            return true;
        }

        Parameter[] parameters = Arrays.copyOfRange(method.getParameters(), 1, method.getParameters().length);

       /* if (args.length < parameters.length) {
            if (subName == null) {
                sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + Arrays.stream(parameters).map(parameter1 -> "<" + (parameter1.isAnnotationPresent(io.github.scifi9902.bria.utils.command.annotation.Parameter.class) ? parameter1.getAnnotation(io.github.scifi9902.bria.utils.command.annotation.Parameter.class).value()  : parameter1.getName()) + ">").collect(Collectors.joining(" ")));
            }
            else {
                sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + subName + " "+ Arrays.stream(parameters).map(parameter1 -> "<" + (parameter1.isAnnotationPresent(io.github.scifi9902.bria.utils.command.annotation.Parameter.class) ? parameter1.getAnnotation(io.github.scifi9902.bria.utils.command.annotation.Parameter.class).value()  : parameter1.getName()) + ">").collect(Collectors.joining(" ")));
            }
            return true;
        }*/

        Object[] objects = new Object[parameters.length];

        if (parameters.length > 0 && !parameters[0].getType().isArray()) {

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Optional optional = parameter.getAnnotation(Optional.class);

                parameterMap.put(i, parameter);

                if (i >= args.length && optional == null) {
                    if (subName == null) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + Arrays.stream(parameters).map(parameter1 -> "<" + (parameter1.isAnnotationPresent(io.github.scifi9902.bria.utils.command.annotation.Parameter.class) ? parameter1.getAnnotation(io.github.scifi9902.bria.utils.command.annotation.Parameter.class).value()  : parameter1.getName()) + ">").collect(Collectors.joining(" ")));
                    }

                    else {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " " + subName + " "+ Arrays.stream(parameters).map(parameter1 -> "<" + (parameter1.isAnnotationPresent(io.github.scifi9902.bria.utils.command.annotation.Parameter.class) ? parameter1.getAnnotation(io.github.scifi9902.bria.utils.command.annotation.Parameter.class).value()  : parameter1.getName()) + ">").collect(Collectors.joining(" ")));
                    }

                    return true;
                }

                Object converted = null;

                IConverter<?> converter = CommandHandler.findConverter(parameter.getType());

                if ((parameters.length == i + 1) && parameters[parameters.length - 1].getType().equals(String.class) && args.length >= i) {
                    String[] appendArgs = Arrays.copyOfRange(args,i, args.length);
                    for (String append : appendArgs) {
                        stringBuilder.append(append).append(" ");
                    }
                } else {
                    if (converter != null) {
                        converted = converter.fromString(sender, i >= args.length ? optional.value().replace("self", sender.getName()) : args[i]);
                    } else {
                        throw new NullPointerException("No Converter Found");
                    }
                }
                objects[i] = stringBuilder.toString().isEmpty() ? converted : stringBuilder.toString().trim();
            }

            objects = ArrayUtils.add(objects, 0, method.getParameters()[0].getType().cast(sender));
            method.invoke(obj, objects);
        }

        else if (parameters.length == 1 && parameters[0].getType().isArray()) {
            method.invoke(obj, sender, args);
        }

        else {
            method.invoke(obj, sender);
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] strings) {

        String[] args;
        Method method;

        if (strings.length >= 1 && !this.commandData.getSubCommands().isEmpty() && this.commandData.getSubCommands().stream().anyMatch(subCommand -> !subCommand.getSubCommand().label().isEmpty() && subCommand.getSubCommand().label().equalsIgnoreCase(strings[1]) || Arrays.stream(subCommand.getSubCommand().aliases()).anyMatch(s -> s.equalsIgnoreCase(strings[1])))) {
            args = Arrays.copyOfRange(strings, 1, strings.length);
            SubCommandData subCommandData = this.commandData.getSubCommands().stream().filter(subCommandData1 -> subCommandData1.getSubCommand().label().equalsIgnoreCase(strings[1]) || Arrays.stream(subCommandData1.getSubCommand().aliases()).anyMatch(s -> s.equalsIgnoreCase(strings[1]))).findFirst().orElse(null);

            if (subCommandData != null) {
                method = subCommandData.getMethod();
            }

            else {
                method = commandData.getMethod();
            }

        }

        else {
            method = commandData.getMethod();
            args = strings;
        }

        final IConverter<?> converter = method.getParameters().length > args.length ? CommandHandler.findConverter(method.getParameters()[args.length].getType()) : CommandHandler.findConverter(String.class);
        return converter == null ? super.tabComplete(commandSender, label, args) : converter.tabComplete(commandSender);
    }
}