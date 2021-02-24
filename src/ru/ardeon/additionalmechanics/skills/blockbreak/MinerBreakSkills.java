package ru.ardeon.additionalmechanics.skills.blockbreak;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import ru.ardeon.additionalmechanics.skills.template.BlockBreakSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class MinerBreakSkills {
	public static BlockBreakSkill bigHalls = new BlockBreakSkill() {
		Set<Material> stones = new HashSet<Material>(Arrays.asList(Material.STONE, 
				Material.ANDESITE, Material.GRANITE, Material.DIORITE, Material.NETHERRACK));
		
		@Override
		public void execute(BlockBreakEvent e) {
			Player p = e.getPlayer();
			ItemStack item = p.getInventory().getItemInMainHand();
			Block b = e.getBlock();
			Material m = b.getType();
			if (stones.contains(m))
			{
				float yaw = p.getLocation().getYaw();//0 = positive z; 90 = negative x; 
				int z0=0,x0=0,z1=0,x1=0,y=0,x=0;
				Integer power = PlayerVarManager.getInstance().getData(p).getCMIint("minesize");
				if (power==0||power==null)
					return;
				if (power>=3) {
					y=1;
					power = 3;
				}
				if (power>1)
					x=1;
				if (yaw>315||yaw<=45) {
					z0 = -1;
					if (power>1) {
						x0=-1;x1=1;
					}
					if (power==3)
						z1=1;
				}
				else if (yaw<=135) {
					x1 = 1;
					if (power>1) {
						z0=-1;z1=1;
					}
					if (power==3)
						x0=-1;
				}
				else if (yaw<=225) {
					z1 = 1;
					if (power>1) {
						x0=-1;x1=1;
					}
					if (power==3)
						z0=-1;
				}
				else {
					x0 = -1;
					if (power>1) {
						z0=-1;z1=1;
					}
					if (power==3)
						x1=1;
				}
				
				BlockFace face = e.getPlayer().rayTraceBlocks(5).getHitBlockFace();
				
				switch (face) {
				case DOWN :
				{
					for (int i = x0; i<=x1; i++)
					{
						for (int j = z0; j<=z1; j++)
						{
							if ((j==0) && (i==0))
								continue;
							Block rb = b.getRelative(i, 0, j);
							Material rm = rb.getType();
							if (stones.contains(rm))
								rb.breakNaturally(item);
						}
					}
					break;
				}
				case UP :
				{
					for (int i = x0; i<=x1;i++)
					{
						for (int j = z0; j<=z1;j++)
						{
							if ((j==0) && (i==0))
								continue;
							Block rb = b.getRelative(i, 0, j);
							Material rm = rb.getType();
							if (stones.contains(rm))
								rb.breakNaturally(item);
						}
					}
					break;
				}
				case EAST :
				{
					for (int i = -1; i<=y;i++)
					{
						for (int j = -x; j<=x;j++)
						{
							if ((j==0) && (i==0))
								continue;
							Block rb = b.getRelative(0, i, j);
							Material rm = rb.getType();
							if (stones.contains(rm))
								rb.breakNaturally(item);
						}
					}
					break;
				}
				case WEST :
				{
					for (int i = -1; i<=y;i++)
					{
						for (int j = -x; j<=x;j++)
						{
							if ((j==0) && (i==0))
								continue;
							Block rb = b.getRelative(0, i, j);
							Material rm = rb.getType();
							if (stones.contains(rm))
								rb.breakNaturally(item);
						}
					}
					break;
				}
				case NORTH :
				{
					for (int i = -1; i<=y;i++)
					{
						for (int j = -x; j<=x;j++)
						{
							if ((j==0) && (i==0))
								continue;
							Block rb = b.getRelative(j, i, 0);
							Material rm = rb.getType();
							if (stones.contains(rm))
								rb.breakNaturally(item);
						}
					}
					break;
				}
				case SOUTH :
				{
					for (int i = -1; i<=y;i++)
					{
						for (int j = -x; j<=x;j++)
						{
							if ((j==0) && (i==0))
								continue;
							Block rb = b.getRelative(j, i, 0);
							Material rm = rb.getType();
							if (stones.contains(rm))
								rb.breakNaturally(item);
						}
					}
					break;
				}
				default:
					break;
				}
			}	
		}
	};
}
