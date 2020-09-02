package ru.ardeon.tost.guild.adventurers;

import ru.ardeon.tost.Tost;
import ru.ardeon.tost.guild.adventurers.horse.HorseCommand;
import ru.ardeon.tost.guild.adventurers.horse.HorseController;
import ru.ardeon.tost.guild.adventurers.portals.PortalBooklistener;
import ru.ardeon.tost.guild.adventurers.portals.Portals;

public class AdventurersHall {
	HorseController guildHorseController = new HorseController();
	HorseController guestHorseController = new HorseController();
	Portals portalManager = new Portals();
	
	public AdventurersHall(){
		Tost.getPlugin().getServer().getPluginManager().registerEvents(new AdventurersListener(this), Tost.getPlugin());
		Tost.getPlugin().getServer().getPluginManager().registerEvents(new PortalBooklistener(), Tost.getPlugin());
		Tost.getPlugin().getServer().getPluginCommand("horsetest").setExecutor(new HorseCommand(this));
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
