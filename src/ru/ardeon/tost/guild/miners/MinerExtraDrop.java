package ru.ardeon.tost.guild.miners;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinerExtraDrop {
	public static ItemStack extradrop(Material m, int extra) {
		double r = Math.random()*10*extra;
		ItemStack add=null;
		switch (m) {
		case COAL_ORE: {
			add = new ItemStack(Material.COAL, (int)(r/10));
			break;
		}
		case IRON_ORE: {
			add = new ItemStack(Material.IRON_NUGGET, (int)(r/3));
			break;
		}
		case GOLD_ORE: {
			add = new ItemStack(Material.GOLD_NUGGET, (int)(r/3));
			break;
		}
		case LAPIS_ORE: {
			add = new ItemStack(Material.LAPIS_LAZULI, (int)(r));
			break;
		}
		case EMERALD_ORE: {
			add = new ItemStack(Material.EMERALD, (int)(r/10));
			break;
		}
		case NETHER_QUARTZ_ORE: {
			add = new ItemStack(Material.QUARTZ, (int)(r/6));
			break;
		}
		case NETHER_GOLD_ORE: {
			add = new ItemStack(Material.GOLD_NUGGET, (int)(r/3));
			break;
		}
		case DIAMOND_ORE: {
			add = new ItemStack(Material.DIAMOND, (int)(r/10));
			break;
		}
		default:
			
			break;
		}
		return add;
	}
	
	@SuppressWarnings("incomplete-switch")
	public static ItemStack drop(Material m) {
		switch (m) {
		case IRON_ORE:
			return new ItemStack(Material.IRON_INGOT, 1);
		case GOLD_ORE: 
			return new ItemStack(Material.GOLD_INGOT, 1);
		}
		return null;
	}
	
	@SuppressWarnings("incomplete-switch")
	public static boolean test(Material m) {
		switch (m) {
		case IRON_ORE:
			return true;
		case GOLD_ORE: 
			return true;
		}
		return false;
	}
}
