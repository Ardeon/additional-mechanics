package ru.ardeon.additionalmechanics.mainCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class AdmSidebarCommand implements CommandExecutor, TabCompleter {

	AdmSidebarCommand(){
		AdditionalMechanics.getPlugin().getServer().getPluginCommand("admsidebar").setTabCompleter(this);;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player && args.length >= 1) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("on")) {
				AdditionalMechanics.getPlugin().sideBar.addViewer(player);
				return true;
			}
				
			if (args[0].equalsIgnoreCase("off")) {
				AdditionalMechanics.getPlugin().sideBar.removeViewer(player);
				return true;
			}
			
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		List<String> tabComplete = new ArrayList<>();
		tabComplete.add("on");
		tabComplete.add("off");
		if (args.length == 1) {
			return tabComplete;
		}
		
		return null;
	}

}
