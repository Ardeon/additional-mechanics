package ru.ardeon.additionalmechanics.mechanics.tempterritory;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestRegionCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			if (args.length==1)
			{
				
				return true;
			}
			
		}
		return false;
	}

}
