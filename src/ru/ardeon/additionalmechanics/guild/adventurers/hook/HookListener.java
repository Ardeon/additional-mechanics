package ru.ardeon.additionalmechanics.guild.adventurers.hook;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class HookListener implements Listener {
	
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
				if (w.equals(pLoc.getWorld())&&hookVec.distance(pVec)>3.3) {
					Vector direction = pVec.clone().subtract(hookVec).normalize();
					Entity lastent = null;
					PotionEffect ef = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2);
					int i = 0;
					while (hookVec.clone().add(direction.clone().multiply(2*i)).distance(pVec)>5) {
						LivingEntity ent = (LivingEntity)projectile
								.getWorld()
								.spawnEntity(projectile.getLocation()
								.add(direction.clone().multiply(2*i)), 
								EntityType.SILVERFISH);
						ef.apply(ent);
						ent.setAI(false);
						ent.setSilent(true);
						if (lastent!=null)
							ent.setLeashHolder(lastent);
						lastent = ent;
						i++;
						if (i>20)
							break;
					}
				}
			}
		}
	}
	
	
	
	@EventHandler
	public void onPlayerClickSilverfish(PlayerInteractEntityEvent e) 
	{
		Entity ent = e.getRightClicked();
		if (ent.getType().equals(EntityType.SILVERFISH)
				&& ent.isSilent()) {
			Player p = e.getPlayer();
			ent.addPassenger(p);
		}
	}
	
	@EventHandler
	public void onDropLead(EntityDropItemEvent e) 
	{
		Item item = e.getItemDrop();
		Entity ent = e.getEntity();
		if (item.getItemStack().getType().equals(Material.LEAD)
				&& ent.getType().equals(EntityType.SILVERFISH)
				&& ent.isSilent()) {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				ItemStack item = e.getItem();
				if (item != null) {
					if (item.getType().equals(Material.IRON_HOE)&&item.getItemMeta().hasLore()) {
						Player p = e.getPlayer();
						String str =  item.getItemMeta().getLore().get(0);
						if (str.equals("§aКрюк кошка")) {
							BatmanHook.useHook(p);
						}
					}
				}
			}
		}
	}
	
}
