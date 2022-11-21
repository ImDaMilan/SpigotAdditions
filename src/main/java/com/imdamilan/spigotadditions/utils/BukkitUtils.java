package com.imdamilan.spigotadditions.utils;

import com.imdamilan.spigotadditions.SpigotAdditions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;

public class BukkitUtils {
    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static boolean isVersionHigherThan(String version) {
        return getServerVersion().compareTo(version) > 0;
    }

    public static boolean isVersionLowerThan(String version) {
        return getServerVersion().compareTo(version) < 0;
    }

    public static boolean isVersionEqualTo(String version) {
        return getServerVersion().compareTo(version) == 0;
    }

    public static boolean isVersionHigherThanOrEqualTo(String version) {
        return getServerVersion().compareTo(version) >= 0;
    }

    public static boolean isVersionLowerThanOrEqualTo(String version) {
        return getServerVersion().compareTo(version) <= 0;
    }

    public static <T> CompletableFuture<T> getAsync(T object, Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> {
            runnable.run();
            return object;
        });
    }

    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotAdditions.getInstance(), runnable);
    }

    public static void runAsync(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static void runLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(SpigotAdditions.getInstance(), runnable, delay);
    }

    public static void runLater(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(SpigotAdditions.getInstance(), runnable, delay);
    }

    public static void runLaterAsync(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public static void runTimer(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(SpigotAdditions.getInstance(), runnable, delay, period);
    }

    public static void runTimer(Plugin plugin, Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    public static void runTimerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotAdditions.getInstance(), runnable, delay, period);
    }

    public static void runTimerAsync(Plugin plugin, Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getServerVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getColorCoded(String string) {
        return string.replace("&", "§");
    }

    public static String getColorCoded(String string, char colorCode) {
        return string.replace(colorCode, '§');
    }

    public static double getTPS() {
        try {
            Class<?> serverClass = getNMSClass("MinecraftServer");
            if (serverClass == null) throw new NullPointerException("serverClass is null");
            Object server = serverClass.getMethod("getServer").invoke(null);
            double[] recentTps = (double[]) serverClass.getMethod("recentTps").invoke(server);
            return recentTps[0];
        } catch (Exception e) {
            throw new RuntimeException("Failed to get TPS", e);
        }
    }
}
