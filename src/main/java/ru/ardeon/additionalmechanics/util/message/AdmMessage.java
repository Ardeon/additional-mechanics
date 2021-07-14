package ru.ardeon.additionalmechanics.util.message;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AdmMessage {
	public static AdmMessage getInstance() {
		if (admMessage!=null)
			return admMessage;
		else {
			return new AdmMessage();
		}
	}
	private static AdmMessage admMessage;
	
	private String eventMessage = "§8[§bСобытие§8]§r ";
	private String itemSkillMessage = "§8[§7Предмет§8]§r ";
	
	public AdmMessage() {
		admMessage = this;
	}
	
	public void broadcastEvent(String message) {
		Bukkit.broadcastMessage(eventMessage + message);
	}
	
	public void itemSkillMessage(Player player, String message) {
		player.sendMessage(itemSkillMessage + message);
	}
}
