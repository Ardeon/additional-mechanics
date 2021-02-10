package ru.ardeon.additionalmechanics.vars.playerdata;

public class PlayerData {
	public MoneyData moneyData;
	public ArenaData arenaData;
	public AbilityAndStatsData abilityAndStatsData;
	
	public PlayerData(MoneyData moneyData, ArenaData arenaData) {
		this.moneyData = moneyData;
		this.arenaData = arenaData;
	}
}
