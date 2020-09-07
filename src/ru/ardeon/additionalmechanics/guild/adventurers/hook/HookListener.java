package ru.ardeon.additionalmechanics.guild.adventurers.hook;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class HookListener implements Listener {
	@EventHandler
	public void onUnleash(EntityUnleashEvent e) {
		AdditionalMechanics.getPlugin().getLogger().info(e.getEntity().getType().toString());
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		Block block = e.getHitBlock();
		Projectile projectile = e.getEntity();
		if (block!=null&&projectile.getScoreboardTags().contains("grapplinghook")) {
			LivingEntity ent = (LivingEntity)projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.SHEEP);
			ent.setLeashHolder(projectile);
		}
	}
	
	
}
