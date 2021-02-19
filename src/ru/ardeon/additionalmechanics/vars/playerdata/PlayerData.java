package ru.ardeon.additionalmechanics.vars.playerdata;

import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.vars.cmidata.CMIData;

public class PlayerData {
	public MoneyData moneyData;
	public ArenaData arenaData;
	public AbilityAndStatsData abilityAndStatsData;
	private CMIData cmidata;
	private Player player;
	
	public PlayerData(Player player,MoneyData moneyData, ArenaData arenaData, CMIData cmidata) {
		this.moneyData = moneyData;
		this.arenaData = arenaData;
		this.cmidata = cmidata;
		this.player = player;
	}
	
	public String getCMIstring(String field) {
		return cmidata.data(player, field);
	}
	
	public Integer getCMIint(String field) {
		Integer value;
		try {
			String string = getCMIstring(field);
			double valuedouble = Double.parseDouble(string);
			value = (int) valuedouble;
		}
		catch (NumberFormatException e)
		{
			value = null;
		}
		return value;
	}
}
