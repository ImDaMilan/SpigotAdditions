package com.imdamilan.spigotadditions.events.custom;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilItemTakeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final @Getter Player player;
    private final @Getter ItemStack item;
    private final @Getter AnvilInventory inventory;

    public AnvilItemTakeEvent(Player player, ItemStack item, AnvilInventory inventory) {
        this.player = player;
        this.item = item;
        this.inventory = inventory;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
