package ru.ardeon.additionalmechanics.skills.combat;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.skills.ItemSkill;
import ru.ardeon.additionalmechanics.util.LevelOfPermission;

public class FireballWithEffect implements ItemSkill{
	public static FireballWithEffect fireballWithEffect = new FireballWithEffect();
	@Override
	public void execute(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.QUARTZ))) 
		{
			int cd = LevelOfPermission.getLevel(player, "adm.quartzcd", 7);
			Class<SmallFireball> ball = SmallFireball.class;
			SmallFireball r = e.getPlayer().launchProjectile(ball);
			r.setShooter(e.getPlayer());
			r.addScoreboardTag("fireball");
			player.setCooldown(Material.QUARTZ, 40 - cd * 3);
			world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 2);
		}
	}

}
