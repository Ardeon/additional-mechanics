package ru.ardeon.additionalmechanics;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import ru.ardeon.additionalmechanics.configs.ConfigLoader;
import ru.ardeon.additionalmechanics.guild.GuildsController;
import ru.ardeon.additionalmechanics.mainCommands.CommandManager;
import ru.ardeon.additionalmechanics.mechanics.builds.BuildManager;
import ru.ardeon.additionalmechanics.mechanics.portal.ScrollListener;
import ru.ardeon.additionalmechanics.randomchest.RandomManager;
import ru.ardeon.additionalmechanics.vars.VarManager;

public class AdditionalMechanics extends JavaPlugin{
    static AdditionalMechanics p; 
    static HashSet<Block> pushBlocks = new HashSet<Block>();
    public static Altar altar;
    public static BuildManager bm;
    GuildsController gc;
    public ConfigLoader configLoader;
    public VarManager varManager;
    public RandomManager rm;
    private LuckPerms lpapi = null;
    public SkillSwitcher skillSwitcher;
	
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
    	RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    	if (provider != null) {
    	    lpapi = provider.getProvider();
    	}
    	gc = new GuildsController();
    	skillSwitcher = new SkillSwitcher();
    	getServer().getPluginManager().registerEvents(new EventsListener(skillSwitcher), this);
    	getServer().getPluginManager().registerEvents(new ScrollListener(), this);
        rm = new RandomManager(this);
        loadBlocks();
        varManager = new VarManager(this);
        
        CommandManager.CommandRegister();
        //altar = new Altar();
        bm = new BuildManager();
        getLogger().info("AdditionalMechanics started!");
    }
    
    public LuckPerms getLP() {
    	return lpapi;
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