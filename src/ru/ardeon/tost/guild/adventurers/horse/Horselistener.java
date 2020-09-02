package ru.ardeon.tost.guild.adventurers.horse;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.spigotmc.event.entity.EntityDismountEvent;


public class Horselistener implements Listener {
	HorseController controller;
	
	public Horselistener(HorseController controller) {
		this.controller = controller;
	}
	
	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		Entity horse = e.getDismounted();
		if (controller.horses.remove(horse)) {
			
			horse.remove();
			//controler.horses.remove(horse);
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		Entity horse = e.getEntity();
		if (controller.horses.remove(horse)) {
			e.getDrops().forEach(i->i.setAmount(0));
			horse.remove();
			//controler.horses.remove(horse);
		}
	}
	/**/
	@EventHandler
	public void onExploit(InventoryClickEvent e) {
		Inventory inventory = e.getClickedInventory();
        if (inventory == null || inventory.getHolder() == null) {
            return;
        }
		InventoryHolder holder = e.getInventory().getHolder();
		if (holder!=null&&holder instanceof Entity&&controller.horses.contains((Entity)holder))
			e.setCancelled(true);
	}
	
	/*
	@EventHandler
	public void onDeath(EntityDamageEvent e) {
		Entity horse = e.getEntity();
		if (controler.horses.contains(horse)) {
			horse.getPassengers().forEach(p->{
				EntityDamageEvent dm = new EntityDamageEvent(p, EntityDamageEvent.DamageCause.CUSTOM, e.getDamage());
				tost.p.getServer().getPluginManager().callEvent(dm);
			});
		}
	}
	*/
}
