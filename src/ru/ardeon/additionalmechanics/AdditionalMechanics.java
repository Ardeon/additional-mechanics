package ru.ardeon.additionalmechanics;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import ru.ardeon.additionalmechanics.configs.ConfigLoader;
import ru.ardeon.additionalmechanics.guild.GuildsController;
import ru.ardeon.additionalmechanics.mainCommands.CommandManager;
import ru.ardeon.additionalmechanics.mechanics.builds.BuildManager;

public class AdditionalMechanics extends JavaPlugin{
    static AdditionalMechanics p; 
    static HashSet<Block> pushBlocks = new HashSet<Block>();
    public static Altar altar;
    public static BuildManager bm;
    GuildsController gc;
    public ConfigLoader configLoader;
	
    public static AdditionalMechanics getPlugin() {
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
        //altar = new Altar();
        bm = new BuildManager();
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