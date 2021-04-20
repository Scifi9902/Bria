package io.github.scifi9902.bria.utils.command.converter.impl;

import io.github.scifi9902.bria.utils.command.converter.IConverter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class LongConverter implements IConverter<Long> {

    @Override
    public Long fromString(CommandSender sender, String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException exception) {
            return -1L;
        }
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Collections.emptyList();
    }
}
