package ru.ardeon.additionalmechanics.skills.interact;

import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Pushes {
	public static InteractSkill forceJump = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.FORCE_JUMP_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				world.spawnParticle(Particle.CLOUD, player.getLocation(), 7);
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.FORCE_JUMP_COOLDOWN.getInt());
				Vector j = player.getLocation().getDirection().normalize().multiply(1.9);
				j.setY(0.4);
				player.setVelocity(j);
				world.playSound(player.getLocation(), Sound.ENTITY_PARROT_FLY, 2, 1);
				world.playSound(player.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 2, 1.2f);
			}
		}
	};
	public static InteractSkill hook = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.HOOK_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{

				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.HOOK_COOLDOWN.getInt());
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
