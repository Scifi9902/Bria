package io.github.scifi9902.bria.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration {

    private final File file;

    public Config(String name, JavaPlugin plugin, String directory) {
        this.file = new File(directory, name + ".yml");

        if (!file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }

        load();
        save();
    }

    public void load() {
        try {
            load(this.file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}