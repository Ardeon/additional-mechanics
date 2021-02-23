package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.interactentity.InteractEntitySkills;
import ru.ardeon.additionalmechanics.skills.template.InteractEntitySkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;

public class InteractEntitySkillSwitcher {
	static private InteractEntitySkillSwitcher interactEntitySkillSwitcher;
	static public InteractEntitySkillSwitcher getInstance() {
		if (interactEntitySkillSwitcher==null)
			interactEntitySkillSwitcher = new InteractEntitySkillSwitcher();
		return interactEntitySkillSwitcher;
	}
	private HashMap<String, InteractEntitySkill> skills = new HashMap<String, InteractEntitySkill>();
	private InteractEntitySkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, InteractEntitySkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		addSkill("soulHeal", InteractEntitySkills.soul);
	}
	
	public void SkillChoose(PlayerInteractEntityEvent e)
	{
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		String skillName = ItemUtil.getTag(item, "skill");
		//AdditionalMechanics.getPlugin().getLogger().info(skillName);
		InteractEntitySkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}
}
