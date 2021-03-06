package ru.ardeon.additionalmechanics.mainCommands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.util.ItemUtil;

public class GetItemTagCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player && args.length >= 1) {
			Player player = (Player) sender;
			ItemStack item = player.getInventory().getItemInMainHand();
			player.sendMessage(ItemUtil.getTag(item, args[0]));
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}

}
