package ru.ardeon.additionalmechanics.mainCommands.roll;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class flipcmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			String flip = "";
			if (Math.random()>0.5)
				flip = "§a© - Орёл!";
			else
				flip = "§9℗ - Решка!";
			List<Entity> ent = p.getNearbyEntities(15, 15, 15);
			for (Entity e : ent) {
				if (e instanceof Player) {
					Player player = (Player) e;
					player.sendMessage("§3● §6§l"+p.getDisplayName()+"§7 подкинул монетку. "+flip);
				}
			}
			p.sendMessage("§3● §6§l"+p.getDisplayName()+"§7 подкинул монетку. "+flip);
			p.sendTitle(flip, "", 10, 20, 10);
			return true;
		}
		return false;
	}

}
