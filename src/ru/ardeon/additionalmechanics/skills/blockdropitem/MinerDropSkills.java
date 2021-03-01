package ru.ardeon.additionalmechanics.skills.blockdropitem;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreConverter;
import ru.ardeon.additionalmechanics.skills.template.BlockDropItemSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class MinerDropSkills {
	public static BlockDropItemSkill extraDrop = new BlockDropItemSkill() {
		private OreConverter converter = new OreConverter();
		
		@Override
		public void execute(BlockDropItemEvent e) {
			Material m = e.getBlockState().getType();
			Player p =e.getPlayer();
			if (converter.testForMaterial(m)&&p.getGameMode().equals(GameMode.SURVIVAL)) {
				ItemStack item = p.getInventory().getItemInMainHand();
				Location loc = e.getBlock().getLocation().clone().add(0.5, 0.5, 0.5);
				int extra = getExtradropPower(p);
				if (extra>0&&!item.containsEnchantment(Enchantment.SILK_TOUCH)) {
					ItemStack drop = extradrop(m, extra);
					if (drop!=null&&drop.getAmount()>0) {
						p.getWorld().dropItem(loc, drop);
					}
					if (meltingTest(m)) {
						p.getWorld().dropItem(loc, melting(m));
						e.setCancelled(true);
					}
				}
			}
		}
		
		private int getExtradropPower(Player player) {
			Integer power = PlayerVarManager.getInstance().getData(player).getCMIint("extradrop");
			if (power==null)
				power = 0;
			return power;
		}
		
		private ItemStack extradrop(Material m, int extra) {
			double r = Math.random()*10*extra;
			ItemStack add=null;
			switch (m) {
			case COAL_ORE: {
				add = new ItemStack(Material.COAL, (int)(r/10));
				break;
			}
			case IRON_ORE: {
				add = new ItemStack(Material.IRON_NUGGET, (int)(r/3));
				break;
			}
			case GOLD_ORE: {
				add = new ItemStack(Material.GOLD_NUGGET, (int)(r/3));
				break;
			}
			case LAPIS_ORE: {
				add = new ItemStack(Material.LAPIS_LAZULI, (int)(r));
				break;
			}
			case EMERALD_ORE: {
				add = new ItemStack(Material.EMERALD, (int)(r/10));
				break;
			}
			case NETHER_QUARTZ_ORE: {
				add = new ItemStack(Material.QUARTZ, (int)(r/6));
				break;
			}
			case NETHER_GOLD_ORE: {
				add = new ItemStack(Material.GOLD_NUGGET, (int)(r/3));
				break;
			}
			case DIAMOND_ORE: {
				add = new ItemStack(Material.DIAMOND, (int)(r/10));
				break;
			}
			default:
				
				break;
			}
			return add;
		}
		
		private ItemStack melting(Material m) {
			switch (m) {
			case IRON_ORE:
				return new ItemStack(Material.IRON_INGOT, 1);
			case GOLD_ORE: 
				return new ItemStack(Material.GOLD_INGOT, 1);
			default:
				break;
			}
			return null;
		}
		
		private boolean meltingTest(Material m) {
			switch (m) {
			case IRON_ORE:
				return true;
			case GOLD_ORE: 
				return true;
			default:
				break;
			}
			return false;
		}
		
	};
}
