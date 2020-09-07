package ru.ardeon.additionalmechanics.guild.adventurers;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.guild.adventurers.hook.HookCommand;
import ru.ardeon.additionalmechanics.guild.adventurers.hook.HookListener;
import ru.ardeon.additionalmechanics.guild.adventurers.horse.HorseCommand;
import ru.ardeon.additionalmechanics.guild.adventurers.horse.HorseController;
import ru.ardeon.additionalmechanics.guild.adventurers.portals.PortalBooklistener;
import ru.ardeon.additionalmechanics.guild.adventurers.portals.Portals;

public class AdventurersHall {
	HorseController guildHorseController = new HorseController();
	HorseController guestHorseController = new HorseController();
	Portals portalManager = new Portals();
	
	public AdventurersHall(){
		AdditionalMechanics.getPlugin().getServer().getPluginManager().registerEvents(new AdventurersListener(this), AdditionalMechanics.getPlugin());
		AdditionalMechanics.getPlugin().getServer().getPluginManager().registerEvents(new PortalBooklistener(), AdditionalMechanics.getPlugin());
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
	
	public Portals getPortalManager() {
		return portalManager;
	}
}
