package ru.shveed.vmp;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.logging.Logger;

public class NotificationManager {

    private static final Logger logger = Bukkit.getLogger();
    private static final Server server = Bukkit.getServer();

    public static void printOnStartup() {
        logger.info("Oxide clown ebaniy");
    }

    public static void printOnShutdown() {
        logger.info("YOUR PLUGIN IS SHUTTING DOWN");
    }

    public static void printCurrentSongPlaying(String author, String songName) {
        server.broadcastMessage("Сейчас играет: " + author + " - " + songName);
    }
}
