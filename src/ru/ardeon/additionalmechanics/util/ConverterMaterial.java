package ru.ardeon.additionalmechanics.util;

import java.util.Set;

import org.bukkit.Material;

public abstract class ConverterMaterial {
	int r;
	public abstract String NameOf(Material m);
	public abstract boolean testForMaterial(Material m);
	
	public abstract Set<Material> FromString(String s);
	
}
