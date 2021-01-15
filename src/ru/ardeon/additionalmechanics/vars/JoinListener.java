package ru.ardeon.additionalmechanics.vars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
	UservarManager playerVars;
	
	JoinListener(UservarManager playerVars){
		this.playerVars = playerVars;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString().toLowerCase();
		Score score = playerVars.getOrCreateUser(player);
		playerVars.adduser(uuid, score);
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString().toLowerCase();
		playerVars.removeUser(uuid);
	}
}
