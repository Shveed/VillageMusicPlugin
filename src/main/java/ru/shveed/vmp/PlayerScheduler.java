package ru.shveed.vmp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class PlayerScheduler {

    private final Plugin plugin;
    private final PlayerDataWriter playerDataWriter;
    private final int tickFrequency;
    private final int maximumTasks;
    private final Set<PlayerTask> playerTasks;

    private final Set<PlayerTask> startingTasks = new HashSet<>();


    private class PlayerTask implements Runnable {

        private final Set<UUID> playerIds = new HashSet<>();
        private final int index;
        private final int tickDisplacement;
        private volatile BukkitTask task;

        private PlayerTask(int index, int tickDisplacement) {
            this.index = index;
            this.tickDisplacement = tickDisplacement;
        }

        @Override
        public void run() {
            synchronized (PlayerScheduler.this) {

                // Если есть невыполненные таски, то выполняем их
                if (!startingTasks.isEmpty()) {
                    for (PlayerTask startingTask : startingTasks) {
                        int diff = startingTask.index - index;
                        int delay = (diff < 0 ? maximumTasks + diff : diff) * tickDisplacement;
                        startingTask.task = Bukkit.getScheduler().runTaskTimer(plugin, startingTask, delay, tickFrequency);
                    }
                    startingTasks.clear();
                }

                Iterator<UUID> iterator = playerIds.iterator();
                while (iterator.hasNext()) {
                    UUID playerId = iterator.next();
                    Player player = Bukkit.getPlayer(playerId);
                    if (player != null) {
                        playerDataWriter.writeData(player);
                    } else {
                    }
                }
            }
        }
    }

    public interface PlayerDataWriter {

        void writeData(Player player);

        void flushData();
    }
}
