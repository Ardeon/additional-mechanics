package ru.ardeon.additionalmechanics.vars;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderVars;
import ru.ardeon.additionalmechanics.util.sql.SQLite;

public class VarManager {
	private int currentDonate;
	private int needDonate;
	private AdditionalMechanics plugin;
	private FileConfiguration cfg;
	private ConfigurationSection d;
	public SQLite playerPointsbd;
	public PlayerVarManager uservars;
	
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

	public void clearDonate() {
		currentDonate = 0;
		d.set("current", currentDonate);
	}
	
	public VarManager(AdditionalMechanics plugin){
		this.plugin = plugin;
		currentDonate = SettingsLoaderVars.SettingVars.DONATE_CURRENT.getInt();
		needDonate = SettingsLoaderVars.SettingVars.DONATE_NEED.getInt();
		playerPointsbd = new SQLite(plugin);
		uservars = new PlayerVarManager(playerPointsbd, plugin);
	}
}
