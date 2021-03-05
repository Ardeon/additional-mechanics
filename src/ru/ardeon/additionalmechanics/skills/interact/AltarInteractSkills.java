package ru.ardeon.additionalmechanics.skills.interact;

import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.mechanics.Altar;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;

public class AltarInteractSkills {
	public static InteractSkill addAltarCharge = new InteractSkill() {
		
		
		@Override
		public void execute(PlayerInteractEvent e) {
			Altar altar = AdditionalMechanics.altar;
			altar.getLocation().distance(null);
		}
	};
}
