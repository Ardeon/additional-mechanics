package ru.ardeon.additionalmechanics.guild.adventurers.hook;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HookCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			BatmanHook.useHook(p);
			return true;
		}
		return false;
	}

}
