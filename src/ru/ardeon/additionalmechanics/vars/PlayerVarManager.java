package ru.ardeon.additionalmechanics.vars;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.SQLite;
import ru.ardeon.additionalmechanics.vars.playerdata.ArenaProgress;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerData;
import ru.ardeon.additionalmechanics.vars.playerdata.Score;

public class PlayerVarManager {
	private HashMap<String, PlayerData> users = new HashMap<String, PlayerData>();
	private SQLite playerPointsbd;
	private BukkitRunnable autosave;
	
	public void adduser(String uuid, PlayerData data) {
		users.put(uuid, data);
	}
	
	public void saveUser(String uuid) {
		Score score = users.get(uuid).score;
		ArenaProgress progress = users.get(uuid).arenaprogress;
		playerPointsbd.saveVars(uuid, score);
		playerPointsbd.saveStats(uuid, progress);
	}
	
	public void saveUsers() {
		Set<String> allusers = users.keySet();
		for (String uuid: allusers){
			saveUser(uuid);
		}
	}
	
	public PlayerData getData(String uuid) {
		return users.getOrDefault(uuid, null);
	}
	
	public int getVar(String uuid, int varID){
		Score score = users.get(uuid).score;
		return score.getVar(varID);
	}
	
	public void setVar(String uuid, int varID, int value){
		Score score = users.get(uuid).score;
		score.setVar(varID, value);
	}
	
	public void addToVar(String uuid, int varID, int value){
		Score score = users.get(uuid).score;
		score.addToVar(varID, value);
	}
	
	public void removeUser(String uuid) {
		saveUser(uuid);
		users.remove(uuid);
	}
	
	public Score getOrCreateUserScore(Player player){
		return playerPointsbd.getOrCreatePlayerScore(player);
	}
	
	public ArenaProgress getOrCreateUserStats(Player player){
		return playerPointsbd.getOrCreatePlayerStats(player);
	}
	
	PlayerVarManager(SQLite playerPointsbd, AdditionalMechanics plugin){
		this.playerPointsbd = playerPointsbd;
		plugin.getServer().getPluginManager().registerEvents(new JoinListener(this), plugin);
		plugin.getServer().getPluginCommand("getvar").setExecutor(new GetVarCommand(this));
		plugin.getServer().getPluginCommand("setvar").setExecutor(new SetVarCommand(this));
		plugin.getServer().getPluginCommand("addtovar").setExecutor(new AddToVarCommand(this));
		plugin.getServer().getPluginCommand("addarenastat").setExecutor(new AddArenaStatCommand(this));
		plugin.getServer().getPluginCommand("getarenastat").setExecutor(new SetArenaStatCommand(this));
		autosave = new BukkitRunnable() {
			@Override
			public void run() {
				saveUsers();
			}
		};
		autosave.runTaskTimerAsynchronously(plugin, 500, 6000);
	}
}
