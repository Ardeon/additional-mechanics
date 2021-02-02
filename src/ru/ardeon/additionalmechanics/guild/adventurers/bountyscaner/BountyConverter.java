package ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner;

import org.bukkit.Material;

import ru.ardeon.additionalmechanics.util.ConverterMaterial;
@Deprecated
public class BountyConverter extends ConverterMaterial {

	@Override
	public String NameOf(Material m) {
		String s="";
		switch (m) {
		case END_PORTAL_FRAME: {
			s="§8Рамка портала";
			break;
		}
		case CHEST: {
			s="§7Сундук";
			break;
		}
		case SPAWNER: {
			s="§6Спавнер";
			break;
		}
		case DISPENSER: {
			s="§9Раздатчик";
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
		if (m!=null&&(m.equals(Material.END_PORTAL_FRAME)||m.equals(Material.CHEST)||m.equals(Material.SPAWNER)||m.equals(Material.DISPENSER)))
			return true;
		return false;
	}
	
	@Override
	public Material FromString(String s) {
		Material m = null;
		switch (s) {
		case "§8Рамка портала": {
			m=Material.END_PORTAL_FRAME;
			break;
		}
		case "§7Сундук": {
			m=Material.CHEST;
			break;
		}
		case "§6Спавнер": {
			m=Material.SPAWNER;
			break;
		}
		case "§9Раздатчик": {
			m=Material.DISPENSER;
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
		case "§8Рамка портала": {
			next="§7Сундук";
			break;
		}
		case "§7Сундук": {
			next="§6Спавнер";
			break;
		}
		case "§6Спавнер": {
			next="§9Раздатчик";
			break;
		}
		case "§9Раздатчик": {
			next="§fВсё";
			break;
		}
		case "§fВсё": {
			next="§8Рамка портала";
			break;
		}
		default:
			next="§8Рамка портала";
			break;
		}
		return next;
	}

}
