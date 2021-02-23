package ru.ardeon.additionalmechanics;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import ru.ardeon.additionalmechanics.common.antitarget.TargetListener;
import ru.ardeon.additionalmechanics.configs.ConfigLoader;
import ru.ardeon.additionalmechanics.guild.GuildsController;
import ru.ardeon.additionalmechanics.mainCommands.CommandManager;
import ru.ardeon.additionalmechanics.mechanics.Altar;
import ru.ardeon.additionalmechanics.mechanics.builds.BuildManager;
import ru.ardeon.additionalmechanics.mechanics.moon.MoonManager;
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
    private MoonManager moonManager;
    
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
		catch(IllegalStateException e) {
			this.getLogger().log(Level.WARNING, "ERROR LuckPerms not load");
		};
		gc = new GuildsController();
		interactSkillSwitcher = new InteractSkillSwitcher();
		getServer().getPluginManager().registerEvents(new EventsListener(interactSkillSwitcher), this);
		getServer().getPluginManager().registerEvents(new ScrollListener(), this);
		rm = new RandomManager(this);
		varManager = new VarManager(this);
		
		
		CommandManager.CommandRegister();
		
		loadRecipe();
		setMoonManager();
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
    
    public void setMoonManager() {
    	String worldName = configLoader.getConfig().getString("MoonWorldCheck", "Wild");
    	World world = Bukkit.getWorld(worldName);
    	if (world!=null) {
    		moonManager = new MoonManager(world, this);
    	}
    }
    
    public MoonManager getMoonManager() {
		return moonManager;
    }
    
    public void loadRecipe() {
    	ItemStack spawnerStack = new ItemStack(Material.SPAWNER, 1);
    	NamespacedKey spawner = new NamespacedKey(this, "spawner");
    	ShapedRecipe recipe = new ShapedRecipe(spawner, spawnerStack);
    	recipe.shape("RBR", "ETE", "MMM");
    	recipe.setIngredient('R', Material.IRON_BARS);
        recipe.setIngredient('E', Material.END_CRYSTAL);
        recipe.setIngredient('B', Material.DRAGON_BREATH);
        recipe.setIngredient('T', Material.ENCHANTING_TABLE);
        recipe.setIngredient('M', Material.BEACON);
        Bukkit.addRecipe(recipe);
    }
    
    public LuckPerms getLP() {
    	return lpapi;
    }

}