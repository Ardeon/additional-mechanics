package ru.ardeon.additionalmechanics.skills.interact;

import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class Teleports {
	public static InteractSkill blink = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			if (!(player.hasCooldown(Material.STICK)))
			{	
				RayTraceResult trace = player.rayTraceBlocks(6);
				Location loc = player.getLocation();
				Location newloc;
				if (trace==null) 
				{
					
					Vector vec=loc.toVector();
					Vector Direct=player.getEyeLocation().getDirection().multiply(6);
					newloc = vec.add(Direct).toLocation(player.getWorld(),loc.getYaw(),loc.getPitch());
					if (newloc.getBlock().getType().isSolid())
					{
						double floor=Math.floor((newloc.getY()+2));
						newloc.setY(floor);
						if (newloc.getBlock().getType().isSolid()) 
						{
							player.sendRawMessage("нет места");
						}
						else 
						{
							player.teleport(newloc);
							TpEffect(player);
							player.setCooldown(Material.STICK, 40);
						}
					}
					else 
					{
						player.teleport(newloc);
						TpEffect(player);
						player.setCooldown(Material.STICK, 40);
					}
				}
				else 
				{
					BlockFace face=trace.getHitBlockFace();
					Vector correct;
					switch (face) 
					{
			           case  DOWN:
			        	   correct = trace.getHitBlockFace().getDirection();
			               break;
			           case UP:
			        	   correct = new Vector();
			               break;
			           default:
			        	   correct = trace.getHitBlockFace().getDirection().multiply(0.3);
			               break;
			        }
					newloc =trace.getHitPosition().add(correct).toLocation(player.getWorld(), loc.getYaw(),loc.getPitch());
					player.teleport(newloc);
					TpEffect(player);
					player.setCooldown(Material.STICK, 40);
				}
			}
		}
		public void TpEffect(Player p)
		{
			p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 50, 100);
			p.spawnParticle(Particle.CLOUD, p.getEyeLocation(), 20, 0.1,0.1,0.1);
		}
	};
}
