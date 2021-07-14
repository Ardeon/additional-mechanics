package ru.ardeon.additionalmechanics.vars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DonateAddCommand implements CommandExecutor {
	
	private VarManager manager;
	
	public DonateAddCommand(VarManager manager) {
		this.manager = manager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length>=1)
		{
			if (args[0].equalsIgnoreCase("clear")) {
				manager.clearDonate();
			}
			else {
				try {
					int x = Integer.parseInt(args[0]);
					manager.addDonate(x);
				}
				catch(Exception e){
					e.printStackTrace();
					return false;
				}

			}
			return true;
		}
		return false;
	}

}
