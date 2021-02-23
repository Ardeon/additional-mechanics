package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

import ru.ardeon.additionalmechanics.skills.template.ProjectileHitSkill;

public class ProjectileHitSkillSwitcher {
	static private ProjectileHitSkillSwitcher projectileHitSkillSwitcher;
	static public ProjectileHitSkillSwitcher getInstance() {
		if (projectileHitSkillSwitcher==null)
			projectileHitSkillSwitcher = new ProjectileHitSkillSwitcher();
		return projectileHitSkillSwitcher;
	}
	private HashMap<String, ProjectileHitSkill> skills = new HashMap<String, ProjectileHitSkill>();
	private ProjectileHitSkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, ProjectileHitSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		
	}
	
	public void SkillChoose(ProjectileHitEvent e)
	{
		e.getHitEntity();
		e.getEntity();
		Projectile projectile = e.getEntity();
		for (String tag : projectile.getScoreboardTags()) {
			ProjectileHitSkill skill = skills.getOrDefault(tag, null);
			if (skill!=null) {
				skill.execute(e);
			}
		}
	}
}
