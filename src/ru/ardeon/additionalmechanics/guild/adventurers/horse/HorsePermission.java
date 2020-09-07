package ru.ardeon.additionalmechanics.guild.adventurers.horse;

import org.bukkit.entity.Player;

public class HorsePermission {
	public static int getHp(Player p) {
		int i = 0;
		if (p.hasPermission("guilds.adventurers.horse.hp.1"))
			i=1;
		if (p.hasPermission("guilds.adventurers.horse.hp.2"))
			i=2;
		if (p.hasPermission("guilds.adventurers.horse.hp.3"))
			i=3;
		if (p.hasPermission("guilds.adventurers.horse.hp.4"))
			i=4;
		if (p.hasPermission("guilds.adventurers.horse.hp.5"))
			i=5;
		if (p.hasPermission("guilds.adventurers.horse.hp.6"))
			i=6;
		return i;
	}
	
	public static int getSpeed(Player p) {
		int i = 0;
		if (p.hasPermission("guilds.adventurers.horse.speed.1"))
			i=1;
		if (p.hasPermission("guilds.adventurers.horse.speed.2"))
			i=2;
		if (p.hasPermission("guilds.adventurers.horse.speed.3"))
			i=3;
		if (p.hasPermission("guilds.adventurers.horse.speed.4"))
			i=4;
		if (p.hasPermission("guilds.adventurers.horse.speed.5"))
			i=5;
		if (p.hasPermission("guilds.adventurers.horse.speed.6"))
			i=6;
		if (p.hasPermission("guilds.adventurers.horse.speed.7"))
			i=7;
		if (p.hasPermission("guilds.adventurers.horse.speed.8"))
			i=8;
		if (p.hasPermission("guilds.adventurers.horse.speed.9"))
			i=9;
		if (p.hasPermission("guilds.adventurers.horse.speed.10"))
			i=10;
		return i;
	}
	
	public static int getJump(Player p) {
		int i = 0;
		if (p.hasPermission("guilds.adventurers.horse.jump.1"))
			i=1;
		if (p.hasPermission("guilds.adventurers.horse.jump.2"))
			i=2;
		if (p.hasPermission("guilds.adventurers.horse.jump.3"))
			i=3;
		if (p.hasPermission("guilds.adventurers.horse.jump.4"))
			i=4;
		if (p.hasPermission("guilds.adventurers.horse.jump.5"))
			i=5;
		if (p.hasPermission("guilds.adventurers.horse.jump.6"))
			i=6;
		if (p.hasPermission("guilds.adventurers.horse.jump.7"))
			i=7;
		if (p.hasPermission("guilds.adventurers.horse.jump.8"))
			i=8;
		if (p.hasPermission("guilds.adventurers.horse.jump.9"))
			i=9;
		if (p.hasPermission("guilds.adventurers.horse.jump.10"))
			i=10;
		return i;
	}
	
	public static boolean getCanUse(Player p) {
		if (p.hasPermission("guilds.adventurers.horse.use"))
			return true;
		return false;
	}
}
