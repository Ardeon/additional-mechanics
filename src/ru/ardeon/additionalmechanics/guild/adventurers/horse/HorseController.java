package ru.ardeon.additionalmechanics.guild.adventurers.horse;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class HorseController {
	public HashSet<Entity> horses = new HashSet<Entity>();
	
	public HorseController() {
		AdditionalMechanics.getPlugin().getServer().getPluginManager().registerEvents(new Horselistener(this), AdditionalMechanics.getPlugin());
	}
	
	public void CreateHorse(Entity creator, EntityType type, double hp, double speed, double jump) {
		
		Entity horse = creator.getWorld().spawnEntity(creator.getLocation(), type);
		if (type.equals(EntityType.SKELETON_HORSE)) {
			PotionEffect eff = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2);
			eff.apply((LivingEntity) horse);
			eff = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 2);
			eff.apply((LivingEntity) horse);
		}
		horse.addPassenger(creator);
		AbstractHorse h = (AbstractHorse) horse;
		h.setMaxDomestication(1);
		h.setDomestication(1);
		h.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		h.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
		h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
		h.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jump);
		horses.add(horse);
	}
}
