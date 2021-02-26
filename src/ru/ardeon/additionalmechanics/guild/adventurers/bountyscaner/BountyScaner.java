package ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.mechanics.scan.Scan;

public class BountyScaner extends Scan {
	public BountyScaner(Player p, Block start, Set<Material> requiredBlocks, int power) {
		super(p, start, requiredBlocks, power, new BountyConverter(), "§2Поиск сокровищ закончен", "§3Поиск сокровищ...", "Поиск...");
	}

	@Override
	public boolean testBlock(Block c) {
		if (requiredBlocks.contains(c.getType()))
			return true;
		return false;
	}
}
