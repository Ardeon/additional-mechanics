package ru.ardeon.tost.guild.miners;

import ru.ardeon.tost.Tost;

public class MinersHall {

	public MinersHall() {
		Tost.getPlugin().getServer().getPluginManager().registerEvents(new EventsMiner(), Tost.getPlugin());
	}
	
}
