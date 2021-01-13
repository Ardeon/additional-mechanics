package ru.ardeon.additionalmechanics.vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.util.sql.SQLite;

public class SetVarCommand implements CommandExecutor {
	SQLite playerPointsbd;
	SetVarCommand(SQLite playerPointsbd){
		this.playerPointsbd = playerPointsbd;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==3) {
			Player player = Bukkit.getPlayer(args[0]);
			String uuid = player.getUniqueId().toString().toLowerCase();
			int varID;
			int value;
			try {
				varID = Integer.parseInt(args[1]);
				value = Integer.parseInt(args[2]);
				playerPointsbd.setVar(uuid, varID, value);
				return true;
			}
			catch (NumberFormatException e)
			{
				return false;
			}
		}
		return false;
	}

}
