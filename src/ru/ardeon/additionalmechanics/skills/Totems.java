package ru.ardeon.additionalmechanics.skills;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.myEntity.HealTotem;
import ru.ardeon.additionalmechanics.myEntity.ProtectorTotem;
import ru.ardeon.additionalmechanics.myEntity.WindTotem;

public class Totems {
	public static void HealTotem(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.LIME_CONCRETE))) 
		{
			player.setCooldown(Material.LIME_CONCRETE, 300);
			world.playSound(player.getLocation(), Sound.BLOCK_WOOD_PLACE, 2, 1);
			world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 2, 1.2f);
			world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
			new HealTotem(player);
			e.setCancelled(true);
		}
	}
	
	public static void WindTotem(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.GRAY_CONCRETE))) 
		{
			player.setCooldown(Material.GRAY_CONCRETE, 1000);
			world.playSound(player.getLocation(), Sound.BLOCK_WOOD_PLACE, 2, 1);
			world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 2, 1.2f);
			world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
			new WindTotem(player);
			e.setCancelled(true);
		}
	}
	
	public static void ProtectorTotem(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (!(player.hasCooldown(Material.BLACK_CONCRETE))) 
		{
			player.setCooldown(Material.BLACK_CONCRETE, 1000);
			world.playSound(player.getLocation(), Sound.BLOCK_WOOD_PLACE, 2, 1);
			world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 2, 1.2f);
			world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
			new ProtectorTotem(player);
			e.setCancelled(true);
		}
	}
}
