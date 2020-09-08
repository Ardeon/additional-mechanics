package ru.ardeon.additionalmechanics.guild.adventurers.hook;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

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
			ProjectileSource shooter = projectile.getShooter();
			if (shooter instanceof Player) {
				Player p = (Player)shooter;
				Location hookLoc = projectile.getLocation();
				Location pLoc = p.getLocation();
				
				World w = hookLoc.getWorld();
				
				Vector hookVec = hookLoc.toVector().clone();
				Vector pVec = pLoc.toVector().clone();
				if (w.equals(pLoc.getWorld())&&hookVec.distance(pVec)>5) {
					Vector direction = pVec.clone().subtract(hookVec).normalize();
					Entity lastent = projectile;
					int i = 0;
					while (hookVec.clone().add(direction.clone().multiply(i)).distance(pVec)>5) {
						LivingEntity ent = (LivingEntity)projectile
								.getWorld()
								.spawnEntity(projectile.getLocation()
								.add(direction.clone().multiply(3*i)), 
								EntityType.SILVERFISH);
						ent.setAI(false);
						ent.setSilent(true);
						ent.setLeashHolder(lastent);
						lastent = ent;
						i++;
						if (i>20)
							break;
					}
				}
				//hookLoc.distance(pLoc);
				//LivingEntity ent = (LivingEntity)projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.ARMOR_STAND);
				//ent.setLeashHolder(projectile);
			}
			
		}
	}
	
	
}
