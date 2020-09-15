package ru.ardeon.additionalmechanics.guild.adventurers.horse;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				ItemStack item = e.getItem();
				Player p = e.getPlayer();
				if (item != null) {
					ItemMeta meta = item.getItemMeta();
					if (meta.hasLore()) {
						List<String> lore = meta.getLore();
						if (!p.hasCooldown(Material.GLOWSTONE_DUST)&&(lore.get(0).equals(controller.itemLore))&&HorsePermission.getCanUse(p)) {
							double hp, speed, jump;
							hp = HorsePermission.getHp(p);
							speed = HorsePermission.getSpeed(p);
							jump = HorsePermission.getJump(p);
							controller.CreateHorse(p, EntityType.SKELETON_HORSE, hp, speed, jump);
							p.setCooldown(Material.GLOWSTONE_DUST, 40);
						}
					}
				}
			}
		}
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
