package ru.shveed.vmp.handler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.shveed.vmp.VillageMusicPlugin;

public class BlockEventHandler implements Listener {

    public BlockEventHandler(VillageMusicPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDiamondBlockPlaced(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.DIAMOND_BLOCK) return;

        Bukkit.getServer().broadcastMessage("Здарова, Ниггеры. Это алмазич-калюмбасич ёпты");
    }
}
