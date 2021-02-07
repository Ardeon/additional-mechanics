package ru.ardeon.additionalmechanics.vars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;
import ru.ardeon.additionalmechanics.vars.playerdata.MoneyData;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerData;

public class JoinListener implements Listener {
	PlayerVarManager playerVars;
	
	JoinListener(PlayerVarManager playerVars){
		this.playerVars = playerVars;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString().toLowerCase();
		MoneyData moneyData = playerVars.getOrCreateUserScore(player);
		ArenaData arenaData = playerVars.getOrCreateUserStats(player);
		PlayerData data = new PlayerData(moneyData, arenaData);
		playerVars.adduser(uuid, data);
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString().toLowerCase();
		playerVars.removeUser(uuid);
	}
}
