package ru.ardeon.additionalmechanics.mechanics.builds;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestBuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==4&&sender instanceof Player) {
			try {
				int x1, y1, z1;
				x1 = Integer.parseInt(args[0]);
				y1 = Integer.parseInt(args[1]);
				z1 = Integer.parseInt(args[2]);
				String name = args[3];
				Player p = (Player) sender;
				World w = p.getWorld();
				Block block = w.getBlockAt(x1, y1, z1);
				//AdditionalMechanics.getPlugin().getLogger().info(block.getType().toString());
				//Build build = 
				
				return AdditionalMechanics.bm.builds.get(name).test(block);
			}
			catch(Exception e){
				return false;
			}
		}
		return false;
	}

}
