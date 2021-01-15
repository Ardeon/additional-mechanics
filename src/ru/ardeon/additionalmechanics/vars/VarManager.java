package ru.ardeon.additionalmechanics.vars;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.SQLite;

public class VarManager {
	private int currentDonate;
	private int needDonate;
	private AdditionalMechanics plugin;
	private FileConfiguration cfg;
	private ConfigurationSection d;
	public SQLite playerPointsbd;
	public UservarManager uservars;
	
	public SQLite getPointsBD() {
		return playerPointsbd;
	}
	
	public int getCurrentDonate() {
		return currentDonate;
	}
	
	public int getNeedDonate() {
		return needDonate;
	}
	
	public void addDonate(int rub) {
		currentDonate += rub;
		d.set("current", currentDonate);
		//plugin.configLoader.saveYamls();
	}
	
	public VarManager(AdditionalMechanics plugin){
		this.plugin = plugin;
		cfg = this.plugin.configLoader.getVars();
		d = cfg.getConfigurationSection("donate");
		currentDonate = d.getInt("current");
		needDonate = d.getInt("need");
		playerPointsbd = new SQLite(plugin);
		uservars = new UservarManager(playerPointsbd, plugin);
	}
}
