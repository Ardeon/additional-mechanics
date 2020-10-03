package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class TostCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) 
	{
		// TODO Auto-generated method stub

		if (args.length==1&&args[0].equalsIgnoreCase("reload"))
		{
			AdditionalMechanics.getPlugin().configLoader.loadYamls();
			AdditionalMechanics.getPlugin().loadBlocks();
			return true;
		}
		return false;
	}

}
