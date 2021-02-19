package ru.ardeon.additionalmechanics.vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestGetMetaCommand implements CommandExecutor {
	PlayerVarManager uservars;
	TestGetMetaCommand(PlayerVarManager uservars){
		this.uservars = uservars;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==2) {
			Player player = Bukkit.getPlayer(args[0]);
			String string = "";
			string = uservars.getData(player).getCMIstring(args[1]);
			Integer number = uservars.getData(player).getCMIint(args[1]);
			if (number != null)
				string = string + " - " + number.toString();
			player.sendMessage(string);
			return true;
		}
		return false;
	}

}
