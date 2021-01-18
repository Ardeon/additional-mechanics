package ru.ardeon.additionalmechanics.vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.vars.playerdata.ArenaProgress;

public class AddArenaStatCommand implements CommandExecutor {
	PlayerVarManager uservars;
	AddArenaStatCommand(PlayerVarManager uservars){
		this.uservars = uservars;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==4) {
			Player player = Bukkit.getPlayer(args[0]);
			String uuid = player.getUniqueId().toString().toLowerCase();
			int classID;
			int statID;
			int value;
			try {
				classID = Integer.parseInt(args[1]);
				statID = Integer.parseInt(args[2]);
				value = Integer.parseInt(args[3]);
				ArenaProgress arenaprogress = uservars.getData(uuid).arenaprogress;
				switch (statID) {
				case 1 :{
					arenaprogress.upgradeBoots(classID, value);
					break;
				}
				case 2 :{
					arenaprogress.upgradeLegs(classID, value);
					break;
				}
				case 3 :{
					arenaprogress.upgradeChest(classID, value);
					break;
				}
				case 4 :{
					arenaprogress.upgradePower(classID, 1, value);
					break;
				}
				case 5 :{
					arenaprogress.upgradePower(classID, 2, value);
					break;
				}
				case 6 :{
					arenaprogress.upgradePower(classID, 3, value);
					break;
				}
				case 7 :{
					arenaprogress.upgradePower(classID, 4, value);
					break;
				}
				case 8 :{
					arenaprogress.upgradePower(classID, 5, value);
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
