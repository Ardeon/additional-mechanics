package ru.ardeon.additionalmechanics.guild.adventurers.horse;

import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class HorsePermission {
	public static double getHp(Player p) {
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
		double hp = AdditionalMechanics.getPlugin().configLoader.getConfig().getDouble("guilds.adventurers.horse.hp."+i, 9);
		return hp;
	}
	
	public static double getSpeed(Player p) {
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
		double speed = AdditionalMechanics.getPlugin().configLoader.getConfig().getDouble("guilds.adventurers.horse.speed."+i, 0.2);
		return speed;
	}
	
	public static double getJump(Player p) {
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
		double jump = AdditionalMechanics.getPlugin().configLoader.getConfig().getDouble("guilds.adventurers.horse.jump."+i, 0.6);
		return jump;
	}
	
	public static boolean getCanUse(Player p) {
		if (p.hasPermission("guilds.adventurers.horse.use"))
			return true;
		return false;
	}
}
