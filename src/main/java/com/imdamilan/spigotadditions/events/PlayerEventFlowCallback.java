package com.imdamilan.spigotadditions.events;

import com.imdamilan.spigotadditions.SpigotAdditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * A class that allows you to listen for two events in a row, does a certain player handle, than will call a callback once the second event is called for the same player.
 * @param <T> The first event class, must extend PlayerEvent.
 * @param <K> The second event class, must extend PlayerEvent.
 */
public class PlayerEventFlowCallback<T extends PlayerEvent, K extends PlayerEvent> implements Listener {

    private final HashMap<Player, Boolean> map = new HashMap<>();
    private Consumer<T> firstEvent;
    private Consumer<K> secondEvent;

    public PlayerEventFlowCallback(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public PlayerEventFlowCallback() {
        Bukkit.getPluginManager().registerEvents(this, SpigotAdditions.getInstance());
    }

    /**
     * Sets the listener for the first event.
     * @param event The consumer to be called when the first event is called.
     * @return This PlayerEventFlowCallback instance.
     */
    public PlayerEventFlowCallback<T, K> listen(Consumer<T> event) {
        this.firstEvent = event;
        return this;
    }

    /**
     * Executes the callback when the second event is called for the same player as the first event.
     * @param event The consumer to be called when the second event is called.
     * @return This PlayerEventFlowCallback instance.
     */
    public PlayerEventFlowCallback<T, K> then(Consumer<K> event) {
        this.secondEvent = event;
        return this;
    }

    @EventHandler
    public void onEvent(T event) {
        firstEvent.accept(event);
        Player player = event.getPlayer();
        map.put(player, true);
    }

    @EventHandler
    public void onSecondEvent(K event) {
        if (map.getOrDefault(event.getPlayer(), false)) {
            if (map.containsKey(event.getPlayer())) {
                secondEvent.accept(event);
                map.remove(event.getPlayer());
            }
        }
    }
}
