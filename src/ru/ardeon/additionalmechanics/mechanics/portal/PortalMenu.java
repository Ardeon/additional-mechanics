package ru.ardeon.additionalmechanics.mechanics.portal;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class PortalMenu implements InventoryHolder {

    private final Inventory inventory;

    public PortalMenu(String title) {
        this.inventory = Bukkit.createInventory(this, 5 * 9, title);
    }
    
    public PortalMenu(String title, ItemStack[] items) {
        this.inventory = Bukkit.createInventory(this, 5 * 9, title);
        inventory.setContents(items);
    }

    public PortalMenu(String title, Portal[] portals) {
    	this.inventory = Bukkit.createInventory(this, 5 * 9, title);
    	ItemStack[] items = new ItemStack[45];
    	for (int i = 0; i < portals.length; i++) {
    		items[i] = portals[i].getItemIcon();
    	}
        inventory.setContents(items);
	}

	@Override
    public Inventory getInventory() {
        return this.inventory;
    }

	public void onInventoryClick(InventoryClickEvent e) {
		e.getSlot();
	}
    
}