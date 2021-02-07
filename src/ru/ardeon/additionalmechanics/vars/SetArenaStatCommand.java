package ru.ardeon.additionalmechanics.vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;

public class SetArenaStatCommand implements CommandExecutor {
	PlayerVarManager uservars;
	SetArenaStatCommand(PlayerVarManager uservars){
		this.uservars = uservars;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==3) {
			Player player = Bukkit.getPlayer(args[0]);
			String uuid = player.getUniqueId().toString().toLowerCase();
			int classID;
			int statID;
			try {
				classID = Integer.parseInt(args[1]);
				statID = Integer.parseInt(args[2]);
				ArenaData arenaData = uservars.getData(uuid).arenaData;
				switch (statID) {
				case 1 :{
					player.sendMessage(""+arenaData.getBoots(classID));
					break;
				}
				case 2 :{
					player.sendMessage(""+arenaData.getLegs(classID));
					break;
				}
				case 3 :{
					player.sendMessage(""+arenaData.getChest(classID));
					break;
				}
				case 4 :{
					player.sendMessage(""+arenaData.getPower(classID, 1));
					break;
				}
				case 5 :{
					player.sendMessage(""+arenaData.getPower(classID, 2));
					break;
				}
				case 6 :{
					player.sendMessage(""+arenaData.getPower(classID, 3));
					break;
				}
				case 7 :{
					player.sendMessage(""+arenaData.getPower(classID, 4));
					break;
				}
				case 8 :{
					player.sendMessage(""+arenaData.getPower(classID, 5));
					break;
				}
				}
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
