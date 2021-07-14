package ru.ardeon.additionalmechanics.mainCommands;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.ardeon.additionalmechanics.util.TextUtilRGB;

public class GradientCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length>=3)
		{
			String hex1 = args[0];
			String hex2 = args[1];
			Color color1 = TextUtilRGB.colorFromStr(hex1);
			Color color2 = TextUtilRGB.colorFromStr(hex2);
			String string = args[2];
			for(int i = 3; i < args.length; i++)
				string = string.concat(" ").concat(args[i]);
			Bukkit.spigot().broadcast(TextUtilRGB.toSet(string, color1, color2));
			return true;
		}
		return false;
	}

}
