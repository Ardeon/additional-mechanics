package ru.ardeon.additionalmechanics.skills.shootbow;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import ru.ardeon.additionalmechanics.skills.template.ShootBowSkill;

public class ShootBowSkills {
	static public ShootBowSkill blastArrow = new ShootBowSkill() {
		@Override
		public void execute(EntityShootBowEvent e) {
			LivingEntity owner = e.getEntity();
			if (owner instanceof Player) {
				Player player = (Player) owner;
				Entity arrow = e.getProjectile();
				if (player.isSneaking() && !(player.hasCooldown(Material.FIREWORK_ROCKET))) {
					player.setCooldown(Material.FIREWORK_ROCKET, 600);
					arrow.addScoreboardTag("explosiveArrow");
				}
				else {
					double x = Math.random() * 100;
					if (x>96) {
						arrow.addScoreboardTag("explosiveArrow");
					}
				}
			}
		}
	};
}
