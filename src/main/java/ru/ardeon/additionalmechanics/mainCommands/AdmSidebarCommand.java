package ru.ardeon.additionalmechanics.mainCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.ScoreboardVars;

public class AdmSidebarCommand implements CommandExecutor, TabCompleter {

	AdmSidebarCommand(){
		AdditionalMechanics.getPlugin().getServer().getPluginCommand("sidebar").setTabCompleter(this);;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("on")) {
					ScoreboardVars.setVar(player.getName(), "adm_sidebar",1);
					AdditionalMechanics.getPlugin().sideBars.getBar(player).addViewer(player);
					return true;
				}
				if (args[0].equalsIgnoreCase("off")) {
					ScoreboardVars.setVar(player.getName(), "adm_sidebar",0);
					AdditionalMechanics.getPlugin().sideBars.getBar(player).removeViewer(player);
					return true;
				}
			}
			else {
				AdditionalMechanics.getPlugin().sideBars.getBar(player).toggleViewer(player);
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
