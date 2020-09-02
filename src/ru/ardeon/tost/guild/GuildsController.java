package ru.ardeon.tost.guild;

import ru.ardeon.tost.guild.adventurers.AdventurersHall;
import ru.ardeon.tost.guild.miners.MinersHall;

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
