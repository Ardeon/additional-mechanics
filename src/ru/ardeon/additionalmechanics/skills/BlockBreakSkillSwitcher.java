package ru.ardeon.additionalmechanics.skills;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.blockbreak.MinerBreakSkills;
import ru.ardeon.additionalmechanics.skills.template.BlockBreakSkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;

public class BlockBreakSkillSwitcher {
	static private BlockBreakSkillSwitcher blockBreakSkillSwitcher;
	static public BlockBreakSkillSwitcher getInstance() {
		if (blockBreakSkillSwitcher==null)
			blockBreakSkillSwitcher = new BlockBreakSkillSwitcher();
		return blockBreakSkillSwitcher;
	}
	private HashMap<String, BlockBreakSkill> skills = new HashMap<String, BlockBreakSkill>();
	private BlockBreakSkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, BlockBreakSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		addSkill("bigHalls", MinerBreakSkills.bigHalls);
	}
	
	public void SkillChoose(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		String skillName = ItemUtil.getTag(item, "skill");
		BlockBreakSkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}
}
