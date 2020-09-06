package ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.util.Filter;
import ru.ardeon.additionalmechanics.util.PermissionTester;

public class BountyFilter extends Filter implements PermissionTester {

	public BountyFilter() {
		super();
	}
	
	public BountyFilter(Material m) {
		super(m);
	}
	
	@Override
	public boolean test(Material tested) {
		if (mat!=null) 
			return mat.equals(tested);
		else 
			return new BountyConverter().testForMaterial(tested);
	}

	@Override
	public boolean testPermission(Player p) {
		// TODO Auto-generated method stub
		return true;
	}

}
