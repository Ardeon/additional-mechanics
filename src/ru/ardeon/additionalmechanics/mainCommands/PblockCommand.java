package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class PblockCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			if (args.length==1)
			{
				AdditionalMechanics.getPlugin().configLoader.getConfigBlocks().set(args[0]+".location", p.getLocation());
				AdditionalMechanics.getPlugin().getLogger().info(p.getLocation().toString());
				return true;
			}
			
		}
		return false;
	}

}
