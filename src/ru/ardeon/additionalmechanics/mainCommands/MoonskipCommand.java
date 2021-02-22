package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoonskipCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length > 0) {
			World world = Bukkit.getWorld(args[0]);
			return skip(world);
		}
		if ((sender instanceof Player))
		{
			Player p = (Player) sender;
			World world = p.getWorld();
			return skip(world);
		}
		return false;
	}
	
	private boolean skip(World world) {
		if (world != null) {
			long time = world.getFullTime();
			world.setFullTime(time + 24000);
			return true;
		}
		else {
			return false;
		}
	}

}
