package ru.shveed.vmp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.shveed.vmp.handler.BlockEventHandler;
import ru.shveed.vmp.handler.ServerEventHandler;

import java.io.File;

public class VillageMusicPlugin extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {

        CustomConfig.setup();
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();

        new BlockEventHandler(this);
        new ServerEventHandler(this, config);

        Bukkit.getLogger().info("ExeLS: STARTED");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("ExeLS: SHUTTING DOWN");
    }
}
