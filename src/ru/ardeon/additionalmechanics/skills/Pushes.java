package ru.ardeon.additionalmechanics.skills;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import ru.ardeon.additionalmechanics.util.LevelOfPermission;

public class Pushes {
	public static ItemSkill forceJump = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.FEATHER))) 
			{
				world.spawnParticle(Particle.CLOUD, player.getLocation(), 7);
				player.setCooldown(Material.FEATHER, 40);
				Vector j = player.getLocation().getDirection().normalize().multiply(2.4);
				j.setY(0.4);
				player.setVelocity(j);
				world.playSound(player.getLocation(), Sound.ENTITY_PARROT_FLY, 2, 1);
				world.playSound(player.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 2, 1.2f);
			}
		}
	};
	public static ItemSkill hook = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.TRIPWIRE_HOOK))) 
			{
				int cd = LevelOfPermission.getLevel(player, "adm.hookcd", 7);
				player.setCooldown(Material.TRIPWIRE_HOOK, 100 - cd * 5);
				world.spawnParticle(Particle.END_ROD, player.getEyeLocation(), 10);
				Arrow ar = player.launchProjectile(Arrow.class);
				ar.addScoreboardTag("hook");
				world.playSound(player.getLocation(), Sound.ITEM_CROSSBOW_QUICK_CHARGE_3, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_FISHING_BOBBER_THROW, 2, 2);
			}
			e.setCancelled(true);
		}
	};
}
