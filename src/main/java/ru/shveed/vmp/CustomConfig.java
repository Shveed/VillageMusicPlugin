package ru.shveed.vmp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CustomConfig {

    private static File configFile;
    private static FileConfiguration config;

    public static void setup() {
        File path = Objects.requireNonNull(
                Bukkit.getServer()
                        .getPluginManager()
                        .getPlugin("Vmp")
        ).getDataFolder();

        configFile = new File(path, "ExeLSConfig.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning("Failed to create config file");
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Failed to save config");
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

}
