package io.github.scifi9902.bria.utils.command.converter;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface IConverter<T> {

    T fromString(CommandSender sender, String string);

    Class<T> getType();

    List<String> tabComplete(CommandSender sender);

}
