package com.imdamilan.spigotadditions.utils;

import com.imdamilan.spigotadditions.SpigotAdditions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;

public class BukkitUtils {

    /**
     * @return The version of the server.
     */
    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    /**
     * Checks if the server version is higher than the given version.
     * @param version The version to check.
     * @return True if the server version is higher than the given version.
     */
    public static boolean isVersionHigherThan(String version) {
        return getServerVersion().compareTo(version) > 0;
    }

    /**
     * Checks if the server version is lower than the given version.
     * @param version The version to check.
     * @return True if the server version is lower than the given version.
     */
    public static boolean isVersionLowerThan(String version) {
        return getServerVersion().compareTo(version) < 0;
    }

    /**
     * Checks if the server version is equal to the given version.
     * @param version The version to check.
     * @return True if the server version is equal to the given version.
     */
    public static boolean isVersionEqualTo(String version) {
        return getServerVersion().compareTo(version) == 0;
    }

    /**
     * Checks if the server version is higher than or equal to the given version.
     * @param version The version to check.
     * @return True if the server version is higher than or equal to the given version.
     */
    public static boolean isVersionHigherThanOrEqualTo(String version) {
        return getServerVersion().compareTo(version) >= 0;
    }

    /**
     * Checks if the server version is lower than or equal to the given version.
     * @param version The version to check.
     * @return True if the server version is lower than or equal to the given version.
     */
    public static boolean isVersionLowerThanOrEqualTo(String version) {
        return getServerVersion().compareTo(version) <= 0;
    }

    /**
     * Gets a value asynchronously and returns it as a CompletableFuture, this is useful for getting values from the database.
     * Will also modify the data of the object passed as a parameter, so it can also be used to modify the data of an object asynchronously.
     * @param object The object to modify asynchronously.
     * @param runnable The runnable to run asynchronously.
     * @return A CompletableFuture with the value of the object.
     * @param <T> The type of the object.
     */
    public static <T> CompletableFuture<T> getAsync(T object, Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> {
            runnable.run();
            return object;
        });
    }

    /**
     * Runs a runnable asynchronously.
     * @param runnable The runnable to run asynchronously.
     */
    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotAdditions.getInstance(), runnable);
    }

    /**
     * Runs a runnable asynchronously as the given plugin.
     * @param plugin The plugin to run the runnable as.
     * @param runnable The runnable to run asynchronously.
     */
    public static void runAsync(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Runs the runnable after the given delay.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     */
    public static void runLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(SpigotAdditions.getInstance(), runnable, delay);
    }

    /**
     * Runs the runnable after the given delay as the given plugin.
     * @param plugin The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     */
    public static void runLater(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    /**
     * Runs the runnable after the given delay asynchronously.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     */
    public static void runLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(SpigotAdditions.getInstance(), runnable, delay);
    }

    /**
     * Runs the runnable after the given delay asynchronously as the given plugin.
     * @param plugin The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     */
    public static void runLaterAsync(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    /**
     * Runs the runnable as a repeating task.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     * @param period The period of repeating in ticks.
     */
    public static void runTimer(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(SpigotAdditions.getInstance(), runnable, delay, period);
    }

    /**
     * Runs the runnable as a repeating task as the given plugin.
     * @param plugin The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     * @param period The period of repeating in ticks.
     */
    public static void runTimer(Plugin plugin, Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    /**
     * Runs the runnable as a repeating task asynchronously.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     * @param period The period of repeating in ticks.
     */
    public static void runTimerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotAdditions.getInstance(), runnable, delay, period);
    }

    /**
     * Runs the runnable as a repeating task asynchronously as the given plugin.
     * @param plugin The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay The delay in ticks.
     * @param period The period of repeating in ticks.
     */
    public static void runTimerAsync(Plugin plugin, Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }

    /**
     * Gets the NMS class with the given name (e.g. "EntityPlayer").
     * @param name The name of the class.
     * @return The NMS class.
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getServerVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the CraftBukkit class with the given name (e.g. "CraftPlayer").
     * @param name The name of the class.
     * @return The CraftBukkit class.
     */
    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retuns the passed string with & replaced with §, a.k.a. color codes.
     * @param string The string to replace.
     * @return The string with color codes.
     */
    public static String getColorCoded(String string) {
        return string.replace("&", "§");
    }

    /**
     * Returns the passed string color coded with the selected color code character.
     * @param string The string to replace.
     * @param colorCode The color code character.
     * @return The string with color codes.
     */
    public static String getColorCoded(String string, char colorCode) {
        return string.replace(colorCode, '§');
    }

    /**
     * @return The server's TPS (ticks per second).
     */
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
