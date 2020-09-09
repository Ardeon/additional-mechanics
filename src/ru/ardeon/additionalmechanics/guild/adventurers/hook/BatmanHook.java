package ru.ardeon.additionalmechanics.guild.adventurers.hook;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class BatmanHook {

	public static Vector rayTrace(Player p, int length) {
		RayTraceResult result = p.rayTraceBlocks(length, FluidCollisionMode.NEVER);
		if (result!=null)
			return result.getHitPosition();
		return null;
	}
	
	public static boolean useHook(Player p) {
		int length = testPermission(p);
		if (length!=0) {
			Vector vector = rayTrace(p, length);
			if (vector!=null) {
				Vector playerVec = p.getEyeLocation().toVector();
				Vector direction = vector.clone().subtract(playerVec).normalize();
				World w = p.getWorld();
				p.setVelocity(direction.multiply(1.7));
				rayDraw(w, vector, playerVec);
				return true;
			}
			else
				return false;
		}
		return false;
	}
	
	static void rayDraw(World w, Vector v1, Vector v2) {
		Vector dir = v2.clone().subtract(v1).normalize().multiply(v1.distance(v2)/14);
		for (int i = 0; i<14; i++) {
			Vector curr = v1.clone().add(dir.clone().multiply(i));
			w.spawnParticle(Particle.ASH, curr.getX(), curr.getY(), curr.getZ(), 6);
		}
		
	}
	
	public static int testPermission(Player p) {
		int perm = 0;
		if (p.hasPermission("guilds.adventurers.hook.length.1"))
			perm=1;
		if (p.hasPermission("guilds.adventurers.hook.length.2"))
			perm=2;
		if (p.hasPermission("guilds.adventurers.hook.length.3"))
			perm=3;
		if (perm==0)
			return 0;
		int length = AdditionalMechanics.getPlugin().configLoader.getConfig().getInt("guilds.adventurers.hook.length."+perm, 9);
		return length;
	}
}
