package ru.ardeon.additionalmechanics.util;

import org.bukkit.entity.Player;

public class LevelOfPermission {
	public static int getLevel(Player p, String perm, int max) {
		int l=0;
		for(int i = 0; i < max; i++) {
			if (p.hasPermission(perm+"\\."+i)) 
				l=i;
		}
		return l;
	}
}
