package com.imdamilan.spigotadditions.inventories;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class GUIButton {

    private final @Getter ItemStack item;

    protected GUIButton(ItemStack item) {
        this.item = item;
    }

    /**
     * Creates a new GUIButton with the given item and click event.
     * @param item The item to be displayed in the inventory.
     * @param clickEventConsumer The event to be executed when the button is clicked.
     */
    public static GUIButton create(ItemStack item, Consumer<InventoryClickEvent> clickEventConsumer) {
        return new GUIButton(item) {

            @Override
            public void onClick(InventoryClickEvent event) {
                clickEventConsumer.accept(event);
            }
        };
    }

    public abstract void onClick(InventoryClickEvent event);
}
