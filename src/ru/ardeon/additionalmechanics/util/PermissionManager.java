package ru.ardeon.additionalmechanics.util;

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.permissions.DefaultPermissions;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class PermissionManager {
	
	static String advHorseHp = "guilds.adventurers.horse.hp";
	static String advHorseSpeed = "guilds.adventurers.horse.speed";
	static String advHorseJump = "guilds.adventurers.horse.jump";
	static String advRadar = "guilds.adventurers.radar";

	static public void regAll() {
		PermissionCreate(advHorseHp);
		PermissionCreate(advHorseSpeed);
		PermissionCreate(advHorseJump);
		PermissionCreate(advRadar);
	}
	
	static void PermissionCreate(String part){
		ConfigurationSection section = AdditionalMechanics.getPlugin().configLoader.getConfig().getConfigurationSection(part);
		if (section!=null) {
			Set<String> keys = section.getKeys(false);
			for (String value : keys) {
				DefaultPermissions.registerPermission(part+"\\."+value, "");
			}
		}
		//
	}
	
	
}
