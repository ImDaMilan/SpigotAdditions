package com.imdamilan.spigotadditions.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends GUIButton {

    private final PaginatedGUI gui;

    /**
     * Creates a new NextPageButton with the given item and GUI.
     * @param item The item to be displayed in the inventory.
     * @param gui The PaginatedGUI to be used.
     */
    public NextPageButton(ItemStack item, PaginatedGUI gui) {
        super(item);
        this.gui = gui;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (gui.getCurrentPage() < gui.getPages().size() - 1) {
            gui.nextPage(event.getWhoClicked());
        }
    }
}
