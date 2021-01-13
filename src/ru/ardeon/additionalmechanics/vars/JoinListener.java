package ru.ardeon.additionalmechanics.vars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.ardeon.additionalmechanics.util.sql.SQLite;

public class JoinListener implements Listener {
	private SQLite playerPointsbd;
	
	JoinListener(SQLite playerPointsbd){
		this.playerPointsbd = playerPointsbd;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		playerPointsbd.newPlayer(e.getPlayer());
	}
}
