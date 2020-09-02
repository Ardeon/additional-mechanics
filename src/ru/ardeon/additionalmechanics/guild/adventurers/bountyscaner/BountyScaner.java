package ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.mechanics.scan.Scan;
import ru.ardeon.additionalmechanics.util.Filter;

public class BountyScaner extends Scan {
	public BountyScaner(Player p, Block start, Filter filter, int power) {
		super(p, start, filter, power, new BountyConverter(), "§2Поиск сокровищ закончен", "§3Поиск сокровищ...", "Поиск...");
	}

	@Override
	public boolean testBlock(Block c) {
		if (filter.test(c.getType()))
			return true;
		return false;
	}
}
