package ru.ardeon.tost.guild.adventurers.portals;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
    public void onInventoryClick(InventoryClickEvent e) {
    	if (e.getCurrentItem()!=null&&!e.getCurrentItem().getType().equals(Material.PAPER)) {
    		e.setCancelled(true);
    	}
    }
    
    
    /*
     * 
     * 
     * Inventory inventory = e.getClickedInventory();
        if (inventory == null || inventory.getHolder() == null) {
            return;
        }
        if (inventory.getHolder() instanceof LootChestInventory) {
            ((LootChestInventory) inventory.getHolder()).onInventoryClick(e);
        }
    */
}