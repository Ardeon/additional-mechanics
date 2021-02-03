package ru.ardeon.additionalmechanics.util;

import org.bukkit.inventory.ItemStack;

public class PetConverter {
	public static int PetNameToID(ItemStack item) {
		int id=0;
		String number = ItemUtil.getTag(item, "pet");
		try {
			id = Integer.parseInt(number);
		}
		catch (NumberFormatException e)
		{

		}
		return id;
	}
}
