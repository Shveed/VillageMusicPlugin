package ru.shveed.vmp.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.shveed.vmp.VillageMusicPlugin;

public class PlayerEventHandler implements Listener {

    public PlayerEventHandler(VillageMusicPlugin plugin) {
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


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // todo: remove from music listeners list
    }
}
