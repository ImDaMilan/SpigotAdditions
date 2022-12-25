package com.imdamilan.spigotadditions.events.custom;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerStripLogEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final @Getter Player player;
    private final @Getter Block block;
    private final @Getter ItemStack item;

    public PlayerStripLogEvent(Player player, Block block, ItemStack item) {
        this.player = player;
        this.block = block;
        this.item = item;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
