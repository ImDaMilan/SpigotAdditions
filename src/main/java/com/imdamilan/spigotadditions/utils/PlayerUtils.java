package com.imdamilan.spigotadditions.utils;

import com.imdamilan.spigotadditions.SpigotAdditions;
import com.imdamilan.spigotadditions.events.Listener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

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
