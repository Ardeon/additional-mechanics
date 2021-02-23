package ru.ardeon.additionalmechanics.skills.entitydamagebyentity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.ardeon.additionalmechanics.skills.template.EntityDamageByEntitySkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;

public class DamageSkills {
	static public EntityDamageByEntitySkill vampirism = new EntityDamageByEntitySkill() {
		@Override
		public void execute(EntityDamageByEntityEvent e) {
			Entity damager = e.getDamager();
			if (damager instanceof Player) {
				Player p = (Player) damager;
				double maxhealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				double newhealth = p.getHealth()+e.getFinalDamage()/4;
				if (newhealth<maxhealth) {
					p.setHealth(newhealth);
				}
				else {
					p.setHealth(maxhealth);
				}
					
				double x = Math.random() * 100;
				if (x>99) {
					ItemStack soul = new ItemStack(Material.SLIME_BALL,1);
					ItemUtil.setTag(soul, "skill", "soulHeal");
					ItemMeta soulmeta = soul.getItemMeta();
					soulmeta.setDisplayName("§1Частичка души§1");
					List<String> soullore = new ArrayList<String>();
					soullore.add("§aПередать жизненные силы§a");
					soullore.add("§2Усиливает союзника§2");
					soulmeta.setLore(soullore);
					soul.setItemMeta(soulmeta);
					p.getInventory().addItem(soul);
				}
				x = Math.random() * 100;
				if (x>96) {
					ItemStack chaos = new ItemStack(Material.BROWN_DYE,1);
					ItemUtil.setTag(chaos, "skill", "soulAgro");
					ItemMeta chaosmeta = chaos.getItemMeta();
					chaosmeta.setDisplayName("§1Частичка хаоса§1");
					List<String> soullore = new ArrayList<String>();
					soullore.add("§aОслабить врагов§a");
					soullore.add("§2Снижает урон противников§2");
					chaosmeta.setLore(soullore);
					chaos.setItemMeta(chaosmeta);
					p.getInventory().addItem(chaos);
				}
			}
		}
	};
}
