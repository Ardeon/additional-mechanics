package ru.ardeon.additionalmechanics.skills.interactentity;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.ardeon.additionalmechanics.skills.template.InteractEntitySkill;

public class InteractEntitySkills {
	static public InteractEntitySkill soul = new InteractEntitySkill() {
		@Override
		public void execute(PlayerInteractEntityEvent e) {
			Player player = e.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			Entity target = e.getRightClicked();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.SLIME_BALL))&& (target instanceof Player)) 
			{
				player.setCooldown(Material.SLIME_BALL, 50);
				Player targetplayer = (Player) target;
				world.playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 1, 1.2f);
				PotionEffect ef = new PotionEffect(PotionEffectType.HEAL, 1, 2);
				ef.apply(targetplayer);
				ef = new PotionEffect(PotionEffectType.SPEED, 120, 1);
				ef.apply(targetplayer);
				ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 2);
				ef.apply(targetplayer);
				item.setAmount(item.getAmount()-1);
			}
		}
	};
}
