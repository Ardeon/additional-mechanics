package ru.ardeon.additionalmechanics.mechanics.portal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ScrollListener implements Listener {
	
	@EventHandler
	public void menuClick(InventoryClickEvent e) {
		Inventory inventory = e.getView().getTopInventory();
        if (inventory == null || inventory.getHolder() == null) {
            return;
        }
        if (inventory.getHolder() instanceof PortalMenu) {
            ((PortalMenu) inventory.getHolder()).onInventoryClick(e);
            e.setCancelled(true);
        }
	}
	
}
