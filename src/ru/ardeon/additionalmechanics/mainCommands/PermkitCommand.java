package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class PermkitCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==2)
		{
			Player p = Bukkit.getPlayer(args[0]);
			String mainGroup = args[1];
			AdditionalMechanics t = AdditionalMechanics.getPlugin();
			FileConfiguration config = t.configLoader.getConfigPermkit();
			if (config.contains(mainGroup, false)) {
				ConfigurationSection groups = config.getConfigurationSection(mainGroup);
				for(String minorGroup : groups.getKeys(false)) {
					ConfigurationSection kits = groups.getConfigurationSection(minorGroup);
					String kitForPlayer = "none";
					int weight = -1;
					for(String key : kits.getKeys(false)) {
						ConfigurationSection kit = kits.getConfigurationSection(key);
						int kitWeight = kit.getInt("weight", 0);
						if (kitWeight >= weight) {
							String perm = kit.getString("permission");
							//AdditionalMechanics.getPlugin().getLogger().info(perm);
							if (p.hasPermission(perm)) {
								kitForPlayer = kit.getString("kit", key);
								weight = kitWeight;
							}
						}
					}
					
					if (kitForPlayer.equals("none")) {
						ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						String command = "cmi kit "+kitForPlayer+" "+p.getName();
						Bukkit.dispatchCommand(console, command);
					}
			    }
			}
			
			return true;
		}
		return false;
	}

}
