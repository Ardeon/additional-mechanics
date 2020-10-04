package ru.ardeon.additionalmechanics.skills.souls;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class SoulSlowManager {
	public HashMap<Player, Integer> souls = new HashMap<Player, Integer>();
	
	public void applyDebuff(Player p) {
		int power = souls.getOrDefault(p, 0);
		
	}
}
