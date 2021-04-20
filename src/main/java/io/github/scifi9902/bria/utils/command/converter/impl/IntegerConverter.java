package io.github.scifi9902.bria.utils.command.converter.impl;

import io.github.scifi9902.bria.utils.command.converter.IConverter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class IntegerConverter implements IConverter<Integer> {

    @Override
    public Integer fromString(CommandSender sender, String string) {
        try {
          return Integer.parseInt(string);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Collections.emptyList();
    }
}
