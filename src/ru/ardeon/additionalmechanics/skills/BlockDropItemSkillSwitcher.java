package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;

import ru.ardeon.additionalmechanics.skills.blockdropitem.MinerDropSkills;
import ru.ardeon.additionalmechanics.skills.template.BlockDropItemSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class BlockDropItemSkillSwitcher {
	static private BlockDropItemSkillSwitcher blockDropItemSkillSwitcher;
	static public BlockDropItemSkillSwitcher getInstance() {
		if (blockDropItemSkillSwitcher==null)
			blockDropItemSkillSwitcher = new BlockDropItemSkillSwitcher();
		return blockDropItemSkillSwitcher;
	}
	private HashMap<String, BlockDropItemSkill> skills = new HashMap<String, BlockDropItemSkill>();
	private BlockDropItemSkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, BlockDropItemSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		addSkill("miner", MinerDropSkills.extraDrop);
	}
	
	public void SkillChoose(BlockDropItemEvent e)
	{
		Player player = e.getPlayer();
		String guild = PlayerVarManager.getInstance().getData(player).getCMIstring("guild");
		BlockDropItemSkill skill = skills.getOrDefault(guild, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}
}
