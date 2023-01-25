package com.imdamilan.spigotadditions.conversation.message;

import com.imdamilan.spigotadditions.SpigotAdditions;
import lombok.Data;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;
import java.util.function.Consumer;

public @Data class ChatMessage implements Listener {

    private String message;
    private Consumer<Player> event = player -> {};
    private String command = UUID.randomUUID().toString().substring(0, 5);
    private NamedTextColor color = NamedTextColor.WHITE;

    public ChatMessage(String message) {
        this.message = message;
        Bukkit.getPluginManager().registerEvents(this, SpigotAdditions.getInstance());
    }

    public void onClick(Consumer<Player> event) {
        this.event = event;
    }

    public void onClick() {
        this.event = player -> {};
    }

    public void setColor(ChatColor color) {
        message = color + message;
    }

    public void setColor(NamedTextColor color) {
        this.color = color;
    }

    public void send(Player player, BukkitAudiences adventure) {
        if (event == null) {
            player.sendMessage(message);
        } else {
            TextComponent component = Component.text(message).clickEvent(ClickEvent.runCommand("/" + command)).color(color);
            adventure.player(player).sendMessage(component);
        }
    }

    public void send(Player player) {
        send(player, SpigotAdditions.getAdventure());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (this.event == null) return;
        if (event.getMessage().equalsIgnoreCase("/" + command)) {
            event.setCancelled(true);
            this.event.accept(event.getPlayer());
        }
    }
}
