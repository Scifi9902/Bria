package io.github.scifi9902.bria.utils.command.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDUtils {
    private static final Pattern PATTERN = Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");

    public static boolean isUUID(String string) {
        return  PATTERN.matcher(string).find();
    }

    public static Player getPlayer(String string) {
        return isUUID(string) ? Bukkit.getServer().getPlayer(UUID.fromString(string)) : Bukkit.getServer().getPlayer(string);
    }

    public static OfflinePlayer getOfflinePlayer(String string) {
        return isUUID(string) ? Bukkit.getServer().getOfflinePlayer(UUID.fromString(string)) : Bukkit.getServer().getOfflinePlayer(string);
    }
}

