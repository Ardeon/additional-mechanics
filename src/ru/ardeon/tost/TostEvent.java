package ru.ardeon.tost;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.tost.skills.Combat;
import ru.ardeon.tost.skills.Heals;
import ru.ardeon.tost.skills.ItemEdit;
import ru.ardeon.tost.skills.Pushes;
import ru.ardeon.tost.skills.Teleports;
import ru.ardeon.tost.skills.Totems;;

public class TostEvent
{
	
	static void ItemChoose(PlayerInteractEvent e)
	{
		//Player player = e.getPlayer();
		ItemStack item = e.getItem();
		//World world = player.getWorld();
		//tost.log.info(item.getItemMeta().getLore().toArray()[0].toString());
		switch (item.getItemMeta().getLore().toArray()[0].toString()){//проверка первой строчки		
		case ("§aОковы§a"):		
		{			
			Combat.SlowStick(e);
			break;
		}
		
		case ("§aТолчок§a"):		
		{			
			Pushes.ForceJump(e);
			break;
		}
		case ("§aПровокация§a"):		
		{			
			Combat.Agro(e);
			break;
		}
		
		case ("§aВзрыв§a"):
		{
			Combat.Explosion(e);
			break;
		}
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
		case ("§aИссушение§a"):
		{
			Combat.quartz(e);
			break;
		}
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
		default:
			break;
		}
	}

}