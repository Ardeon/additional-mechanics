package ru.ardeon.tost.guild.adventurers.bountyscaner;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import ru.ardeon.tost.util.Filter;
import ru.ardeon.tost.util.PermissionTester;

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
			return tested.equals(mat);
		else 
			return true;
	}

	@Override
	public boolean testPermission(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
