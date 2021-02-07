package ru.ardeon.additionalmechanics.vars;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.SQLite;
import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;
import ru.ardeon.additionalmechanics.vars.playerdata.MoneyData;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerData;

public class PlayerVarManager {
	private HashMap<String, PlayerData> users = new HashMap<String, PlayerData>();
	private SQLite playerPointsbd;
	private BukkitRunnable autosave;
	public static PlayerVarManager instance;
	
	public static PlayerVarManager getInstance() {
		return instance;
	}
	
	public void adduser(String uuid, PlayerData data) {
		users.put(uuid, data);
	}
	
	public void saveUser(String uuid) {
		MoneyData moneyData = users.get(uuid).moneyData;
		ArenaData arenaData = users.get(uuid).arenaData;
		playerPointsbd.moneyTable.saveData(uuid, moneyData);
		playerPointsbd.arenatable.saveData(uuid, arenaData);
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
		MoneyData moneyData = users.get(uuid).moneyData;
		return moneyData.getVar(varID);
	}
	
	public void setVar(String uuid, int varID, int value){
		MoneyData moneyData = users.get(uuid).moneyData;
		moneyData.setVar(varID, value);
	}
	
	public void addToVar(String uuid, int varID, int value){
		MoneyData moneyData = users.get(uuid).moneyData;
		moneyData.addToVar(varID, value);
	}
	
	public void removeUser(String uuid) {
		saveUser(uuid);
		users.remove(uuid);
	}
	
	public MoneyData getOrCreateUserScore(String uuid){
		return playerPointsbd.moneyTable.getOrCreate(uuid);
	}
	
	public ArenaData getOrCreateUserStats(String uuid){
		return playerPointsbd.arenatable.getOrCreate(uuid);
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
		instance = this;
	}
}
