package ru.ardeon.additionalmechanics.guild.miners;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class MinersHall {

	public MinersHall() {
		AdditionalMechanics.getPlugin().getServer().getPluginManager().registerEvents(new EventsMiner(), AdditionalMechanics.getPlugin());
	}
	
}
