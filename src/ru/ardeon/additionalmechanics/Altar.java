package ru.ardeon.additionalmechanics;

import java.util.Collection;
import java.util.Formatter;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

public class Altar {
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
			if (checkTimer != null && !checkTimer.isCancelled())
				checkTimer.cancel();
		}
		catch(Exception e) {};
	}

	public void timeSkip() 
    {
		Collection<Entity> players = w.getNearbyEntities(area, testplayer);
		Integer n = players.size();
		if (players.size()>0) {
			targetWorld.setTime(targetWorld.getTime()+25*4*n);
			//return;
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
			player.sendTitle(str, "", 3, 10, 3);
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
