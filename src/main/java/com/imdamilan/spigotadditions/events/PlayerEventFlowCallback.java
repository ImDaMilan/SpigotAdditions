package com.imdamilan.spigotadditions.events;

import com.imdamilan.spigotadditions.SpigotAdditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class PlayerEventFlowCallback<T extends PlayerEvent, K extends PlayerEvent> implements Listener {

    private Player player;
    private boolean done;
    private Consumer<T> firstEvent;
    private Consumer<K> secondEvent;

    public PlayerEventFlowCallback(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public PlayerEventFlowCallback() {
        Bukkit.getPluginManager().registerEvents(this, SpigotAdditions.getInstance());
    }

    public PlayerEventFlowCallback<T, K> listen(Consumer<T> event) {
        this.firstEvent = event;
        return this;
    }

    public PlayerEventFlowCallback<T, K> then(Consumer<K> event) {
        this.secondEvent = event;
        return this;
    }

    @EventHandler
    public void onEvent(T event) {
        firstEvent.accept(event);
        Player player = event.getPlayer();
        this.done = true;
    }

    @EventHandler
    public void onSecondEvent(K event) {
        if (done) {
            if (event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                secondEvent.accept(event);
            }
        }
    }
}
