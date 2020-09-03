package ru.ardeon.additionalmechanics.guild.adventurers;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner.BountyConverter;
import ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner.BountyFilter;
import ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner.BountyScaner;

public class AdventurersListener implements Listener {
	AdventurersHall hall;
	BountyConverter converter = new BountyConverter();
	
	public AdventurersListener(AdventurersHall hall) {
		this.hall = hall;
	}
	

	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				ItemStack item = e.getItem();
				Player p = e.getPlayer();
				if (item != null) {
					ItemMeta meta = item.getItemMeta();
					if (meta.hasLore()) {
						List<String> lore = meta.getLore();
						if (!p.hasCooldown(Material.COMPASS)&&(lore.get(0).equals("§6Радар")
								||lore.get(0).equals("§6Малый радар")||lore.get(0).equals("§6Большой радар"))) {
							if (p.isSneaking()) {
								if (lore.size()>2) {
									lore.set(2, converter.getNext(lore.get(2)));
									meta.setLore(lore);
									item.setItemMeta(meta);
								}
							}
							else {
								BountyFilter filter = new BountyFilter();
								if (lore.size()>2) {
									filter = new BountyFilter(converter.FromString(lore.get(2)));
								}
								if (filter.testPermission(p)) {
									switch (lore.get(0)) {
									case "§6Малый радар":{
										if (p.hasPermission("additionalmechanics.radar.power.1")) {
											new BountyScaner(p, e.getClickedBlock(), filter, 1);
											p.setCooldown(Material.COMPASS, 20*30);
										}
										else
											p.sendRawMessage("§cВы не умеете это использовать");
										break;
									}
									case "§6Радар":{
										if (p.hasPermission("additionalmechanics.radar.power.2")) {
											new BountyScaner(p, e.getClickedBlock(), filter, 2);
											p.setCooldown(Material.COMPASS, 20*45);
										}
										else
											p.sendRawMessage("§cВы не умеете это использовать");
										break;
									}
									case "§6Большой радар":{
										if (p.hasPermission("additionalmechanics.radar.power.3")) {
											new BountyScaner(p, e.getClickedBlock(), filter, 3);
											p.setCooldown(Material.COMPASS, 20*70);
										}
										else
											p.sendRawMessage("§cВы не умеете это использовать");
										break;
									}
									}
								}
								else {
									p.sendRawMessage("§cВы не умеете искать данный ресурс");
									p.setCooldown(Material.COMPASS, 20*1);
								}
							}
							
						}
					}
				}
			}
		}
	}
}
