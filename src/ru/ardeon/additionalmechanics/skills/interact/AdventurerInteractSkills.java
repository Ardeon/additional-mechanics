package ru.ardeon.additionalmechanics.skills.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import ru.ardeon.additionalmechanics.guild.adventurers.horse.HorseController;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class AdventurerInteractSkills {
	public static InteractSkill horse = new InteractSkill(){
		
		
		private HorseController horseController = HorseController.getInstace();
		
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			String horse = PlayerVarManager.getInstance().getData(player).getCMIstring("adventurerHorse");
			EntityType horseType = getHorseType(horse);
			if (!player.hasCooldown(Material.GLOWSTONE_DUST) && horseType!=null) {
				Double hp, speed, jump;
				hp = PlayerVarManager.getInstance().getData(player).getCMIdouble("horseHP");
				if (hp==null) {
					hp = 10.0;
				}
				speed = PlayerVarManager.getInstance().getData(player).getCMIdouble("horseSPEED");
				if (speed==null) {
					speed = 0.2;
				}
				jump = PlayerVarManager.getInstance().getData(player).getCMIdouble("horseJUMP");
				if (jump==null) {
					jump = 0.3;
				}
				if(horseController==null)
					horseController = HorseController.getInstace();
				horseController.CreateHorse(player, horseType, hp, speed, jump);
				player.setCooldown(Material.GLOWSTONE_DUST, 40);
			}

		}
		
		private EntityType getHorseType(String type) {
			EntityType horse = null;
			switch (type) {
			case "skeleton":{
				horse = EntityType.SKELETON_HORSE;
				break;
			}
			case "zombie":{
				horse = EntityType.ZOMBIE_HORSE;
				break;
			}
			case "normal":{
				horse = EntityType.HORSE;
				break;
			}
			default:
				break;
			}
			return horse;
		}
	};
}
