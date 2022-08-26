package ru.shveed.vmp;

import org.bukkit.plugin.java.JavaPlugin;
import ru.shveed.vmp.command.CommandEventHandler;
import ru.shveed.vmp.handler.BlockEventHandler;
import ru.shveed.vmp.handler.PlayerEventHandler;

public class VillageMusicPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        NotificationManager.printOnStartup();

        getCommand("").setExecutor(new CommandEventHandler());

        new BlockEventHandler(this);
        new PlayerEventHandler(this);
    }

    @Override
    public void onDisable() {
        NotificationManager.printOnShutdown();
    }
}
