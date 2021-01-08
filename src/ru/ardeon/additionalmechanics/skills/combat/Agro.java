package ru.ardeon.additionalmechanics.skills.combat;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.ardeon.additionalmechanics.skills.ItemSkill;
import ru.ardeon.additionalmechanics.util.LevelOfPermission;

public class Agro implements ItemSkill{
	public static Agro agro = new Agro();
	@Override
	public void execute(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.RED_DYE))) 		
		{
			int power = LevelOfPermission.getLevel(player, "adm.agro", 5);
			int cd = LevelOfPermission.getLevel(player, "adm.agrocd", 7);
			player.setCooldown(Material.RED_DYE, 1200 - cd * 60);
			AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(2+power);
			cloud.setColor(Color.RED);
			List<Entity> mobs = player.getNearbyEntities(10, 2 + power, 10);
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
			PotionEffect ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300 + power * 50, counter);
			ef.apply(player);
			world.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 2, 0.7f);
			world.playSound(player.getLocation(), Sound.BLOCK_NETHER_WART_BREAK, 2, 1);
		}
	}

}
