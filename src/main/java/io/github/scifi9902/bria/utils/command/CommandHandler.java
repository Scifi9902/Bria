package io.github.scifi9902.bria.utils.command;

import com.google.common.collect.Lists;
import io.github.scifi9902.bria.utils.command.annotation.Command;
import io.github.scifi9902.bria.utils.command.annotation.SubCommand;
import io.github.scifi9902.bria.utils.command.converter.IConverter;
import io.github.scifi9902.bria.utils.command.data.CommandData;
import io.github.scifi9902.bria.utils.command.data.SubCommandData;
import io.github.scifi9902.bria.utils.command.converter.impl.*;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler {

    private static final List<IConverter> converters = new ArrayList<>();

    private static final List<CustomCommand> customCommands = Lists.newLinkedList();

    private static String fallbackPrefix = "";

    private static CommandMap commandMap;

    private final String permissionMessage;

    @SneakyThrows
    public CommandHandler(String prefix, String permissionMessage) {
        fallbackPrefix = prefix;
        this.permissionMessage = permissionMessage;
        converters.addAll(Arrays.asList(new IntegerConverter(), new StringConverter(), new UUIDConverter(), new LongConverter(), new BooleanConverter(), new PlayerConverter(), new OfflinePlayerConverter()));

        Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        bukkitCommandMap.setAccessible(true);
        commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());


    }

    public void registerConverters(List<IConverter<?>> list) {
        converters.addAll(list);
    }

    public void registerConverter(IConverter<?> converter) {
        converters.add(converter);
    }

    public void registerCommands(Object... object) {
        for (Object obj : object) {
            registerCommand(obj);
        }
    }

    public void registerCommand(Object object) {
        List<Method> methods = Arrays.asList(object.getClass().getMethods());
        List<Method> subCommands = methods.stream().filter(method -> method.getAnnotation(SubCommand.class) != null)
                .collect(Collectors.toList());

        List<Method> commands = methods.stream().filter(method -> method.getAnnotation(Command.class) != null)
                .collect(Collectors.toList());

        commands.forEach(method -> {
            CommandData commandData = new CommandData(object, method);
            CustomCommand customCommand = new CustomCommand(commandData);
            customCommands.add(customCommand);
            commandMap.register(fallbackPrefix, customCommand);

        });

        subCommands.forEach(method -> {
            SubCommandData subCommandData = new SubCommandData(object, method);
            CustomCommand parentCommand = customCommands.stream()
                    .filter(customCommand -> customCommand.getLabel().equalsIgnoreCase(subCommandData.getSubCommand().parent())
                            || Arrays.stream(customCommand.getCommandData().getCommand().aliases())
                            .filter(s -> s.equalsIgnoreCase(subCommandData.getSubCommand().parent()))
                            .findAny().orElse(null) != null).findFirst().orElse(null);

            if (parentCommand == null) {
                System.out.println("Parent command not found");
            } else {
                parentCommand.getCommandData().getSubCommands().add(subCommandData);
            }
        });
    }

    public static IConverter<?> findConverter(Class<?> clazz) {
        return converters.stream().filter(iConverter -> iConverter.getType().equals(clazz)).findAny().orElse(null);
    }

}


