package ru.ardeon.tost.guild.miners;

import org.bukkit.entity.Player;

public class PermissonConvert {
	/*
	public static int getradius(Player p) {
		int s=0;
		if (p.hasPermission("tost.radar.power.1")) 
			s=1;
		if (p.hasPermission("tost.radar.power.2")) 
			s=2;
		if (p.hasPermission("tost.radar.power.3")) 
			s=3;
		return s;
	}
	*/
	public static int getextradrop(Player p) {
		int s=0;
		if (p.hasPermission("tost.miner.extradrop.1")) 
			s=1;
		if (p.hasPermission("tost.miner.extradrop.2")) 
			s=2;
		if (p.hasPermission("tost.miner.extradrop.3")) 
			s=3;
		if (p.hasPermission("tost.miner.extradrop.4")) 
			s=4;
		return s;
	}
}
