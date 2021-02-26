package ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

import ru.ardeon.additionalmechanics.util.ConverterMaterial;

public class BountyConverter extends ConverterMaterial {
	Set<Material> dedaultset = new HashSet<Material>(Arrays.asList(Material.END_PORTAL_FRAME, Material.CHEST, Material.SPAWNER, Material.DISPENSER));
	
	@Override
	public String NameOf(Material m) {
		String s="";
		switch (m) {
		case END_PORTAL_FRAME: {
			s="1";
			break;
		}
		case CHEST: {
			s="2";
			break;
		}
		case SPAWNER: {
			s="3";
			break;
		}
		case DISPENSER: {
			s="4";
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
			set.add(Material.END_PORTAL_FRAME);
			break;
		}
		case "2": {
			set.add(Material.CHEST);
			break;
		}
		case "3": {
			set.add(Material.SPAWNER);
			break;
		}
		case "4": {
			set.add(Material.DISPENSER);
			break;
		}
		default:
			set.addAll(dedaultset);
			break;
		}
		return set;
	}

}
