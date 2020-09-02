package ru.ardeon.additionalmechanics.guild.adventurers.horse;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.guild.adventurers.AdventurersHall;

public class HorseCommand implements CommandExecutor {
	AdventurersHall hall;
	
	public HorseCommand(AdventurersHall hall) {
		this.hall = hall;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) 
	{
		// TODO Auto-generated method stub
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			hall.getGuestHorseController().CreateHorse(p, EntityType.HORSE, 20, 0.2, 0.8);
			return true;
		}
		return false;
	}

}
