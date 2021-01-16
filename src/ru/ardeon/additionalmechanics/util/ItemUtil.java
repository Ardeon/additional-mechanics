package ru.ardeon.additionalmechanics.util;

import org.bukkit.inventory.ItemStack;

public class ItemUtil {

	public static boolean testForLore(ItemStack item) {
		if (item != null) {
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				return true;
			}
		}
		return false;
	}
	
}
