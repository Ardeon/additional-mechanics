package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.util.ItemUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ADMCommand implements CommandExecutor, TabCompleter {
	private final List<String> ARG1 = Arrays.asList("reload","item");

	public ADMCommand(PluginCommand command) {
		command.setTabCompleter(this);
	}

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

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (args.length==1){
			return ARG1;
		}
		if (args.length==2 && args[0].equalsIgnoreCase("item")){
			return new ArrayList<>(SettingsLoaderUseableItems.SettingItems.getItems());
		}
		//commandSender.sendMessage(args.length+"");
		return null;
	}
}
