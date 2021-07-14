package ru.ardeon.additionalmechanics.util;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtil {

	public static boolean testForLore(ItemStack item) {
		if (item != null) {
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean testForSkillTag(ItemStack item) {
		String skill = getTag(item, "skill");
		return !skill.equalsIgnoreCase("");
	}
	
	public static boolean setTag(ItemStack item, String tag, String value) {
		//
		NamespacedKey key = new NamespacedKey(AdditionalMechanics.getPlugin(), tag);
		Material material = item.getType();
		if (!material.equals(Material.AIR)) {
			ItemMeta itemMeta;
			if (item.hasItemMeta())
				itemMeta = item.getItemMeta();
			else
				itemMeta = Bukkit.getItemFactory().getItemMeta(material);
			itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
			item.setItemMeta(itemMeta);
			return true;
		}
		return false;
		//
	}
	
	public static String getTag(ItemStack item, String tag) {
		NamespacedKey key = new NamespacedKey(AdditionalMechanics.getPlugin(), tag);
		if (item!=null&&item.hasItemMeta()) {
			ItemMeta itemMeta = item.getItemMeta();
			PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			String value = container.getOrDefault(key, PersistentDataType.STRING, "");
			return value;
		}
		return "";
	}
}
