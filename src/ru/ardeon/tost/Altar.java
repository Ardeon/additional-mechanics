package ru.ardeon.tost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

public class Altar {
    File configFile;           
    public YamlConfiguration config;  
    List<BoundingBox> bb = new ArrayList<BoundingBox>();
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
				Check();
		}
	};
	
	public Altar(){
		configFile = new File(Tost.p.getDataFolder(), "altar.yml"); 
        configFile.getParentFile().mkdirs();
        config = YamlConfiguration.loadConfiguration(configFile);
        targetWorld = Bukkit.getWorld("world");
        loadYamls();
        activeted = loadBlocks();
        saveYamls();
        checkTimer.runTaskTimer(Tost.getPlugin(), 20L, 20L);
	}
	
	public Altar(World world){
		configFile = new File(Tost.p.getDataFolder(), "altar.yml"); 
        configFile.getParentFile().mkdirs();
        config = YamlConfiguration.loadConfiguration(configFile);
        targetWorld = world;
        loadYamls();
        activeted = loadBlocks();
        saveYamls();
        checkTimer.runTaskTimer(Tost.getPlugin(), 20L, 20L);
	}
	
	public void setWorld(World world) {
		targetWorld = world;
	}

	public void Check() 
    {
		List<Collection<Entity>> players = new ArrayList<Collection<Entity>>();
		for (BoundingBox box : bb) {
			Collection<Entity> p = w.getNearbyEntities(box,testplayer);
			players.add(p);
		}
		Integer n = 0;
		for (Collection<Entity> p : players) {
			if(!p.isEmpty())
				n++;
		}
		if (players.size()>0) {
			targetWorld.setTime(targetWorld.getTime()+100*4*n/players.size());
			return;
		}
			
		long h = targetWorld.getTime()/1000;
		h+=6;
		if (h>=24)
			h-=24;
		Formatter f = new Formatter();
		long min = (targetWorld.getTime()%1000)/17;
		String str = f.format("§6§l%02d:%02d", h, min).toString();
		f.close();
		for (Collection<Entity> p : players)
		{
			for (Entity ent : p) {
				Player player = (Player) ent;
				player.sendTitle(str, "", 3, 10, 3);
			}
		}
    }
	
	public boolean loadBlocks() 
    {
		Set<String> points = config.getKeys(false);
		for(String key : points) {
			Location loc = config.getLocation(key+".location", null);
	        if (loc==null)
	        	return false;
	        if (loc.isWorldLoaded())
	        	w = loc.getWorld();
	        else
	        	return false;
	        Block block = loc.getBlock();
	        bb.add(BoundingBox.of(block));
		}
        return true;
    }
	
    public void loadYamls() {
        try {
            config.load(configFile); //loads the contents of the File to its FileConfiguration
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveYamls() {
        try 
        {
            config.save(configFile); //saves the FileConfiguration to its File
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
