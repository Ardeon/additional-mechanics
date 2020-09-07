package ru.ardeon.additionalmechanics.guild.adventurers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RideCommand implements CommandExecutor {
	AdventurersHall hall;
	
	public RideCommand(AdventurersHall hall) {
		this.hall = hall;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==2) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p!=null&&p.isOnline()) {
				switch (args[0].toLowerCase()) {
				case "horse":{
					hall.getGuestHorseController().CreateHorse(p, EntityType.HORSE, 20, 0.33, 0.5);
					break;
				}
				default:
					break;
				}
				
				return true;
			}
			
			}
		return false;
	}

}
