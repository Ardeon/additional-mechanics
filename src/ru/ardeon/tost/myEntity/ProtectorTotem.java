package ru.ardeon.tost.myEntity;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.tost.Tost;

public class ProtectorTotem extends Totem{
	BukkitRunnable protecttimer = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			timer--;
			World w = a.getWorld();
			Location l = a.getLocation();
			w.playSound(a.getLocation(), Sound.BLOCK_WOOL_FALL, 2, 1.2f);
			List<Entity> ents = a.getNearbyEntities(7, 4, 7);
			AreaEffectCloud cloud = (AreaEffectCloud) w.spawnEntity(a.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(7);
			cloud.setColor(Color.SILVER);
			PotionEffect ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 22, 2);
			for (Entity m: ents)
			{
				if (m instanceof Player)
				{
					Player play = (Player) m;
					ef.apply(play);
					w.spawnParticle(Particle.FLASH, play.getEyeLocation(), 2);
				}
				if (m instanceof Mob)
				{
					Mob mob = (Mob) m;
					Location ml = mob.getLocation();
					w.spawnParticle(Particle.BARRIER, mob.getEyeLocation(), 2);
					mob.setVelocity(ml.toVector().subtract(l.toVector()).normalize().multiply(0.5).setY(0.5));
				}
			}
			if (timer<=0)
			{
				protecttimer.cancel();
				a.remove();
				p.sendTitle("", "Тотем защиты исчез!", 5, 10, 5);
				protecttimer = null;
			}
		}
	};
	public ProtectorTotem(Player p)
	{
		super(p);
		EntityEquipment eq = a.getEquipment();
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
		meta.setColor(Color.BLACK);
		boots.setItemMeta(meta);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		leggings.setItemMeta(meta);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		chestplate.setItemMeta(meta);
		ItemStack helmet = new ItemStack(Material.BLACK_CONCRETE, 1);
		eq.setBoots(boots);
		eq.setLeggings(leggings);
		eq.setChestplate(chestplate);
		eq.setHelmet(helmet);
		protecttimer.runTaskTimer(Tost.getPlugin(), 0, 20);
	}
	public ProtectorTotem()
	{
		super();
	}
}
