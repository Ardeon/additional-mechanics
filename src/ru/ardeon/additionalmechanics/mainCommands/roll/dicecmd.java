package ru.ardeon.additionalmechanics.mainCommands.roll;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class dicecmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if ((sender instanceof Player)) 
		{
			Player p = (Player) sender;
			int n = (int) (Math.random()*6)+1;
			String q = "";
			switch (n) {
				case 1:
				{
					q="§3● §7- один.";
					break;
				}
				case 2:
				{
					q="§3●● §7- два.";
					break;
				}
				case 3:
				{
					q="§3●●● §7- три.";
					break;
				}
				case 4:
				{
					q="§3●●●● §7- четыре.";
					break;
				}
				case 5:
				{
					q="§3●●●●● §7- пять.";
					break;
				}
				case 6:
				{
					q="§3●●●●●● §7- шесть.";
					break;
				}
				default:
				{
					q="§3●●●●●● §7- шесть.";
					break;
				}
			}
			List<Entity> ent = p.getNearbyEntities(15, 15, 15);
			for (Entity e : ent) {
				if (e instanceof Player) {
					Player player = (Player) e;
					player.sendMessage("§3● §6§l"+p.getDisplayName()+"§7 бросил кубик. "+q);
				}
			}
			p.sendMessage("§3● §6§l"+p.getDisplayName()+"§7 бросил кубик. "+q);
			p.sendTitle(""+q, "", 10, 20, 10);
			return true;
		}
		return false;
	}
}
