package com.imdamilan.spigotadditions.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetPageButton extends GUIButton {

    private final PaginatedGUI gui;
    private final SetPageButtonType type;

    /**
     * Creates a new NextPageButton with the given item and GUI.
     * @param item The item to be displayed in the inventory.
     * @param gui The PaginatedGUI to be used.
     */
    public SetPageButton(ItemStack item, PaginatedGUI gui, SetPageButtonType type) {
        super(item);
        this.gui = gui;
        this.type = type;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (type == SetPageButtonType.NEXT) {
            if (gui.getCurrentPage() < gui.getPages().size() - 1) {
                gui.nextPage(event.getWhoClicked());
            }
        } else if (type == SetPageButtonType.PREVIOUS) {
            if (gui.getCurrentPage() > 0) {
                gui.previousPage(event.getWhoClicked());
            }
        }
    }
}
