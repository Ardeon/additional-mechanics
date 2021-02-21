package ru.ardeon.additionalmechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import ru.ardeon.additionalmechanics.common.antitarget.TargetListener;
import ru.ardeon.additionalmechanics.configs.ConfigLoader;
import ru.ardeon.additionalmechanics.guild.GuildsController;
import ru.ardeon.additionalmechanics.mainCommands.CommandManager;
import ru.ardeon.additionalmechanics.mechanics.builds.BuildManager;
import ru.ardeon.additionalmechanics.mechanics.portal.ScrollListener;
import ru.ardeon.additionalmechanics.randomchest.RandomManager;
import ru.ardeon.additionalmechanics.vars.VarManager;

public class AdditionalMechanics extends JavaPlugin{
    static AdditionalMechanics p; 
    public static Altar altar;
    public static BuildManager bm;
    GuildsController gc;
    public ConfigLoader configLoader;
    public VarManager varManager;
    public RandomManager rm;
    private LuckPerms lpapi = null;
    public InteractSkillSwitcher interactSkillSwitcher;
    
    public static AdditionalMechanics getPlugin() {
    	return p;
    }
	
    @Override
    public void onDisable() {
    	configLoader.saveYamls();
    }
    
    @Override
    public void onEnable() 
    {
    	p=this;
    	configLoader = new ConfigLoader();
    	try {
    		lpapi = LuckPermsProvider.get();
    	}
    	catch(IllegalStateException e) {};
    	//RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    	//if (provider != null) {
    	//    lpapi = provider.getProvider();
    	//}
    	gc = new GuildsController();
    	interactSkillSwitcher = new InteractSkillSwitcher();
    	getServer().getPluginManager().registerEvents(new EventsListener(interactSkillSwitcher), this);
    	getServer().getPluginManager().registerEvents(new ScrollListener(), this);
        rm = new RandomManager(this);
        varManager = new VarManager(this);
        
        CommandManager.CommandRegister();
        setAltar();
        bm = new BuildManager();
        getLogger().info("AdditionalMechanics started!");
        getServer().getPluginManager().registerEvents(new TargetListener(), this);
    }
    
    public void setAltar() {
    	if (altar!=null) {
    		altar.disable();
    	}
    	String world = configLoader.getConfigAltar().getString("world", "wild");
    	Location loc = configLoader.getConfigAltar().getLocation("location", null);
        World targetWorld = null;
        if (Bukkit.getWorld(world)!=null) {
        	targetWorld = Bukkit.getWorld(world);
        }
        else {
        	targetWorld = Bukkit.getWorlds().get(0);
        }
        altar = new Altar(targetWorld, loc);
    }
    
    public LuckPerms getLP() {
    	return lpapi;
    }

}