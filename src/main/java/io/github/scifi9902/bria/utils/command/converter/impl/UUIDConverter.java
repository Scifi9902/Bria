package io.github.scifi9902.bria.utils.command.converter.impl;

import io.github.scifi9902.bria.utils.command.converter.IConverter;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UUIDConverter implements IConverter<UUID> {

    @Override
    public UUID fromString(CommandSender sender, String string) {
        return UUID.fromString(string);
    }

    @Override
    public Class<UUID> getType() {
        return UUID.class;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Collections.emptyList();
    }
}
