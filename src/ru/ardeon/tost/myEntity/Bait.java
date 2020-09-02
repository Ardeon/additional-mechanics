package ru.ardeon.tost.myEntity;

import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.tost.Tost;

public class Bait {
	public Player p;
	public ArmorStand a;
	public int timer;
	BukkitRunnable agro = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			timer--;
			World w = a.getWorld();
			w.playSound(a.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 2, 1.2f);
			if (timer>0) {
				p.sendTitle("", "Приманка пропадёт через: "+(timer*2), 3, 10, 3);
			}
			List<Entity> mobs = a.getNearbyEntities(10, 4, 10);
			for (Entity m: mobs)
			{
				if (m instanceof Mob)
				{
					Mob mob = (Mob) m;
					mob.setTarget(a);
					w.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
				}
			}
			if (timer<=0)
			{
				agro.cancel();
				//w.createExplosion(a.getLocation(), 1, false, false , p);
				a.remove();
				p.sendTitle("", "Приманка сломана!", 5, 10, 5);
				agro = null;
			}
		}
	};
	
	public Bait() {
		
	}
	public Bait(Player p, ArmorStand a) {
		this.p = p;
		this.a = a;
		timer = 6;
	}
	public void agrostart() {
		agro.runTaskTimer(Tost.getPlugin(), 0, 40);
	}
}
