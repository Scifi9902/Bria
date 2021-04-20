package io.github.scifi9902.bria.utils.command.converter.impl;

import io.github.scifi9902.bria.utils.command.converter.IConverter;
import io.github.scifi9902.bria.utils.command.utils.UUIDUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class OfflinePlayerConverter implements IConverter<OfflinePlayer> {

    @Override
    public OfflinePlayer fromString(CommandSender sender, String string) {
        return UUIDUtils.getOfflinePlayer(string);
    }

    @Override
    public Class<OfflinePlayer> getType() {
        return OfflinePlayer.class;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Collections.emptyList();
    }
}
