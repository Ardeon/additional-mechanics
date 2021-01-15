package ru.ardeon.additionalmechanics.vars;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.SQLite;

public class UservarManager {
	private HashMap<String,Score> users = new HashMap<String,Score>();
	private SQLite playerPointsbd;
	
	public void adduser(String uuid, Score score) {
		users.put(uuid, score);
	}
	
	public void saveUser(String uuid) {
		Score score = users.get(uuid);
		playerPointsbd.saveVars(uuid, score);
	}
	
	public void saveUsers() {
		Set<String> allusers = users.keySet();
		for (String uuid: allusers){
			saveUser(uuid);
		}
	}
	
	public int getVar(String uuid, int varID){
		Score score = users.get(uuid);
		return score.getVar(varID);
	}
	
	public void setVar(String uuid, int varID, int value){
		Score score = users.get(uuid);
		score.setVar(varID, value);
	}
	
	public void addToVar(String uuid, int varID, int value){
		Score score = users.get(uuid);
		score.addToVar(varID, value);
	}
	
	public void removeUser(String uuid) {
		saveUser(uuid);
		users.remove(uuid);
	}
	
	public Score getOrCreateUser(Player player){
		return playerPointsbd.getOrCreatePlayer(player);
	}
	
	UservarManager(SQLite playerPointsbd, AdditionalMechanics plugin){
		this.playerPointsbd = playerPointsbd;
		plugin.getServer().getPluginManager().registerEvents(new JoinListener(this), plugin);
		plugin.getServer().getPluginCommand("getvar").setExecutor(new GetVarCommand(this));
		plugin.getServer().getPluginCommand("setvar").setExecutor(new SetVarCommand(this));
		plugin.getServer().getPluginCommand("addtovar").setExecutor(new AddToVarCommand(this));
	}
}
