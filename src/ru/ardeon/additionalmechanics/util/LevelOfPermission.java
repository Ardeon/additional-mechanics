package ru.ardeon.additionalmechanics.util;

import org.bukkit.entity.Player;
@Deprecated
public class LevelOfPermission {
	public static int getLevel(Player p, String perm, int max) {
		int l=0;
		for(int i = 0; i < max; i++) {
			if (p.isPermissionSet(perm+'.'+i)&&p.hasPermission(perm+'.'+i)) {
				l=i;
			}
		}
		//AdditionalMechanics.getPlugin().getLogger().info(""+l);
		return l;
	}
}
