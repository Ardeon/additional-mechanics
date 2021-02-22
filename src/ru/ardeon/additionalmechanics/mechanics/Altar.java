package ru.ardeon.additionalmechanics.mechanics;

import java.util.Collection;
import java.util.Formatter;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class Altar {
	BossBar chargeBar = Bukkit.createBossBar("Заряд алтаря", BarColor.WHITE, BarStyle.SOLID);
    int charge = 0;
    BoundingBox area;
    World w;
    World targetWorld;
    boolean activeted;
    Predicate<Entity> testplayer = p -> (p instanceof Player);
    BukkitRunnable checkTimer = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			if (activeted)
				timeSkip();
		}
	};
	
	public Altar(){
		
	}
	
	public Altar(World world, Location loc){
        targetWorld = world;
        activeted = setArea(loc);
        if (activeted)
        	checkTimer.runTaskTimer(AdditionalMechanics.getPlugin(), 20L, 5L);
	}
	
	public void setWorld(World world) {
		targetWorld = world;
	}
	
	public void disable() {
		try {
			chargeBar.removeAll();
			if (checkTimer != null && !checkTimer.isCancelled())
				checkTimer.cancel();
		}
		catch(Exception e) {};
	}

	public void timeSkip() 
    {
		Collection<Entity> players = w.getNearbyEntities(area, testplayer);
		Integer n = players.size();
		chargeBar.removeAll();
		if (players.size()>0) {
			if (charge > 0) {
				int delta = 25*n;
				charge -= delta;
				targetWorld.setTime(targetWorld.getTime()+delta);
				//AdditionalMechanics.getPlugin().getLogger().info(""+charge);
			}
			chargeBar.setVisible(true);
			double barProgress = ((double) charge)/12000;
			if (barProgress<0) {
				barProgress = 0;
			}
			if (barProgress>1) {
				barProgress = 1;
			}
			chargeBar.setProgress(barProgress);
			for (Entity player : players) {
				chargeBar.addPlayer((Player)player);
			}
			
			//return;
		}
		else {
			if (charge<=12000)
				charge+=5;
		}
			
		long h = targetWorld.getTime()/1000;
		h+=6;
		if (h>=24)
			h-=24;
		Formatter f = new Formatter();
		long min = (targetWorld.getTime()%1000)/17;
		String str = f.format("§6§l%02d:%02d", h, min).toString();
		f.close();
		for (Entity p : players)
		{
			Player player = (Player) p;
			player.sendTitle(str, "", 0, 10, 0);
		}
    }
	
	public boolean setArea(Location loc) 
    {
        if (loc==null)
        	return false;
        if (loc.isWorldLoaded())
        	w = loc.getWorld();
        else
        	return false;
        area = BoundingBox.of(loc, 3, 3, 3);
        return true;
    }
}
