package ru.ardeon.additionalmechanics.guild.miners.orescaner;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.mechanics.scan.Scan;
import ru.ardeon.additionalmechanics.util.Filter;

public class OreScaner extends Scan {
	
	public OreScaner(Player p, Block start, Filter filter, int power) {
		super(p, start, filter, power, new OreConverter(), "§2Поиск руды закончен", "§3Поиск руды...", "Сканирование...");
	}

	@Override
	public boolean testBlock(Block c) {
			switch (c.getType()) {
			case COAL_ORE: {
				p.playNote(c.getLocation(), Instrument.BANJO, Note.flat(1, Tone.F));
				break;
			}
			case IRON_ORE: {
				p.playNote(c.getLocation(), Instrument.IRON_XYLOPHONE, Note.flat(1, Tone.F));
				break;
			}
			case GOLD_ORE: {
				p.playNote(c.getLocation(), Instrument.CHIME, Note.flat(1, Tone.F));
				break;
			}
			case LAPIS_ORE: {
				p.playNote(c.getLocation(), Instrument.FLUTE, Note.flat(1, Tone.F));
				break;
			}
			case EMERALD_ORE: {
				p.playNote(c.getLocation(), Instrument.PIANO, Note.flat(1, Tone.F));
				break;
			}
			case DIAMOND_ORE: {
				p.playNote(c.getLocation(), Instrument.PLING, Note.flat(1, Tone.F));
				break;
			}
			default:
				return false;
			}
		
		return false;
	}
	
}
