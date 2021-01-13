package ru.ardeon.additionalmechanics;

import java.util.HashMap;
import java.util.List;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.Combat;
import ru.ardeon.additionalmechanics.skills.Heals;
import ru.ardeon.additionalmechanics.skills.ItemSkill;
import ru.ardeon.additionalmechanics.skills.ItemToPermission;
import ru.ardeon.additionalmechanics.skills.Pushes;
import ru.ardeon.additionalmechanics.skills.Teleports;
import ru.ardeon.additionalmechanics.skills.Totems;

public class SkillSwitcher
{
	private HashMap<String,ItemSkill> skills = new HashMap<String,ItemSkill>();
	
	public SkillSwitcher(){
		addDefaultSkills();
	}
	
	public void addSkill(String name, ItemSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		//Combat
		addSkill("§aПровокация§a", Combat.agro);
		addSkill("§aОковы§a", Combat.damagingSnowball);
		addSkill("§aВзрыв§a", Combat.explosion);
		addSkill("§aИссушение§a", Combat.fireballWithEffect);
		addSkill("§aСлепая ярость§a", Combat.rage);
		addSkill("§aПриманка§a", Combat.scarecrow);
		addSkill("§aОслабить врагов§a", Combat.soulAgro);
		//Heals
		addSkill("§aЛечение§a", Heals.holy);
		addSkill("§aИсцеление§a", Heals.honey);
		addSkill("§aПервая помощь§a", Heals.firstAid);
		//ItemToPermission
		addSkill("§aПет§a", ItemToPermission.pet);
		//Pushes
		addSkill("§aТолчок§a", Pushes.forceJump);
		addSkill("§aПритягивание§a", Pushes.hook);
		//teleports
		addSkill("§ashort blink§a", Teleports.blink);
		//totems
		addSkill("§aТотем лечения§a", Totems.healTotem);
		addSkill("§aТотем ветра§a", Totems.windTotem);
		addSkill("§aТотем защиты§a", Totems.protectorTotem);
	}
	
	public void ItemChoose(PlayerInteractEvent e)
	{
		ItemStack item = e.getItem();
		List<String> lore = item.getItemMeta().getLore();
		String skillName = lore.get(0);
		ItemSkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
	}

}