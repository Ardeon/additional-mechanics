package ru.ardeon.additionalmechanics.myEntity;

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
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class HealTotem extends Totem {
	BukkitRunnable healtimer = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			timer--;
			World w = a.getWorld();
			w.playSound(a.getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 2, 1.2f);
			List<Entity> players = a.getNearbyEntities(7, 4, 7);
			AreaEffectCloud cloud = (AreaEffectCloud) w.spawnEntity(a.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(7);
			cloud.setColor(Color.LIME);
			for (Entity m: players)
			{
				if (m instanceof Player)
				{
					Player play = (Player) m;
					double maxhealth = play.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					double newhealth = play.getHealth()+1;
					if (newhealth<maxhealth && !play.isDead())
					{
						play.setHealth(newhealth);
					}
					w.spawnParticle(Particle.VILLAGER_HAPPY, play.getEyeLocation(), 2);
				}
			}
			if (timer<=0)
			{
				healtimer.cancel();
				a.remove();
				p.sendTitle("", "Тотем исцеления исчез!", 5, 10, 5);
				healtimer = null;
			}
		}
	};
	public HealTotem(Player p)
	{
		super(p);
		EntityEquipment eq = a.getEquipment();
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
		meta.setColor(Color.LIME);
		boots.setItemMeta(meta);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		leggings.setItemMeta(meta);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		chestplate.setItemMeta(meta);
		ItemStack helmet = new ItemStack(Material.LIME_CONCRETE, 1);
		eq.setBoots(boots);
		eq.setLeggings(leggings);
		eq.setChestplate(chestplate);
		eq.setHelmet(helmet);
		healtimer.runTaskTimer(AdditionalMechanics.getPlugin(), 0, 20);
	}
	public HealTotem()
	{
		super();
	}
}
