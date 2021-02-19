package ru.ardeon.additionalmechanics;

import java.util.HashMap;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.interact.Combat;
import ru.ardeon.additionalmechanics.skills.interact.Heals;
import ru.ardeon.additionalmechanics.skills.interact.ItemToPermission;
import ru.ardeon.additionalmechanics.skills.interact.Pushes;
import ru.ardeon.additionalmechanics.skills.interact.Teleports;
import ru.ardeon.additionalmechanics.skills.interact.Totems;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;

public class InteractSkillSwitcher
{
	private HashMap<String,InteractSkill> skills = new HashMap<String,InteractSkill>();
	
	public InteractSkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, InteractSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		//Combat
		addSkill("agro", Combat.agro);
		addSkill("damagingSnowball", Combat.damagingSnowball);
		addSkill("explosion", Combat.explosion);
		addSkill("fireballWithEffect", Combat.fireballWithEffect);
		addSkill("rage", Combat.rage);
		addSkill("scarecrow", Combat.scarecrow);
		addSkill("soulAgro", Combat.soulAgro);
		//Heals
		addSkill("holy", Heals.holy);
		addSkill("honey", Heals.honey);
		addSkill("firstAid", Heals.firstAid);
		//Pushes
		addSkill("forceJump", Pushes.forceJump);
		addSkill("hook", Pushes.hook);
		//teleports
		addSkill("blink", Teleports.blink);
		//totems
		addSkill("healTotem", Totems.healTotem);
		addSkill("windTotem", Totems.windTotem);
		addSkill("protectorTotem", Totems.protectorTotem);
		
		//ItemToPermission
		addSkill("pet", ItemToPermission.pet);
	}
	
	public void ItemChoose(PlayerInteractEvent e)
	{
		ItemStack item = e.getItem();
		String skillName = ItemUtil.getTag(item, "skill");
		//AdditionalMechanics.getPlugin().getLogger().info(skillName);
		InteractSkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}

}