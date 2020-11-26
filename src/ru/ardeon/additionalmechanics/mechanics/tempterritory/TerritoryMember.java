package ru.ardeon.additionalmechanics.mechanics.tempterritory;

import java.util.UUID;

public class TerritoryMember {
	public long endDate;
	public UUID id;
	public String territory;
	
	public TerritoryMember(int days, UUID id, String territory) {
		this.endDate = System.currentTimeMillis() + 86400000L * days;
		this.id = id;
		this.territory = territory;
	}
}
