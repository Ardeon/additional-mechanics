package ru.ardeon.additionalmechanics.mainCommands;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ADMCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) 
	{
		if (args.length==1&&args[0].equalsIgnoreCase("reload"))
		{
			//AdditionalMechanics.getPlugin().configLoader.loadYamls();
			AdditionalMechanics.getPlugin().reload();
			return true;
		}
		return false;
	}

}
