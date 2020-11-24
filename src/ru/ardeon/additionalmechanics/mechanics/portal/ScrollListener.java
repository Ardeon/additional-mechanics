package ru.ardeon.additionalmechanics.mechanics.portal;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.mechanics.builds.Build;

public class ScrollListener implements Listener {
	@EventHandler
	public void onPlayerUseEmptyScroll(PlayerInteractEvent e) 
	{
		//Player player = e.getPlayer();e.getHand().equals(EquipmentSlot.HAND) && 
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				ItemStack item = e.getItem();
				if (item != null 
						&& item.getItemMeta().hasLore()
						&& item.getItemMeta().getLore()
						.get(0).equals("§aПустой свиток§a")) {
					Player p = e.getPlayer();
					//e.getPlayer().sendMessage("1");
					if (AdditionalMechanics.bm.builds.containsKey("portal")) {
						Build build = AdditionalMechanics.bm.builds.get("portal");
						Block clicked = e.getClickedBlock();
						int c1, c2, c3;
						c1 = clicked.getX();
						c2 = clicked.getY();
						c3 = clicked.getZ();
						Block start = clicked.getLocation().clone().add(-1, 0, -1).getBlock();
						if (build.test(start)) {
							item.setAmount(item.getAmount()-1);
							e.getPlayer().sendMessage("ок");
							ItemStack scroll = new ItemStack(Material.PAPER, 1);
							ItemMeta meta = scroll.getItemMeta();
							List<String> li = Arrays.asList("§aportal scroll§a","§r"+ c1+" "+c2+" "+c3,"§r"+ p.getWorld().getName());
							meta.setLore(li);
							meta.setDisplayName("§rСвиток телепорта");
							scroll.setItemMeta(meta);
							if (p.getInventory().firstEmpty()==-1)
								p.getWorld().dropItem(p.getLocation(), scroll);
							else
								p.getInventory().addItem(scroll);
						}
						else {
							e.getPlayer().sendMessage("-");
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerUseScroll(PlayerInteractEvent e) 
	{
		//Player player = e.getPlayer();e.getHand().equals(EquipmentSlot.HAND) && 
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR))
			{
				ItemStack item = e.getItem();
				if (item != null 
						&& item.getItemMeta().hasLore()
						&& item.getItemMeta().getLore()
						.get(0).equals("§aportal scroll§a")
						&& item.getItemMeta().getLore().size()>2) {
					Player p = e.getPlayer();
					//e.getPlayer().sendMessage("1");
					if (AdditionalMechanics.bm.builds.containsKey("portal")) {
						Build build = AdditionalMechanics.bm.builds.get("portal");
						int c1, c2, c3;
						List<String> lore = item.getItemMeta().getLore();
						String[] xyz = lore.get(1).split(" ");
						if (xyz.length!=3) {
							p.sendMessage("error");
							return;
						}
						c1 = Integer.parseInt(xyz[0]);
						c2 = Integer.parseInt(xyz[1]);
						c3 = Integer.parseInt(xyz[2]);
						World w = Bukkit.getWorld(lore.get(2));
						Block target = w.getBlockAt(c1, c2, c3);
						Block start = target.getLocation().clone().add(-1, 0, -1).getBlock();
						if (build.test(start)) {
							item.setAmount(item.getAmount()-1);
							e.getPlayer().sendMessage("ок");
							p.teleport(target.getLocation().add(0.5, 1, 0.5));
						}
						else {
							e.getPlayer().sendMessage("-");
						}
					}
				}
			}
		}
	}
	
}
