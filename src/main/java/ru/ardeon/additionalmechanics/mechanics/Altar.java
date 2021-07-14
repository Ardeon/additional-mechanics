package ru.ardeon.additionalmechanics.mechanics;

import java.util.*;
import java.util.function.Predicate;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.Reloadable;
import ru.ardeon.additionalmechanics.configs.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;

import ru.ardeon.additionalmechanics.mechanics.moon.MoonManager;

public class Altar implements Reloadable {
	private static List<BukkitTask> timers = new ArrayList<>();

	private BossBar chargeBar = Bukkit.createBossBar(Setting.ALTAR_CHARGE_TEXT.getString(), BarColor.WHITE, BarStyle.SOLID);
	private int charge = 0;
	private int messageCooldown = 0;
	private boolean usable = false;
	private BoundingBox area;
	private World w;
	private World targetWorld;
	private boolean activeted;
	private final Predicate<Entity> testPlayer = p -> (p instanceof Player);
	private BukkitTask timerTask;
	private BukkitRunnable checkTimer = new BukkitRunnable()
	{
		@Override
		public void run()
		{
			if (activeted) {
				timeSkip();
				if (messageCooldown>0) {
					messageCooldown--;
				}
			}
		}
	};

    public static void removeAllTimers(){
		for (BukkitTask task : timers){
			task.cancel();
		}
	}

	public Altar(World world, Location loc){
        targetWorld = world;
        activeted = setArea(loc) && Setting.ALTAR_ENABLE.getBool();
        if (activeted)
			timerTask = checkTimer.runTaskTimer(AdditionalMechanics.getPlugin(), 20L, 5L);
	}
	
	public Location getLocation() {
		return new Location(w, area.getCenterX(), area.getCenterY(), area.getCenterZ());
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
		Collection<Entity> players = w.getNearbyEntities(area.clone().expand(6), testPlayer);
		Integer n = players.size();
		chargeBar.removeAll();
		MoonManager moonManager = AdditionalMechanics.getPlugin().getMoonManager();
		if (players.size()>0) {
			if (charge < 0 || !usable) {
				usable = false;
				chargeBar.setColor(BarColor.RED);
				chargeBar.setTitle(Setting.ALTAR_DISCHARGED_TEXT.getString());
				
			}
			double barProgress = ((double) charge)/12000;
			if (barProgress<0) {
				barProgress = 0;
			}
			if (barProgress>1) {
				barProgress = 1;
			}
			chargeBar.setProgress(barProgress);
			long h = targetWorld.getTime()/1000;
			h+=6;
			if (h>=24)
				h-=24;
			Formatter f = new Formatter();
			long min = (targetWorld.getTime()%1000)/17;
			String str = f.format("§6§l%02d:%02d", h, min).toString();
			f.close();
			for (Entity p : players) {
				Player player = (Player) p;
				player.sendTitle(str, "", 0, 10, 0);
				chargeBar.addPlayer(player);
			}
		}
		players = w.getNearbyEntities(area, testPlayer);
		if (players.size()>0) {
			if (moonManager != null && moonManager.fullMoon) {
				for (Entity p : players) {
					Player player = (Player) p;
					player.sendTitle(Setting.ALTAR_MOON_TITLE_TEXT.getString(), Setting.ALTAR_MOON_SUBTITLE_TEXT.getString(), 0, 10, 0);
					chargeBar.addPlayer(player);
				}
			}
			else {
				if (charge > 0 && usable) {
					int delta = 25*n;
					charge -= delta;
					targetWorld.setTime(targetWorld.getTime()+delta);
					if (messageCooldown<=0) {
						Bukkit.broadcastMessage(Setting.ALTAR_BROADCAST_TEXT.getString());
						messageCooldown= 1000;
					}
				} 
				else {
					for (Entity p : players) {
						p.setVelocity(p.getLocation().toVector().subtract(area.getCenter()).normalize().setY(0.2));
						Player player = (Player) p;
						player.sendMessage(Setting.ALTAR_DISCHARGED_MASSAGE.getString());
					}
				}
			}
		} else {
			if (charge<=12000)
				charge+=5;
			if (charge>4000) {
				usable = true;
				chargeBar.setColor(BarColor.WHITE);
				chargeBar.setTitle(Setting.ALTAR_CHARGE_TEXT.getString());
			}
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

	@Override
	public void reload() {
		removeAllTimers();
		chargeBar.removeAll();
	}
}
