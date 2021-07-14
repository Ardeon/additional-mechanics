package ru.ardeon.additionalmechanics.guild.miners.orescaner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ru.ardeon.additionalmechanics.util.ConverterMaterial;
import org.bukkit.Material;

public class OreConverter extends ConverterMaterial {
	Set<Material> dedaultset = new HashSet<Material>(Arrays.asList(Material.COAL_ORE,Material.IRON_ORE,Material.GOLD_ORE,Material.LAPIS_ORE,
			Material.EMERALD_ORE,Material.DIAMOND_ORE,Material.NETHER_QUARTZ_ORE,Material.NETHER_GOLD_ORE));
	
	@Override
	public String NameOf(Material m) {
		String s="";
		switch (m) {
		case COAL_ORE: {
			s="1";
			break;
		}
		case IRON_ORE: {
			s="2";
			break;
		}
		case GOLD_ORE: {
			s="3";
			break;
		}
		case LAPIS_ORE: {
			s="4";
			break;
		}
		case EMERALD_ORE: {
			s="5";
			break;
		}
		case DIAMOND_ORE: {
			s="6";
			break;
		}
		default:
			s="";
			break;
		}
		return s;
	}
	@Override
	public boolean testForMaterial(Material m) {
		if (dedaultset.contains(m))
			return true;
		return false;
	}
	@Override
	public Set<Material> FromString(String s) {
		Set<Material> set = new HashSet<Material>();
		switch (s) {
		case "1": {
			set.add(Material.COAL_ORE);
			break;
		}
		case "2": {
			set.add(Material.IRON_ORE);
			break;
		}
		case "3": {
			set.add(Material.GOLD_ORE);
			break;
		}
		case "4": {
			set.add(Material.LAPIS_ORE);
			break;
		}
		case "5": {
			set.add(Material.EMERALD_ORE);
			break;
		}
		case "6": {
			set.add(Material.DIAMOND_ORE);
			break;
		}
		default:
			set.addAll(dedaultset);
			break;
		}
		return set;
	}
}
