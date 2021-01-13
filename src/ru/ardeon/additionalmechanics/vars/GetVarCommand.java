package ru.ardeon.additionalmechanics.vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.util.sql.SQLite;

public class GetVarCommand implements CommandExecutor {
	SQLite playerPointsbd;
	GetVarCommand(SQLite playerPointsbd){
		this.playerPointsbd = playerPointsbd;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==2) {
			Player player = Bukkit.getPlayer(args[0]);
			String uuid = player.getUniqueId().toString().toLowerCase();
			int varID;
			try {
				varID = Integer.parseInt(args[1]);
				playerPointsbd.getVar(uuid, varID);
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
