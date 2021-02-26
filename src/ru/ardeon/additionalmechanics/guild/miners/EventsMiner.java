package ru.ardeon.additionalmechanics.guild.miners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreConverter;


public class EventsMiner implements Listener {
	OreConverter converter = new OreConverter();

	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerBreak(BlockDropItemEvent e) {
		Material m = e.getBlockState().getType();
		Player p =e.getPlayer();
		if (converter.testForMaterial(m)&&p.getGameMode().equals(GameMode.SURVIVAL)) {
			ItemStack item = p.getInventory().getItemInMainHand();
			Location loc = e.getBlock().getLocation().clone().add(0.5, 0.5, 0.5);
			int extra = PermissonConvert.getextradrop(p);
			if (extra>0&&!item.containsEnchantment(Enchantment.SILK_TOUCH)) {
				ItemStack drop = MinerExtraDrop.extradrop(m, extra);
				if (drop!=null&&drop.getAmount()>0) {
					p.getWorld().dropItem(loc, drop);
				}
				if (MinerExtraDrop.test(m)) {
					p.getWorld().dropItem(loc, MinerExtraDrop.drop(m));
					e.setCancelled(true);
				}
						
					
				
			}
		}
		
	}
	
	
	
	
}
