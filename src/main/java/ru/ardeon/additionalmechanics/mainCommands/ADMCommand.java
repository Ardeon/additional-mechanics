package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.util.ItemUtil;

import java.util.stream.Collectors;

public class ADMCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) 
	{
		if (args.length==1&&args[0].equalsIgnoreCase("reload"))
		{

			AdditionalMechanics.getPlugin().reload();
			return true;
		}
		if (args.length==2&&args[0].equalsIgnoreCase("item"))
		{
			if(sender instanceof Player){
				Player player = (Player) sender;

				try {
					String itemName = args[1];
					Material material = SettingsLoaderUseableItems.SettingItems.getMaterialOfItem(itemName);
					int modelData = SettingsLoaderUseableItems.SettingItems.getModelOfItem(itemName);
					String skillName = SettingsLoaderUseableItems.SettingItems.getSkillNameOfItem(itemName);
					ItemStack itemStack = new ItemStack(material);
					ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material);
					assert itemMeta != null;
					itemMeta.setCustomModelData(modelData);
					itemStack.setItemMeta(itemMeta);
					ItemUtil.setTag(itemStack, "skill", skillName);
					player.getInventory().addItem(itemStack);
					return true;
				} catch (Exception exception) {
					player.sendRawMessage("такого предмета нет");
					player.sendRawMessage(String.join(",", SettingsLoaderUseableItems.SettingItems.getItems()));
				}
			}

		}
		return false;
	}

}
