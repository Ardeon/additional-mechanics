package ru.ardeon.additionalmechanics.skills.combat;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.skills.ItemSkill;
import ru.ardeon.additionalmechanics.util.LevelOfPermission;

public class Explosion implements ItemSkill{
	public static Explosion explosion = new Explosion();
	@Override
	public void execute(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.FIREWORK_STAR))) 
		{
			int power = LevelOfPermission.getLevel(player, "adm.explosion", 3);
			int cd = LevelOfPermission.getLevel(player, "adm.explosioncd", 7);
			player.setCooldown(Material.FIREWORK_STAR, 800 - cd * 40);
			world.createExplosion(player.getLocation(), 2 + power, false, false, player);
		}
	}

}
