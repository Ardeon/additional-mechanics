package ru.ardeon.additionalmechanics.randomchest;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandomManager {
	//private ProtocolManager protocolManager;
	AdditionalMechanics plugin;
	
	public RandomManager(AdditionalMechanics plugin) {
		this.plugin = plugin;
		//protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	
	
	public void n(Player player) {
		ItemStack reward = new ItemStack(Material.ACACIA_DOOR);
		RollRunnable roll = new RollRunnable(plugin, player, reward);
		roll.runTaskTimer(plugin, 0, 5);
	}
}
