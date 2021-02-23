package ru.ardeon.additionalmechanics.skills.interact;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.myEntity.HealTotem;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class WeatherTotem {
	public static InteractSkill weatherTotem = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.BLAZE_POWDER))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(6, 4);
				player.setCooldown(Material.BLAZE_POWDER, 300 - cd * 10);
				world.getWeatherDuration();
				world.setWeatherDuration(world.getWeatherDuration() + 2000);
				new HealTotem(player);
				e.setCancelled(true);
			}
		}
	};
}
