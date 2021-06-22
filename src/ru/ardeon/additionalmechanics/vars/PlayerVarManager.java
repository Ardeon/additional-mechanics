package ru.ardeon.additionalmechanics.vars;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.AchievementDB;
import ru.ardeon.additionalmechanics.util.sql.AchievementDBSQLite;
import ru.ardeon.additionalmechanics.util.sql.SQLite;
import ru.ardeon.additionalmechanics.vars.cmidata.CMIData;
import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;
import ru.ardeon.additionalmechanics.vars.playerdata.MoneyData;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerAchievement;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerData;

public class PlayerVarManager {
	private final HashMap<String, PlayerData> users = new HashMap<String, PlayerData>();
	private final SQLite playerPointsbd;
	//public AchievementDB achievementDB;
	private ArrayList<PlayerAchievement> playerAchievementTop = new ArrayList<>();
	private BukkitRunnable autosave;
	public CMIData cmidata = new CMIData();
	public static PlayerVarManager instance;
	public static Comparator<PlayerAchievement> comparator = new Comparator<PlayerAchievement>(){
		public int compare(PlayerAchievement o1, PlayerAchievement o2) {
			return o2.getValue()-o1.getValue();
		}
	};
	
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

	public ArrayList<PlayerAchievement> getPlayerAchievementTop(){
		return playerAchievementTop;
	}

	public void setPlayerAchievementTop(ArrayList<PlayerAchievement> top){
		top.sort(comparator);
		playerAchievementTop = top;
	}

	public void refreshTop(){
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective obj = scoreboard.getObjective("bac_advancements");
		Set<String> ent = scoreboard.getEntries();
		ArrayList<PlayerAchievement> playerAchievementTop = new ArrayList<>();
		for(String s : ent) {
			playerAchievementTop.add(new PlayerAchievement(s,obj.getScore(s).getScore()));
			//AdditionalMechanics.getPlugin().getLogger().info(""+obj.getScore(s).getScore());
		}
		setPlayerAchievementTop(playerAchievementTop);
	}
	
	public void saveUsers() {
		Set<String> allusers = users.keySet();
		for (String uuid: allusers){
			saveUser(uuid);
		}
	}
	
	public PlayerData getData(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
		return users.getOrDefault(uuid, null);
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
		//achievementDB = new AchievementDBSQLite(plugin);
		plugin.getServer().getPluginManager().registerEvents(new JoinListener(this), plugin);
		plugin.getServer().getPluginCommand("getvar").setExecutor(new GetVarCommand(this));
		plugin.getServer().getPluginCommand("setvar").setExecutor(new SetVarCommand(this));
		plugin.getServer().getPluginCommand("addtovar").setExecutor(new AddToVarCommand(this));
		plugin.getServer().getPluginCommand("addarenastat").setExecutor(new AddArenaStatCommand(this));
		plugin.getServer().getPluginCommand("getarenastat").setExecutor(new SetArenaStatCommand(this));
		plugin.getServer().getPluginCommand("testgetmeta").setExecutor(new TestGetMetaCommand(this));
		plugin.getServer().getPluginCommand("achievementpoints").setExecutor(new AchievementPointsCommand(this));
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
