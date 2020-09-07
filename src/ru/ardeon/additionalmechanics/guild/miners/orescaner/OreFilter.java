package ru.ardeon.additionalmechanics.guild.miners.orescaner;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.util.Filter;
import ru.ardeon.additionalmechanics.util.PermissionTester;

public class OreFilter extends Filter implements PermissionTester {

	public OreFilter() {
		super();
	}
	
	public OreFilter(Material m) {
		super(m);
	}
	
	@Override
	public boolean test(Material tested) {
		if (mat!=null) 
			return mat.equals(tested);
		else
			return new OreConverter().testForMaterial(tested);
	}
	
	@Override
	public boolean testPermission(Player p) {
		if (mat!=null)
		switch (mat) {
		case COAL_ORE: {
			if (p.hasPermission("additionalmechanics.miner.filter.coal"))
				return true;
			break;
		}
		case IRON_ORE: {
			if (p.hasPermission("additionalmechanics.miner.filter.iron"))
				return true;
			break;
		}
		case GOLD_ORE: {
			if (p.hasPermission("additionalmechanics.miner.filter.gold"))
				return true;
			break;
		}
		case LAPIS_ORE: {
			if (p.hasPermission("additionalmechanics.miner.filter.lazurit"))
				return true;
			break;
		}
		case EMERALD_ORE: {
			if (p.hasPermission("additionalmechanics.miner.filter.emerald"))
				return true;
			break;
		}
		case DIAMOND_ORE: {
			if (p.hasPermission("additionalmechanics.miner.filter.diamond"))
				return true;
			break;
		}
		default:
			if (p.hasPermission("additionalmechanics.miner.filter.other"))
				return true;
			break;
		}
		else
			return true;
		return false;
	}
}
