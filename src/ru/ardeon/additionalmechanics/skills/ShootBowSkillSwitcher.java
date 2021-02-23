package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.template.ShootBowSkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;

public class ShootBowSkillSwitcher {
	static private ShootBowSkillSwitcher shootBowSkillSwitcher;
	static public ShootBowSkillSwitcher getInstance() {
		if (shootBowSkillSwitcher==null)
			shootBowSkillSwitcher = new ShootBowSkillSwitcher();
		return shootBowSkillSwitcher;
	}
	private HashMap<String, ShootBowSkill> skills = new HashMap<String, ShootBowSkill>();
	private ShootBowSkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, ShootBowSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		
	}
	
	public void SkillChoose(EntityShootBowEvent e)
	{
		ItemStack item = e.getBow();
		String skillName = ItemUtil.getTag(item, "skill");
		//AdditionalMechanics.getPlugin().getLogger().info(skillName);
		ShootBowSkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}
}
