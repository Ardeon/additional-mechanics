package ru.ardeon.additionalmechanics.util;

import org.bukkit.Material;

public abstract class ConverterMaterial {
	int r;
	public abstract String NameOf(Material m);
	public abstract boolean testForMaterial(Material m);
	
	public abstract Material FromString(String s);
	
	public abstract String getNext(String s);
}
