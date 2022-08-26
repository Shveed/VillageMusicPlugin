package ru.shveed.vmp.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.shveed.vmp.VillageMusicPlugin;

public class ServerEventHandler implements Listener {

    public ServerEventHandler(VillageMusicPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent event) {
        event.setJoinMessage(
                "Здарова, " + event.getPlayer().getName() +
                        "! Чувствуй себя как дома, закури калюмбасик, " +
                        "покушай курочки KFC и бахни певчанского =)"
        );
    }
}
