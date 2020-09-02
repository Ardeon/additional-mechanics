package ru.ardeon.additionalmechanics.util;

import org.bukkit.Material;

public abstract class Filter {
	protected Material mat;
	
	public Filter(Material mat) {
		this.mat = mat;
	}
	
	public Filter() {
		this.mat = null;
	}
	
	public abstract boolean test(Material tested);

}
