package ru.ardeon.additionalmechanics.skills.interact;

import java.util.Set;

import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreConverter;
import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreScaner;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.util.ItemUtil;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner.BountyConverter;
import ru.ardeon.additionalmechanics.guild.adventurers.bountyscaner.BountyScaner;

public class ScanSkills {
	public static InteractSkill minerScan = new InteractSkill() {
		
		OreConverter converter = new OreConverter();
		
		@Override
		public void execute(PlayerInteractEvent e) {
			
			ItemStack item = e.getItem();
			Player player = e.getPlayer();
				if (!player.hasCooldown(Material.COMPASS)) {
					Set<Material> requiredBlocks =  getRequiredBlocks(item, player);
					String power = ItemUtil.getTag(item, "scanPower");
					if (requiredBlocks!=null) {
						
						switch (power) {
						case "1":{
							if (PlayerVarManager.getInstance().getData(player).getCMIstring("minerscan1").equalsIgnoreCase("yes")) {
								new OreScaner(player, player.getEyeLocation().getBlock(), requiredBlocks, 1);
								player.setCooldown(Material.COMPASS, 20*30);
							}
							else
								player.sendRawMessage("§cВы не умеете это использовать");
							break;
						}
						case "2":{
							if (PlayerVarManager.getInstance().getData(player).getCMIstring("minerscan2").equalsIgnoreCase("yes")) {
								new OreScaner(player, player.getEyeLocation().getBlock(), requiredBlocks, 2);
								player.setCooldown(Material.COMPASS, 20*45);
							}
							else
								player.sendRawMessage("§cВы не умеете это использовать");
							break;
						}
						case "3":{
							if (PlayerVarManager.getInstance().getData(player).getCMIstring("minerscan3").equalsIgnoreCase("yes")) {
								new OreScaner(player, player.getEyeLocation().getBlock(), requiredBlocks, 3);
								player.setCooldown(Material.COMPASS, 20*70);
							}
							else
								player.sendRawMessage("§cВы не умеете это использовать");
							break;
						}
						}
					}
					else {
						player.sendRawMessage("§cВы не умеете искать данный ресурс");
						player.setCooldown(Material.COMPASS, 20*1);
					}
				}
			
			
		}
		
		Set<Material> getRequiredBlocks(ItemStack item, Player player){
			String required = ItemUtil.getTag(item, "required");
			String permission = PlayerVarManager.getInstance().getData(player).getCMIstring("minerfilter"+required);
			if (!permission.equalsIgnoreCase("yes")) {
				return null;
			}
			Set<Material> requiredBlocks = converter.FromString(required);
			return requiredBlocks;
		}
	};
	
	
	
	
	public static InteractSkill adventurerScan = new InteractSkill() {
		BountyConverter converter = new BountyConverter();
		
		@Override
		public void execute(PlayerInteractEvent e) {
			
			ItemStack item = e.getItem();
			Player player = e.getPlayer();
			if (!player.hasCooldown(Material.COMPASS)) {
				Set<Material> requiredBlocks =  getRequiredBlocks(item, player);
				String power = ItemUtil.getTag(item, "scanPower");
				if (requiredBlocks!=null) {
					switch (power) {
					case "1":{
						if (PlayerVarManager.getInstance().getData(player).getCMIstring("adventurerscan1").equalsIgnoreCase("yes")) {
							new BountyScaner(player, e.getClickedBlock(), requiredBlocks, 1);
							player.setCooldown(Material.COMPASS, 20*30);
						}
						else
							player.sendRawMessage("§cВы не умеете это использовать");
						break;
					}
					case "2":{
						if (PlayerVarManager.getInstance().getData(player).getCMIstring("adventurerscan2").equalsIgnoreCase("yes")) {
							new BountyScaner(player, e.getClickedBlock(), requiredBlocks, 2);
							player.setCooldown(Material.COMPASS, 20*45);
						}
						else
							player.sendRawMessage("§cВы не умеете это использовать");
						break;
					}
					case "3":{
						if (PlayerVarManager.getInstance().getData(player).getCMIstring("adventurerscan3").equalsIgnoreCase("yes")) {
							new BountyScaner(player, e.getClickedBlock(), requiredBlocks, 3);
							player.setCooldown(Material.COMPASS, 20*70);
						}
						else
							player.sendRawMessage("§cВы не умеете это использовать");
						break;
					}
					}
				}
				else {
					player.sendRawMessage("§cВы не умеете искать данный ресурс");
					player.setCooldown(Material.COMPASS, 20*1);
				}
				
				
			}
			
		}
		
		
		Set<Material> getRequiredBlocks(ItemStack item, Player player){
			String required = ItemUtil.getTag(item, "required");
			String permission = PlayerVarManager.getInstance().getData(player).getCMIstring("adventurerfilter"+required);
			if (!permission.equalsIgnoreCase("yes")) {
				return null;
			}
			Set<Material> requiredBlocks = converter.FromString(required);
			return requiredBlocks;
		}
		
		
	};
	
	
	
	
	
	
}
