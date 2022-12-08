package com.imdamilan.spigotadditions.utils;

import com.imdamilan.spigotadditions.SpigotAdditions;
import com.imdamilan.spigotadditions.events.Listener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PlayerUtils {

    private static final ArrayList<Player> vanishedPlayers = new ArrayList<>();

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void hidePlayer(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(SpigotAdditions.getInstance(), player);
        }
        vanishedPlayers.add(player);
    }

    public static void showPlayer(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(SpigotAdditions.getInstance(), player);
        }
        vanishedPlayers.remove(player);
    }

    public static ItemStack getItemInMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    public static ItemStack getItemInOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }

    public static void banPlayer(Player player, String reason) {
        player.kickPlayer(reason);
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, null, null);
    }

    public static void banPlayer(Player player, String reason, String source) {
        player.kickPlayer(reason);
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, null, source);
    }

    public static void banPlayer(Player player, String reason, String source, Date expiration) {
        player.kickPlayer(reason);
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, expiration, source);
    }

    public static void banPlayer(Player player, String reason, Date expiration) {
        player.kickPlayer(reason);
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), reason, expiration, null);
    }

    public static void banPlayerIP(Player player, String reason) {
        player.kickPlayer(reason);
        if (player.getAddress() != null) {
            Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress(), reason, null, null);
        }
    }

    public static void banPlayerIP(Player player, String reason, String source) {
        player.kickPlayer(reason);
        if (player.getAddress() != null) {
            Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress(), reason, null, source);
        }
    }

    public static void banPlayerIP(Player player, String reason, String source, Date expiration) {
        player.kickPlayer(reason);
        if (player.getAddress() != null) {
            Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress(), reason, expiration, source);
        }
    }

    public static void heal(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    public static void feed(Player player) {
        player.setFoodLevel(20);
    }

    public static void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public static void playSound(Player player, String sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public static void playSound(Player player, String sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playSoundGlobal(String sound, float volume, float pitch) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer.getLocation(), sound, volume, pitch);
        }
    }

    public static void playSoundGlobal(String sound) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer.getLocation(), sound, 1, 1);
        }
    }

    public static void setPlayerListName(Player player, String name) {
        player.setPlayerListName(name);
    }

    public static void setPlayerHeader(Player player, String header) {
        player.setPlayerListHeader(header);
    }

    public static void setPlayerFooter(Player player, String footer) {
        player.setPlayerListFooter(footer);
    }

    public static void setPlayerHeaderFooter(Player player, String header, String footer) {
        player.setPlayerListHeaderFooter(header, footer);
    }

    public static void setPlayerTabName(Player player, String name) {
        player.setPlayerListName(name);
    }

    public static Block getTargetBlock(Player player, int range) {
        return player.getTargetBlock(null, range);
    }

    public static void setMaxHealth(Player player, double health) {
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
    }

    public static Block getBlockBelow(Player player) {
        return player.getLocation().subtract(0, 1, 0).getBlock();
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", BukkitUtils.getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void kill(Player player) {
        player.setHealth(0);
    }

    public static UUID getOfflinePlayerUUID(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
    }

    public static @Listener void onPlayerJoin(PlayerJoinEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (vanishedPlayers.contains(player)) {
                event.getPlayer().hidePlayer(SpigotAdditions.getInstance(), player);
            } else {
                event.getPlayer().showPlayer(SpigotAdditions.getInstance(), player);
            }
        }
    }
}
