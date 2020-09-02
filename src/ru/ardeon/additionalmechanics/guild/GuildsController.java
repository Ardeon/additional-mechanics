package ru.ardeon.additionalmechanics.guild;

import ru.ardeon.additionalmechanics.guild.adventurers.AdventurersHall;
import ru.ardeon.additionalmechanics.guild.miners.MinersHall;

public class GuildsController {
	AdventurersHall ah = new AdventurersHall();
	MinersHall mh = new MinersHall();
	
	public GuildsController() {
		
	}
	
	public AdventurersHall getAdventurers() {
		return ah;
	}
	
	public MinersHall getMiners() {
		return mh;
	}
}
