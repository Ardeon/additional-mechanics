package ru.ardeon.additionalmechanics.guild.miners.orescaner;

import org.bukkit.Material;

import ru.ardeon.additionalmechanics.util.ConverterMaterial;

public class OreConverter extends ConverterMaterial {
	@Override
	public String NameOf(Material m) {
		String s="";
		switch (m) {
		case COAL_ORE: {
			s="§8Уголь";
			break;
		}
		case IRON_ORE: {
			s="§7Железо";
			break;
		}
		case GOLD_ORE: {
			s="§6Золото";
			break;
		}
		case LAPIS_ORE: {
			s="§9Лазурит";
			break;
		}
		case EMERALD_ORE: {
			s="§a�?зумруд";
			break;
		}
		case DIAMOND_ORE: {
			s="§bАлмаз";
			break;
		}
		default:
			s="§f-";
			break;
		}
		return s;
	}
	@Override
	public boolean testForMaterial(Material m) {
		if (m!=null&&(m.equals(Material.COAL_ORE)||m.equals(Material.IRON_ORE)||m.equals(Material.GOLD_ORE)||m.equals(Material.LAPIS_ORE)
				||m.equals(Material.EMERALD_ORE)||m.equals(Material.DIAMOND_ORE)
				||m.equals(Material.NETHER_QUARTZ_ORE)||m.equals(Material.NETHER_GOLD_ORE)))
			return true;
		return false;
	}
	@Override
	public Material FromString(String s) {
		Material m = null;
		switch (s) {
		case "§8Уголь": {
			m=Material.COAL_ORE;
			break;
		}
		case "§7Железо": {
			m=Material.IRON_ORE;
			break;
		}
		case "§6Золото": {
			m=Material.GOLD_ORE;
			break;
		}
		case "§9Лазурит": {
			m=Material.LAPIS_ORE;
			break;
		}
		case "§a�?зумруд": {
			m=Material.EMERALD_ORE;
			break;
		}
		case "§bАлмаз": {
			m=Material.DIAMOND_ORE;
			break;
		}
		default:
			break;
		}
		return m;
	}
	@Override
	public String getNext(String s) {
		String next="";
		switch (s) {
		case "§8Уголь": {
			next="§7Железо";
			break;
		}
		case "§7Железо": {
			next="§6Золото";
			break;
		}
		case "§6Золото": {
			next="§9Лазурит";
			break;
		}
		case "§9Лазурит": {
			next="§a�?зумруд";
			break;
		}
		case "§a�?зумруд": {
			next="§bАлмаз";
			break;
		}
		case "§bАлмаз": {
			next="§fВсё";
			break;
		}
		case "§fВсё": {
			next="§8Уголь";
			break;
		}
		default:
			next="§8Уголь";
			break;
		}
		return next;
	}
}
