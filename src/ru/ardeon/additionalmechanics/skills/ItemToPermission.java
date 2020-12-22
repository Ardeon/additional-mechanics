package ru.ardeon.additionalmechanics.skills;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class ItemToPermission {
	public static void PetGet(PlayerInteractEvent e, List<String> lore)
	{
		Player player = e.getPlayer();
		//World world = player.getWorld();
		
		String string2 = "";
		if (lore.size()>=2) {
			string2 = lore.get(1);
			try {
				string2 = string2.substring(2);
				AdditionalMechanics.getPlugin().getLogger().info(string2);
			} catch (Exception ex) {
				
			}
			
		}
		int petN;
		try {
		   petN = Integer.parseInt(string2);
		}
		catch (NumberFormatException ex)
		{
		   petN = 0;
		}
		if (petN!=0) {
			String permission = "pet."+petN;
			boolean hasPet =  player.hasPermission(permission);
			LuckPerms lp = AdditionalMechanics.getPlugin().getLP();
			
			if (!(player.hasCooldown(Material.PLAYER_HEAD)) && !hasPet && lp!=null) 
			{
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
		e.setCancelled(true);
	}
}
