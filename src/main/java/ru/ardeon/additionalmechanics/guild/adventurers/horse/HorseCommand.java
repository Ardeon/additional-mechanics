package ru.ardeon.additionalmechanics.guild.adventurers.horse;

import ru.ardeon.additionalmechanics.guild.adventurers.AdventurersHall;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class HorseCommand implements CommandExecutor {
	AdventurersHall hall;
	
	public HorseCommand(AdventurersHall hall) {
		this.hall = hall;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			hall.getHorseController().CreateHorse(p, EntityType.HORSE, 20, 0.2, 0.8);
			return true;
		}
		return false;
	}

}
