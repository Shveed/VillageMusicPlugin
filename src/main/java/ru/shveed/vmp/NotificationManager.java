package ru.shveed.vmp;

import org.bukkit.Bukkit;

public class NotificationManager {

    public static void printOnStartup() {
        Bukkit.getLogger().info("Oxide clown ebaniy");
    }

    public static void printOnShutdown() {
        Bukkit.getLogger().info("YOUR PLUGIN IS SHUTTING DOWN");
    }
}
