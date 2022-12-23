package com.imdamilan.spigotadditions.events.custom.listeners;

import com.imdamilan.spigotadditions.events.Listener;
import com.imdamilan.spigotadditions.events.custom.AnvilItemTakeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;

public class AnvilItemTakeListener {

    public static @Listener void onAnvilItemTake(InventoryClickEvent event) {
        if (event.getInventory() instanceof AnvilInventory inventory) {
            if (event.getSlot() == 2) {
                if (event.getCurrentItem() != null) {
                    Bukkit.getPluginManager().callEvent(new AnvilItemTakeEvent(Bukkit.getPlayer(event.getWhoClicked().getUniqueId()), event.getCurrentItem(), inventory));
                }
            }
        } else if (event.getClickedInventory() instanceof AnvilInventory inventory) {
            if (event.getSlot() == 2) {
                if (event.getCurrentItem() != null) {
                    Bukkit.getPluginManager().callEvent(new AnvilItemTakeEvent(Bukkit.getPlayer(event.getWhoClicked().getUniqueId()), event.getCurrentItem(), inventory));
                }
            }
        } else if (event.getWhoClicked().getOpenInventory().getTopInventory() instanceof AnvilInventory inventory) {
            if (event.getSlot() == 2) {
                if (event.getCurrentItem() != null) {
                    Bukkit.getPluginManager().callEvent(new AnvilItemTakeEvent(Bukkit.getPlayer(event.getWhoClicked().getUniqueId()), event.getCurrentItem(), inventory));
                }
            }
        }
    }
}
