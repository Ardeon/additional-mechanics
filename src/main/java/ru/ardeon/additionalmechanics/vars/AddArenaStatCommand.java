package ru.ardeon.additionalmechanics.vars;

import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
				ArenaData arenaData = uservars.getData(uuid).arenaData;
				switch (statID) {
				case 1 :{
					arenaData.upgradeBoots(classID, value);
					break;
				}
				case 2 :{
					arenaData.upgradeLegs(classID, value);
					break;
				}
				case 3 :{
					arenaData.upgradeChest(classID, value);
					break;
				}
				case 4 :{
					arenaData.upgradePower(classID, 1, value);
					break;
				}
				case 5 :{
					arenaData.upgradePower(classID, 2, value);
					break;
				}
				case 6 :{
					arenaData.upgradePower(classID, 3, value);
					break;
				}
				case 7 :{
					arenaData.upgradePower(classID, 4, value);
					break;
				}
				case 8 :{
					arenaData.upgradePower(classID, 5, value);
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
