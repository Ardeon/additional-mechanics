package ru.ardeon.additionalmechanics.common.antitarget;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;


public class TargetListener implements Listener {
	@EventHandler
	public void MobAgro(EntityTargetEvent e) {
		Entity entity = e.getTarget();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.hasPermission("antitarget")) {
				e.setCancelled(true);
			}
		}
	}
}
