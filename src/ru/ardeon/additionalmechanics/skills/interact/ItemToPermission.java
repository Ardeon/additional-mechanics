package ru.ardeon.additionalmechanics.skills.interact;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.util.PetConverter;

public class ItemToPermission {
	public static InteractSkill pet = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem();
			int petN = PetConverter.PetNameToID(item);
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
			e.setCancelled(true);
		}
	};
}
