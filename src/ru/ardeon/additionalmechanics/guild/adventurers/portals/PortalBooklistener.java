package ru.ardeon.additionalmechanics.guild.adventurers.portals;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class PortalBooklistener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				ItemStack item = e.getItem();
				//Player p = e.getPlayer();
				if (item != null) {
					ItemMeta meta = item.getItemMeta();
					if (meta.hasLore()) {
						List<String> lore = meta.getLore();
						if ((lore.get(0).equals("§6KNigga"))) {
							//PortalBook book = new PortalBook();
							//p.getInventory().addItem(book.i);
							//book.open(p);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		Inventory inventory = e.getView().getTopInventory();
        if (inventory == null || inventory.getHolder() == null) {
            return;
        }
        if (inventory.getHolder() instanceof PortalMenu) {
            ((PortalMenu) inventory.getHolder()).onInventoryClick(e);
        }
    }
	
}
