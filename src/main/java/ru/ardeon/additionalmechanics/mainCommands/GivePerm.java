package ru.ardeon.additionalmechanics.mainCommands;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class GivePerm implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length==2)
		{
			Player p = Bukkit.getPlayer(args[0]);
			if (p!=null && p.isOnline())
			{
				User user = AdditionalMechanics.getPlugin().getLP().getUserManager().getUser(p.getUniqueId());
				user.data().add(Node.builder(args[1]).value(true).build());
				AdditionalMechanics.getPlugin().getLP().getUserManager().saveUser(user);
			}
		}
		return true;
	}

}
