package com.imdamilan.spigotadditions.utils;

import com.imdamilan.spigotadditions.SpigotAdditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static BukkitTask runAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(SpigotAdditions.getInstance(), runnable);
    }

    /**
     * Runs a runnable asynchronously as the given plugin.
     *
     * @param plugin   The plugin to run the runnable as.
     * @param runnable The runnable to run asynchronously.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runAsync(Plugin plugin, Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Runs the runnable after the given delay.
     *
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(SpigotAdditions.getInstance(), runnable, delay);
    }

    /**
     * Runs the runnable after the given delay as the given plugin.
     *
     * @param plugin   The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runLater(Plugin plugin, Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    /**
     * Runs the runnable after the given delay asynchronously.
     *
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runLaterAsync(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(SpigotAdditions.getInstance(), runnable, delay);
    }

    /**
     * Runs the runnable after the given delay asynchronously as the given plugin.
     *
     * @param plugin   The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runLaterAsync(Plugin plugin, Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    /**
     * Runs the runnable as a repeating task.
     *
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @param period   The period of repeating in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runTimer(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(SpigotAdditions.getInstance(), runnable, delay, period);
    }

    /**
     * Runs the runnable as a repeating task as the given plugin.
     *
     * @param plugin   The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @param period   The period of repeating in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runTimer(Plugin plugin, Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    /**
     * Runs the runnable as a repeating task asynchronously.
     *
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @param period   The period of repeating in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runTimerAsync(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotAdditions.getInstance(), runnable, delay, period);
    }

    /**
     * Runs the runnable as a repeating task asynchronously as the given plugin.
     *
     * @param plugin   The plugin to run the runnable as.
     * @param runnable The runnable to run.
     * @param delay    The delay in ticks.
     * @param period   The period of repeating in ticks.
     * @return The BukkitTask of the runnable.
     */
    public static BukkitTask runTimerAsync(Plugin plugin, Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
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
        return ChatColor.translateAlternateColorCodes(colorCode, string);
    }

    /**
     * Returns the string with the hex color codes replaced with the desired color.
     * The hex color codes must be in the format of &#RRGGBB.
     * @param message The string to replace.
     * @return The string with hex color codes replaced.
     */
    public static String applyHex(String message) {
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x').replace("&", "");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
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
