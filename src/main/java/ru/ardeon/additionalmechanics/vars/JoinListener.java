package ru.ardeon.additionalmechanics.vars;

import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;
import ru.ardeon.additionalmechanics.vars.playerdata.MoneyData;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
	PlayerVarManager playerVars;
	
	JoinListener(PlayerVarManager playerVars){
		this.playerVars = playerVars;
		for (Player player : Bukkit.getOnlinePlayers()) {
			registerPlayer(player);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		registerPlayer(player);
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString().toLowerCase();
		playerVars.removeUser(uuid);
	}
	
	void registerPlayer(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
		MoneyData moneyData = playerVars.getOrCreateUserScore(uuid);
		ArenaData arenaData = playerVars.getOrCreateUserStats(uuid);
		PlayerData data = new PlayerData(player, moneyData, arenaData, playerVars.cmidata);
		playerVars.adduser(uuid, data);
	}
}
