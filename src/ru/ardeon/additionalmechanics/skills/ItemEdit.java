package ru.ardeon.additionalmechanics.skills;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemEdit {

	public static boolean BowBuff(PlayerInteractEvent e)//legacy
	{
		Player player = e.getPlayer();
		int n = player.getInventory().first(Material.BOW);
		if (n!=-1)
		{
			ItemStack item = player.getInventory().getItem(n);
			if (item.getItemMeta().hasLore()) {
				ItemMeta met = item.getItemMeta();
				List<String> lor = met.getLore();
				lor.add("§aВзрывная стрела§a");
				lor.add("§7Выпущеные стрелы имеют шанс взорваться§7");
				lor.add("§dСтрела выпущеная из приседа§d");
				lor.add("§dгарантированно взорвётся§d");
				lor.add("§dПерезарядка - §d §730 сек§7");
				met.setLore(lor);
				item.setItemMeta(met);
				return true;
			}
			return false;
		}
		return false;
	}
	
}
