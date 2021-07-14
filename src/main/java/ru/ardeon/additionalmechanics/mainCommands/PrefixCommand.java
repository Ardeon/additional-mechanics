package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;

public class PrefixCommand implements CommandExecutor {
	LuckPerms lp;
	public PrefixCommand(LuckPerms lp) {
		this.lp = lp;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==1 && lp!=null && sender instanceof Player) {
			Player player = (Player) sender;
			String test = args[0];
			for (int i = 0; i < 10; i++) {
				test = test.replaceAll("[&][" + i + "]", "");
			}
			test = test.replaceAll("[&][aA]", "");
			test = test.replaceAll("[&][bB]", "");
			test = test.replaceAll("[&][cC]", "");
			test = test.replaceAll("[&][dD]", "");
			test = test.replaceAll("[&][eE]", "");
			test = test.replaceAll("[&][fF]", "");
			test = test.replaceAll("[&][kK]", "");
			test = test.replaceAll("[&][lL]", "");
			test = test.replaceAll("[&][mM]", "");
			test = test.replaceAll("[&][nN]", "");
			test = test.replaceAll("[&][oO]", "");
			test = test.replaceAll("[&][rR]", "");
			if (args[0].length() < 24 && test.length() < 8) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = "lp u " + player.getName() + " meta set prefix " + args[0];
				Bukkit.dispatchCommand(console, command);
				return true;
			}
		}
		return false;
	}

}
