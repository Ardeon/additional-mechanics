package ru.ardeon.additionalmechanics.randomchest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fakeinv implements CommandExecutor {
	RandomManager rm;
	
	public Fakeinv(RandomManager rm) {
		this.rm = rm;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			if (args.length>=1) {
				p.updateInventory();
				return true;
			}
			rm.n(p);
			return true;
		}
		return false;
	}

}
