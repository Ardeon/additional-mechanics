package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class guildregister implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length==2) {
			Player p = Bukkit.getPlayer(args[0]);
			Group group = AdditionalMechanics.getPlugin().getLP().getGroupManager().getGroup(args[1]);
			if (p!=null && group!=null && p.isOnline()) {
				User user = AdditionalMechanics.getPlugin().getLP().getUserManager().getUser(p.getUniqueId());
				user.data().add(InheritanceNode.builder(group).value(true).build());
				AdditionalMechanics.getPlugin().getLP().getUserManager().saveUser(user);
			}
		}
		return true;
	}

}
