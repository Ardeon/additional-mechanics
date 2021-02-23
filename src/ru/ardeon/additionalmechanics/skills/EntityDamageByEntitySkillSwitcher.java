package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.entitydamagebyentity.DamageSkills;
import ru.ardeon.additionalmechanics.skills.template.EntityDamageByEntitySkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;

public class EntityDamageByEntitySkillSwitcher {
	static private EntityDamageByEntitySkillSwitcher entityDamageByEntitySkillSwitcher;
	static public EntityDamageByEntitySkillSwitcher getInstance() {
		if (entityDamageByEntitySkillSwitcher==null)
			entityDamageByEntitySkillSwitcher = new EntityDamageByEntitySkillSwitcher();
		return entityDamageByEntitySkillSwitcher;
	}
	private HashMap<String,EntityDamageByEntitySkill> skills = new HashMap<String,EntityDamageByEntitySkill>();
	private EntityDamageByEntitySkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, EntityDamageByEntitySkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		addSkill("vampirism", DamageSkills.vampirism);
	}
	
	public void SkillChoose(EntityDamageByEntityEvent e)
	{
		ItemStack item = null;
		Entity damager = e.getDamager();
		if (damager instanceof Player) {
			Player player = (Player) damager;
			item = player.getInventory().getItemInMainHand();
		}
		String skillName = ItemUtil.getTag(item, "skill");
		EntityDamageByEntitySkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}
}
