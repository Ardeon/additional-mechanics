package ru.ardeon.additionalmechanics.guild.adventurers;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.guild.adventurers.hook.HookCommand;
import ru.ardeon.additionalmechanics.guild.adventurers.hook.HookListener;
import ru.ardeon.additionalmechanics.guild.adventurers.horse.HorseCommand;
import ru.ardeon.additionalmechanics.guild.adventurers.horse.HorseController;

public class AdventurersHall {
	HorseController guildHorseController = new HorseController();
	HorseController guestHorseController = new HorseController();
	
	public AdventurersHall(){
		AdditionalMechanics.getPlugin().getServer().getPluginCommand("horsetest").setExecutor(new HorseCommand(this));
		AdditionalMechanics.getPlugin().getServer().getPluginCommand("rideon").setExecutor(new RideCommand(this));
		AdditionalMechanics.getPlugin().getServer().getPluginCommand("launchhook").setExecutor(new HookCommand());
		AdditionalMechanics.getPlugin().getServer().getPluginManager().registerEvents(new HookListener(), AdditionalMechanics.getPlugin());
	}
	
	public HorseController getGuildHorseController() {
		return guildHorseController;
	}
	
	public HorseController getGuestHorseController() {
		return guestHorseController;
	}
}
