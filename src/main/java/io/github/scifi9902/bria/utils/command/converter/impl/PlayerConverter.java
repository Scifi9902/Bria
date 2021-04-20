package io.github.scifi9902.bria.utils.command.converter.impl;

import io.github.scifi9902.bria.utils.command.converter.IConverter;
import io.github.scifi9902.bria.utils.command.utils.UUIDUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerConverter implements IConverter<Player> {

    @Override
    public Player fromString(CommandSender sender, String string) {
        return UUIDUtils.getPlayer(string);
    }

    @Override
    public Class<Player> getType() {
        return Player.class;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName)
                .collect(Collectors.toList());
    }
}
