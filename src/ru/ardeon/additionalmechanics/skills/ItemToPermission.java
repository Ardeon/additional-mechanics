package ru.ardeon.additionalmechanics.skills;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.PetConverter;

public class ItemToPermission {
	public static ItemSkill pet = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem();
			List<String> lore = item.getItemMeta().getLore();
			//World world = player.getWorld();
			e.setCancelled(true);
			String string10 = "";
			if (lore.size()>=10) {
				string10 = lore.get(9);
				
				/*try {
					string10 = string10.substring(2);
					//AdditionalMechanics.getPlugin().getLogger().info(string2);
				} catch (Exception ex) {
					
				}
				*/
			}
			int petN = PetConverter.PetNameToID(string10);
			/*
			try {
				petN = Integer.parseInt(string2);
			}
			catch (NumberFormatException ex)
			{
			   petN = 0;
			}*/
			if (petN!=0) {
				String permission = "pet."+petN;
				boolean hasPet =  player.hasPermission(permission);
				LuckPerms lp = AdditionalMechanics.getPlugin().getLP();
				
				if (!(player.hasCooldown(Material.PLAYER_HEAD))) 
				{
					if (!hasPet && lp!=null) {
						User user = lp.getPlayerAdapter(Player.class).getUser(player);
						user.data().add(Node.builder(permission).value(true).build());
						lp.getUserManager().saveUser(user);
						player.setCooldown(Material.PLAYER_HEAD, 50);
						e.getItem().setAmount(e.getItem().getAmount()-1);
					}
					else {
						player.sendMessage("У вас уже есть этот пет");
					}
				}
			}
		}
	};
}
