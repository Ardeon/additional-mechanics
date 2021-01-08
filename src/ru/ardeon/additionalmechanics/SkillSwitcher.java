package ru.ardeon.additionalmechanics;

import java.util.HashMap;
import java.util.List;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.Combat;
import ru.ardeon.additionalmechanics.skills.Heals;
import ru.ardeon.additionalmechanics.skills.ItemEdit;
import ru.ardeon.additionalmechanics.skills.ItemSkill;
import ru.ardeon.additionalmechanics.skills.ItemToPermission;
import ru.ardeon.additionalmechanics.skills.Pushes;
import ru.ardeon.additionalmechanics.skills.Teleports;
import ru.ardeon.additionalmechanics.skills.Totems;
import ru.ardeon.additionalmechanics.skills.combat.Agro;
import ru.ardeon.additionalmechanics.skills.combat.DamagingSnowball;
import ru.ardeon.additionalmechanics.skills.combat.Explosion;
import ru.ardeon.additionalmechanics.skills.combat.FireballWithEffect;;

public class SkillSwitcher
{
	private HashMap<String,ItemSkill> skills = new HashMap<String,ItemSkill>();
	
	public void addSkill(String name, ItemSkill skill) {
		skills.put(name, skill);
	}
	
	public void eraseSkills() {
		skills.clear();
	}
	
	public void addDefaultSkills() {
		//Combat
		addSkill("§aПровокация§a", Agro.agro);
		addSkill("§aОковы§a", DamagingSnowball.damagersnowball);
		addSkill("§aВзрыв§a", Explosion.explosion);
		addSkill("§aИссушение§a", FireballWithEffect.fireballWithEffect);
	}
	
	public void ItemChoose(PlayerInteractEvent e)
	{
		//Player player = e.getPlayer();
		ItemStack item = e.getItem();
		List<String> lore = item.getItemMeta().getLore();
		String skillName = lore.get(0);
		//World world = player.getWorld();
		//tost.log.info(item.getItemMeta().getLore().toArray()[0].toString());
		ItemSkill skill = skills.getOrDefault(skillName, null);
		if (skill!=null) {
			skill.execute(e);
		}
		switch (skillName){//проверка первой строчки		
		/*case ("§aОковы§a"):		
		{			
			Combat.SlowStick(e);
			break;
		}*/
		
		case ("§aТолчок§a"):		
		{			
			Pushes.ForceJump(e);
			break;
		}
		/*case ("§aПровокация§a"):		
		{			
			Combat.Agro(e);
			break;
		}*/
		
		/*case ("§aВзрыв§a"):
		{
			Combat.Explosion(e);
			break;
		}*/
		case ("§aЛечение§a"):		
		{			
			Heals.Holy(e);
			break;
		}
		
		case ("§aПервая помощь§a"):		
		{			
			if (Heals.FirstAid(e))
				item.setAmount(item.getAmount()-1);
			break;
		}
		
		case ("§aСлепая ярость§a"):		
		{			
			Combat.Rage(e);
			break;
		}
		
		case ("§aПритягивание§a"):		
		{			
			Pushes.Hook(e);
			break;
		}
		
		case ("§aПриманка§a"):		
		{			
			Combat.Bait(e);
			break;
		}
		
		case ("§aИсцеление§a"):		
		{			
			Heals.Honey(e);
			break;
		}
		case ("§aВзрывные стрелы§a"):		
		{			
			if (ItemEdit.BowBuff(e))
				item.setAmount(item.getAmount()-1);
			break;
		}
		case ("§aВзрывные стрелы"):		
		{			
			if (ItemEdit.BowBuff(e))
				item.setAmount(item.getAmount()-1);
			break;
		}
		case ("§aОслабить врагов§a"):		
		{			
			if (Combat.SoulAgro(e))
				item.setAmount(item.getAmount()-1);
			break;
		}
		/*case ("§aИссушение§a"):
		{
			Combat.quartz(e);
			break;
		}*/
		case ("§aТотем лечения§a"):
		{
			Totems.HealTotem(e);
			break;
		}
		case ("§aТотем ветра§a"):
		{
			Totems.WindTotem(e);
			break;
		}
		case ("§aТотем защиты§a"):
		{
			Totems.ProtectorTotem(e);
			break;
		}
		case ("§ashort blink§a"):
		{
			Teleports.Blink(e);
			break;
		}
		case ("§aПет§a"):
		{
			ItemToPermission.PetGet(e, lore);
			break;
		}
		default:
			break;
		}
	}

}