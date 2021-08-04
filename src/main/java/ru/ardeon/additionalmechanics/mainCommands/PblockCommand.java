package ru.ardeon.additionalmechanics.mainCommands;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PblockCommand implements CommandExecutor {
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			if (args.length==1)
			{
				AdditionalMechanics.getPlugin().getConfigLoader().getConfigBlocks().set(args[0]+".location", p.getLocation());
				AdditionalMechanics.getPlugin().getLogger().info(p.getLocation().toString());
				return true;
			}
			
		}
		return false;
	}

}
