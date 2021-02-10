package ru.ardeon.additionalmechanics.skills;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.myEntity.HealTotem;
import ru.ardeon.additionalmechanics.myEntity.ProtectorTotem;
import ru.ardeon.additionalmechanics.myEntity.WindTotem;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class Totems {
	public static ItemSkill healTotem = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.LIME_CONCRETE))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(6, 4);
				player.setCooldown(Material.LIME_CONCRETE, 300 - cd * 10);
				world.playSound(player.getLocation(), Sound.BLOCK_WOOD_PLACE, 2, 1);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
				new HealTotem(player);
				e.setCancelled(true);
			}
		}
	};
	public static ItemSkill windTotem = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.GRAY_CONCRETE))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(6, 3);
				player.setCooldown(Material.GRAY_CONCRETE, 1000 - cd * 50);
				world.playSound(player.getLocation(), Sound.BLOCK_WOOD_PLACE, 2, 1);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
				new WindTotem(player);
				e.setCancelled(true);
			}
		}
	};
	public static ItemSkill protectorTotem = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.BLACK_CONCRETE))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(6, 2);
				player.setCooldown(Material.BLACK_CONCRETE, 1000 - cd * 50);
				world.playSound(player.getLocation(), Sound.BLOCK_WOOD_PLACE, 2, 1);
				world.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
				new ProtectorTotem(player);
				e.setCancelled(true);
			}
		}
	};
}
