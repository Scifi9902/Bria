package io.github.scifi9902.bria.utils.command.converter.impl;

import io.github.scifi9902.bria.utils.command.converter.IConverter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class StringConverter implements IConverter<String> {

    @Override
    public String fromString(CommandSender sender, String string) {
        return string;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Collections.emptyList();
    }
}
