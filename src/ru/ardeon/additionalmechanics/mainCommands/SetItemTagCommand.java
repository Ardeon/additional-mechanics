package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.util.ItemUtil;

public class SetItemTagCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length == 1 && sender instanceof Player) {
			Player player = (Player) sender;
			ItemStack item = player.getInventory().getItemInMainHand();
			return ItemUtil.setTag(item, args[0]);
		}
		return false;
	}

}
