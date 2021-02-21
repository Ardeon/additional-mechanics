package ru.ardeon.additionalmechanics;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import ru.ardeon.additionalmechanics.skills.interact.Heals;
import ru.ardeon.additionalmechanics.util.ItemUtil;
import ru.ardeon.additionalmechanics.util.LevelOfPermission;


public class EventsListener implements Listener 
{
	private InteractSkillSwitcher interactSkillSwitcher;
	
	EventsListener(InteractSkillSwitcher interactSkillSwitcher){
		this.interactSkillSwitcher = interactSkillSwitcher;
	}
	
	public void reload() {
		this.interactSkillSwitcher = AdditionalMechanics.getPlugin().interactSkillSwitcher;
	}
	@Deprecated
	@EventHandler
	public void ShootBow(EntityShootBowEvent e) {
		ItemStack bow = e.getBow();
		if (ItemUtil.testForLore(bow)) {
			List<String> lore = bow.getItemMeta().getLore();
			if (lore.contains("§aВзрывная стрела§a")) {
				LivingEntity owner = e.getEntity();
				if (owner instanceof Player) {
					Player p = (Player) owner;
					Entity arrow = e.getProjectile();
					if (p.isSneaking()&&!(p.hasCooldown(Material.FIREWORK_ROCKET))) {
						p.setCooldown(Material.FIREWORK_ROCKET, 600);
						arrow.addScoreboardTag("ExplosiveArrow");
					}
					else {
						double x = Math.random() * 100;
						if (x>96) {
							arrow.addScoreboardTag("ExplosiveArrow");
						}
					}
				}
			}	
		}
	}
	
