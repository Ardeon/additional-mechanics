package ru.ardeon.tost;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import ru.ardeon.tost.configs.ConfigLoader;
import ru.ardeon.tost.guild.GuildsController;
import ru.ardeon.tost.mainCommands.CommandManager;

public class Tost extends JavaPlugin{
    static Tost p; 
    static HashSet<Block> pushBlocks = new HashSet<Block>();
    public static Altar altar;
    GuildsController gc;
    public ConfigLoader configLoader;
	
    public static Tost getPlugin() {
    	return p;
    }
	
    @Override
    public void onDisable() {
    	configLoader.saveYamls();
        gc.getAdventurers().getPortalManager().saveAll();
    }

    @Override
    public void onEnable() 
    {
    	p=this;
    	configLoader = new ConfigLoader();
    	gc = new GuildsController();
    	getLogger().info("Мой первый плагин!");
    	getServer().getPluginManager().registerEvents(new EventsListener(), this);
        CommandManager.CommandRegister();
        loadBlocks();
        altar = new Altar();
    }
    

    public void loadBlocks() 
    {
    	pushBlocks.clear();
    	Set<String> k = configLoader.getConfigBlocks().getKeys(false);
        for (String key : k) 
		{
        	Location l = configLoader.getConfigBlocks().getLocation(key+".location", null);
        	if (l!=null) {
        		Block b = l.getBlock();
        		pushBlocks.add(b);
        	}
		}
    }

}