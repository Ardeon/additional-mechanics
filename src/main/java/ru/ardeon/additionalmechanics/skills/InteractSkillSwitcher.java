package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import ru.ardeon.additionalmechanics.skills.interact.*;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractSkillSwitcher
{
	static private InteractSkillSwitcher interactSkillSwitcher;
	static public InteractSkillSwitcher getInstance() {
		if (interactSkillSwitcher==null)
			interactSkillSwitcher = new InteractSkillSwitcher();
		return interactSkillSwitcher;
	}
	
	private HashMap<String, InteractSkill> skills = new HashMap<String,InteractSkill>();
	private InteractSkillSwitcher(){
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
		
		//Scans
		addSkill("minerScan", ScanSkills.minerScan);
		addSkill("adventurerScan", ScanSkills.adventurerScan);
		
		//adventurer
		addSkill("horse", AdventurerInteractSkills.horse);

		//new
		addSkill("arenaSnowball", NewArena.snowball);
	}
	
	public void SkillChoose(PlayerInteractEvent e)
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