	@EventHandler
	public void EntityDamageEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager().getScoreboardTags().contains("snowball"))
		{
			e.setDamage(6);
			Projectile sb = (Projectile) e.getDamager();
			ProjectileSource shooter = sb.getShooter();
			World w = sb.getWorld();
			if (e.getEntity() instanceof LivingEntity)
			{
				
				LivingEntity target = (LivingEntity) e.getEntity();
				PotionEffect ef = new PotionEffect(PotionEffectType.SLOW, 120, 2);
				w.playSound(target.getLocation(), Sound.ENTITY_SNOW_GOLEM_HURT, 1, 2);
				w.spawnParticle(Particle.SNOW_SHOVEL, target.getEyeLocation(), 7);
				AreaEffectCloud cloud = (AreaEffectCloud) e.getEntity().getWorld().spawnEntity(target.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(3);
				cloud.setColor(Color.WHITE);
				if(!(target instanceof Player)) 
				{
					ef.apply(target);
				}
				for (Entity t: target.getNearbyEntities(3, 3, 3))
				{
					if (t instanceof LivingEntity && !(t instanceof Player))
					{
						LivingEntity nt = (LivingEntity) t;
						ef.apply(nt);
						nt.damage(2, (Entity) shooter);
						w.spawnParticle(Particle.SNOW_SHOVEL, nt.getEyeLocation(), 7);
					}
				}
			}
		}
		
		if (e.getDamager().getScoreboardTags().contains("hook"))
		{
			
			Projectile sb = (Projectile) e.getDamager();
			//ProjectileSource shooter = sb.getShooter();
			World w = sb.getWorld();
			if (e.getEntity() instanceof LivingEntity)
			{
				
				LivingEntity target = (LivingEntity) e.getEntity();
				PotionEffect ef = new PotionEffect(PotionEffectType.WEAKNESS, 120, 3);
				if(!(target instanceof Player)) 
				{
					e.setDamage(4);
					ef.apply(target);
					w.spawnParticle(Particle.WATER_SPLASH, target.getEyeLocation(), 20);
					Vector v  = sb.getVelocity().multiply(-2.0d);
					target.setVelocity(v);
					w.playSound(target.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 2, 2);
				}
				else
				{
					e.setDamage(0);
				}
				
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) 
	{
		Projectile proj = e.getEntity();
		if (proj.getScoreboardTags().contains("ExplosiveArrow"))
		{
			ProjectileSource shooter = proj.getShooter();
			World w = proj.getWorld();
			w.createExplosion(proj.getLocation(), 3, false, false , (Entity) shooter);
			proj.remove();
		}
		if (proj.getScoreboardTags().contains("fireball"))
		{
			ProjectileSource shooter = proj.getShooter();
			int power = 0;
			if (shooter instanceof Player) {
				Player player = (Player) shooter;
				power = LevelOfPermission.getLevel(player, "adm.quartz", 3);
			}
			
			World w = proj.getWorld();
			PotionEffect ef = new PotionEffect(PotionEffectType.WITHER, 60 + power * 8, 2 + power/2);
			for (Entity t: proj.getNearbyEntities(3 + power, 3 + power, 3 + power))
			{
				if (t instanceof Mob)
				{
					Mob nt = (Mob) t;
					ef.apply(nt);
					w.spawnParticle(Particle.SMOKE_LARGE, nt.getEyeLocation(), 7);
				}
			}
			proj.remove();
		}
	}
	
	@EventHandler
	public void onPlayerTouchUnTouchable(PlayerArmorStandManipulateEvent e) 
	{
		if (e.getRightClicked().getScoreboardTags().contains("untouchable"))
			e.setCancelled(true);
	}
	@Deprecated
	@EventHandler
	public void onPlayerHitEntity(EntityDamageByEntityEvent e) 
	{
		Entity damager = e.getDamager();
		if (damager instanceof Player)
		{
			Player p = (Player) damager;
			ItemStack i = p.getInventory().getItemInMainHand();
			ItemMeta met = i.getItemMeta();
			if (met!=null&&met.hasLore())
			{
				List<String> lore = met.getLore();
				if (lore.contains("§aВампиризм§a"))
				{
					double maxhealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					
					double newhealth = p.getHealth()+e.getFinalDamage()/4;
					if (newhealth<maxhealth)
					{
						p.setHealth(newhealth);
					}
					else 
						p.setHealth(maxhealth);
				}
				if (lore.contains("§aПохищение души§a"))
				{
					double x = Math.random() * 100;
					if (x>99) {
						ItemStack soul = new ItemStack(Material.SLIME_BALL,1);
						ItemMeta soulmeta = soul.getItemMeta();
						soulmeta.setDisplayName("§1Частичка души§1");
						List<String> soullore = new ArrayList<String>();
						soullore.add("§aПередать жизненные силы§a");
						soullore.add("§2Усиливает союзника§2");
						soulmeta.setLore(soullore);
						soul.setItemMeta(soulmeta);
						p.getInventory().addItem(soul);
					}
					x = Math.random() * 100;
					if (x>96)
					{
						ItemStack soul = new ItemStack(Material.BROWN_DYE,1);
						ItemMeta soulmeta = soul.getItemMeta();
						soulmeta.setDisplayName("§1Частичка хаоса§1");
						List<String> soullore = new ArrayList<String>();
						soullore.add("§aОслабить врагов§a");
						soullore.add("§2Снижает урон противников§2");
						soulmeta.setLore(soullore);
						soul.setItemMeta(soulmeta);
						p.getInventory().addItem(soul);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerClickPlayer(PlayerInteractEntityEvent e) 
	{
		ItemStack item = e.getPlayer().getInventory().getItem(e.getHand());
		if (ItemUtil.testForLore(item)) {
			if (item.getItemMeta().getLore().contains("§aПередать жизненные силы§a")) {
				if (Heals.SoulUse(e))
					item.setAmount(item.getAmount()-1);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		//Player player = e.getPlayer();e.getHand().equals(EquipmentSlot.HAND) && 
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				ItemStack item = e.getItem();
				if (ItemUtil.testForSkillTag(item)) {
					interactSkillSwitcher.ItemChoose(e);
				}
			}
		}
	}

}//конец класса
