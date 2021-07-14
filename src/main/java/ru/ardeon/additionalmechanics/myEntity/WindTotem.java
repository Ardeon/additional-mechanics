package ru.ardeon.additionalmechanics.myEntity;

import java.util.List;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
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

public class WindTotem extends Totem{
	BukkitRunnable windtimer = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			timer--;
			World w = a.getWorld();
			Location l = a.getLocation();
			w.playSound(l, Sound.BLOCK_NOTE_BLOCK_BIT, 2, 1.2f);
			List<Entity> mobs = a.getNearbyEntities(5, 4, 5);
			PotionEffect ef = new PotionEffect(PotionEffectType.LEVITATION, 18, 0);
			ef.apply(a);
			AreaEffectCloud cloud = (AreaEffectCloud) w.spawnEntity(a.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(5);
			cloud.setColor(Color.WHITE);
			for (Entity m: mobs)
			{
				if (m instanceof Mob)
				{
					Mob mob = (Mob) m;
					Location ml = mob.getLocation();
					ef.apply(mob);
					mob.setVelocity(l.toVector().subtract(ml.toVector()).normalize().multiply(0.2));
					w.spawnParticle(Particle.END_ROD, mob.getEyeLocation(), 2);
				}
			}
			if (timer<=0)
			{
				windtimer.cancel();
				a.remove();
				p.sendTitle("", "Тотем ветра исчез!", 5, 10, 5);
				windtimer = null;
			}
		}
	};
	public WindTotem(Player p)
	{
		super(p);
		EntityEquipment eq = a.getEquipment();
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
		meta.setColor(Color.GRAY);
		boots.setItemMeta(meta);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		leggings.setItemMeta(meta);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		chestplate.setItemMeta(meta);
		ItemStack helmet = new ItemStack(Material.GRAY_CONCRETE, 1);
		eq.setBoots(boots);
		eq.setLeggings(leggings);
		eq.setChestplate(chestplate);
		eq.setHelmet(helmet);
		windtimer.runTaskTimer(AdditionalMechanics.getPlugin(), 0, 20);
	}
	public WindTotem()
	{
		super();
	}
	
}
