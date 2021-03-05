package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class AdmSidebarCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player && args.length >= 1) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("on"))
				AdditionalMechanics.getPlugin().sideBar.addViewer(player);
			else
				AdditionalMechanics.getPlugin().sideBar.removeViewer(player);
			return true;
		}
		return false;
	}

}
