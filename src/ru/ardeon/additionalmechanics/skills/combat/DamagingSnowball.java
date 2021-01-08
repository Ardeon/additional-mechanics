package ru.ardeon.additionalmechanics.skills.combat;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.skills.ItemSkill;
import ru.ardeon.additionalmechanics.util.LevelOfPermission;

public class DamagingSnowball implements ItemSkill {
	public static DamagingSnowball damagersnowball = new DamagingSnowball();
	@Override
	public void execute(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.STICK))) 
		{
			int cd = LevelOfPermission.getLevel(player, "adm.slowstickcd", 7);
			Class<Snowball> ball = Snowball.class;
			Snowball r = e.getPlayer().launchProjectile(ball);
			r.setShooter(e.getPlayer());
			r.addScoreboardTag("snowball");
			player.setCooldown(Material.STICK, 40 - cd * 3);
			world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
			world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
		}
	}

}
