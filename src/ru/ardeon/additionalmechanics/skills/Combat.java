package ru.ardeon.additionalmechanics.skills;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.ardeon.additionalmechanics.myEntity.Bait;

public class Combat {

	public static void SlowStick(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.STICK))) 
			
		{
			Class<Snowball> ball = Snowball.class;
			Snowball r = e.getPlayer().launchProjectile(ball);
			r.setShooter(e.getPlayer());
			r.addScoreboardTag("snowball");
			player.setCooldown(Material.STICK, 40);
			world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
			world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
		}
	}

	public static void quartz(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.QUARTZ))) 
		{
			Class<SmallFireball> ball = SmallFireball.class;
			SmallFireball r = e.getPlayer().launchProjectile(ball);
			r.setShooter(e.getPlayer());
			r.addScoreboardTag("fireball");
			player.setCooldown(Material.QUARTZ, 40);
			world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 2);
		}
	}
	
	public static void Explosion(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.FIREWORK_STAR))) 
		{
			player.setCooldown(Material.FIREWORK_STAR, 800);
			world.createExplosion(player.getLocation(), 4, false, false, player);
		}
	}
	
	public static void Agro(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.RED_DYE))) 		
		{
			player.setCooldown(Material.RED_DYE, 1200);
			AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(5);
			cloud.setColor(Color.RED);
			List<Entity> mobs = player.getNearbyEntities(10, 4, 10);
			int counter=0;
			for (Entity m: mobs)
			{
				if (m instanceof Mob)
				{
					Mob mob = (Mob) m;
					mob.setTarget(player);
					counter++;
					world.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
				}
			}
			counter/=4;
			if (counter>4)
				counter = 4;
			PotionEffect ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 800, counter);
			ef.apply(player);
			world.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 2, 0.7f);
			world.playSound(player.getLocation(), Sound.BLOCK_NETHER_WART_BREAK, 2, 1);
		}
	}
	
	public static boolean SoulAgro(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.BROWN_DYE))) 		
		{
			player.setCooldown(Material.BROWN_DYE, 400);
			AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(5);
			cloud.setColor(Color.OLIVE);
			List<Entity> mobs = player.getNearbyEntities(10, 4, 10);
			PotionEffect wea = new PotionEffect(PotionEffectType.WEAKNESS, 800, 0);
			for (Entity m: mobs)
			{
				if (m instanceof Mob)
				{
					Mob mob = (Mob) m;
					mob.setTarget(player);

					world.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
					wea.apply(mob);
				}
			}
			world.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 2, 0.7f);
			return true;
		}
		return false;
	}
	
	public static void Rage(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.BLACK_DYE))) 
		{
			player.setCooldown(Material.BLACK_DYE, 500);
			double newhealth = player.getHealth()-3;
			if (newhealth<1)
				newhealth=1;
			player.setHealth(newhealth);
			PotionEffect ef = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 150, 3);
			ef.apply(player);
			ef = new PotionEffect(PotionEffectType.REGENERATION, 150, 2);
			ef.apply(player);
			ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 150, 1);
			ef.apply(player);
			world.spawnParticle(Particle.SQUID_INK, player.getEyeLocation(), 10);
			world.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_HURT, 2, 1.2f);
			world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_ON_FIRE, 2, 1.2f);
			world.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 2, 1.2f);
		}
	}
	
	public static void Bait(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.PLAYER_HEAD))) 
		{
			player.setCooldown(Material.PLAYER_HEAD, 1600);
			world.playSound(player.getLocation(), Sound.ENTITY_EVOKER_AMBIENT, 2, 1);
			world.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, 1.2f);
			world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
			Entity a = world.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
			a.setInvulnerable(true);
			ArmorStand la = (ArmorStand) a;
			la.addScoreboardTag("untouchable");
			equipstand(la);
			Bait b = new Bait(player,(ArmorStand) a);
			b.agrostart();
			e.setCancelled(true);
		}
	}
	
	public static void equipstand(ArmorStand la) 
	{
		EntityEquipment eq = la.getEquipment();
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1);
		eq.setBoots(boots);
		eq.setLeggings(leggings);
		eq.setChestplate(chestplate);
		eq.setHelmet(helmet);
	}
	
}
