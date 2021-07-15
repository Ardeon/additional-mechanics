package ru.ardeon.additionalmechanics.skills.interact;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.mechanics.Altar;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import org.bukkit.event.player.PlayerInteractEvent;

public class AltarInteractSkills {
	public static InteractSkill addAltarCharge = new InteractSkill() {
		
		
		@Override
		public void execute(PlayerInteractEvent e) {
			Altar altar = AdditionalMechanics.getAltar();
			altar.getLocation().distance(null);
		}
	};
}
