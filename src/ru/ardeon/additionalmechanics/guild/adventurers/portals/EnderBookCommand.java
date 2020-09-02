package ru.ardeon.additionalmechanics.guild.adventurers.portals;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderBookCommand implements CommandExecutor {
	Portals portals;
	
	public EnderBookCommand(Portals portals) {
		this.portals = portals;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			if (args.length==1) {
				
				return true;
			}
			else {
				UUID id = p.getUniqueId();
				PortalMenu menu = portals.get(id);
				p.openInventory(menu.getInventory());
				return true;
			}
		}
		return false;
	}

}
