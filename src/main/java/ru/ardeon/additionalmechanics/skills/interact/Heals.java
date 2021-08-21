package ru.ardeon.additionalmechanics.skills.interact;

import java.util.List;

import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Heals {
	public static InteractSkill holy = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.HOLY_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				List<Entity> h;
				World w = player.getWorld();
				h = player.getNearbyEntities(4, 4, 4);
				PotionEffect ef = new PotionEffect(PotionEffectType.HEAL, 1, 0);
				for(Entity ent : h)
				{
					if (ent instanceof Player)
					{
						Player targetPlayer = (Player) ent;
						ef.apply(targetPlayer);
						w.spawnParticle(Particle.HEART, targetPlayer.getEyeLocation(), 12);
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
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.HOLY_COOLDOWN.getInt());
				AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(5);
				cloud.setColor(Color.YELLOW);
				world.playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 2, 2);
			}
		}
	};
	public static InteractSkill honey = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.HONEY_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.HONEY_COOLDOWN.getInt());
				world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1.2f);
				PotionEffect ef = new PotionEffect(PotionEffectType.REGENERATION, 20, 10);
				ef.apply(player);
				world.spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation(), 16);
			}
		}
	};
	public static InteractSkill firstAid = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.FIRST_AID_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.FIRST_AID_COOLDOWN.getInt());
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1.2f);
				PotionEffect ef = new PotionEffect(PotionEffectType.REGENERATION, 50, 3);
				ef.apply(player);
				item.setAmount(item.getAmount()-1);
			}
		}
	};
	
}
