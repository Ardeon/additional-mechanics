package ru.ardeon.additionalmechanics.skills;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.ardeon.additionalmechanics.util.LevelOfPermission;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class Heals {
	public static ItemSkill holy = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.BOOK)))
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(5, 2);
				List<Entity> h;
				World w = player.getWorld();
				h = player.getNearbyEntities(4, 4, 4);
				PotionEffect ef = new PotionEffect(PotionEffectType.HEAL, 1, 0);
				for(Entity ent : h)
				{
					if (ent instanceof Player)
					{
						Player targetplayer = (Player) ent;
						ef.apply(targetplayer);
						w.spawnParticle(Particle.HEART, targetplayer.getEyeLocation(), 12);
					}
					if (ent instanceof Zombie||ent instanceof Skeleton)
					{
						LivingEntity le = (LivingEntity) ent;
						ef.apply(le);
						w.spawnParticle(Particle.CRIT, le.getEyeLocation(), 12);
					}
				}
				ef = new PotionEffect(PotionEffectType.REGENERATION, 10, 4);
				ef.apply(player);
				player.setCooldown(Material.BOOK, 80 - cd * 7);
				AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(5);
				cloud.setColor(Color.YELLOW);
				world.playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 2, 2);
			}
		}
	};
	public static ItemSkill honey = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.HONEYCOMB))) 
			{
				//cd 7
				int cd = LevelOfPermission.getLevel(player, "adm.honeycd", 7);
				player.setCooldown(Material.HONEYCOMB, 2000 - cd * 100);
				world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1.2f);
				PotionEffect ef = new PotionEffect(PotionEffectType.REGENERATION, 20, 10);
				ef.apply(player);
				world.spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation(), 16);
			}
		}
	};
	public static ItemSkill firstAid = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.SCUTE))) 
			{
				player.setCooldown(Material.SCUTE, 50);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1.2f);
				PotionEffect ef = new PotionEffect(PotionEffectType.REGENERATION, 50, 3);
				ef.apply(player);
				item.setAmount(item.getAmount()-1);
			}
		}
	};
	
	
	public static boolean SoulUse(PlayerInteractEntityEvent e)//for delete
	{
		Player player = e.getPlayer();
		Entity target = e.getRightClicked();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.SLIME_BALL))&& (target instanceof Player)) 
		{
			player.setCooldown(Material.SLIME_BALL, 50);
			Player targetplayer = (Player) target;
			world.playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 1, 1.2f);
			PotionEffect ef = new PotionEffect(PotionEffectType.HEAL, 1, 2);
			ef.apply(targetplayer);
			ef = new PotionEffect(PotionEffectType.SPEED, 120, 1);
			ef.apply(targetplayer);
			ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 2);
			ef.apply(targetplayer);
			return true;
		}
		return false;
	}
	
}
