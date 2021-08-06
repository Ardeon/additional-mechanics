package ru.ardeon.additionalmechanics;


import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import ru.ardeon.additionalmechanics.configs.SettingsLoaderMain;
import ru.ardeon.additionalmechanics.util.discord.DiscordBot;
import ru.ardeon.additionalmechanics.util.sidebar.PlayerSidebars;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import ru.ardeon.additionalmechanics.common.antitarget.TargetListener;
import ru.ardeon.additionalmechanics.configs.ConfigLoader;
import ru.ardeon.additionalmechanics.guild.GuildsController;
import ru.ardeon.additionalmechanics.guild.adventurers.horse.HorseController;
import ru.ardeon.additionalmechanics.mainCommands.CommandManager;
import ru.ardeon.additionalmechanics.mechanics.Altar;
import ru.ardeon.additionalmechanics.mechanics.builds.BuildManager;
import ru.ardeon.additionalmechanics.mechanics.moon.MoonManager;
import ru.ardeon.additionalmechanics.mechanics.portal.ScrollListener;
import ru.ardeon.additionalmechanics.randomchest.RandomManager;
import ru.ardeon.additionalmechanics.vars.VarManager;

/***
 * Главный класс
 * @author First-114
 */
public class AdditionalMechanics extends JavaPlugin{
    private static AdditionalMechanics p;
	private static Altar altar;
    private static BuildManager bm;
    private GuildsController gc;
	private ConfigLoader configLoader;
	private VarManager varManager;
	private RandomManager rm;
    private LuckPerms lpapi = null;
    private MoonManager moonManager;
	private PlayerSidebars sideBars;
	private DiscordBot discordBot;
    private final List<WeakReference<Reloadable>> reloadables = new ArrayList<>();
	private static Logger loggerADM = LogManager.getLogger("Adm");

    public static AdditionalMechanics getPlugin() {return p;}
	/***
	 * Получить главный {@link #altar}
	 * @return Алтарь хорошей погоды
	 */
	public static Altar getAltar(){ return altar; }
	public static BuildManager getBuildManager(){ return bm; }

	/**
	 * @return Загрузчик конфигураций
	 */
	public ConfigLoader getConfigLoader(){ return configLoader; }

	/**
	 * @return Менеджер переменных
	 */
	public VarManager getVarManager(){ return varManager; }

	/**
	 * @return Генератор случайных предметов
	 */
	public RandomManager getRandomManager() { return rm; }

	/**
	 * @return Ссылка на плагин LuckPerms или null если плагин не загружен
	 */
    public LuckPerms getLP() {
        return lpapi;
    }
    public DiscordBot getDiscordBot() { return discordBot; }

	/**
	 * @return Логгер записывающий в отдельный файл
	 */
    public static Logger getLoggerADM() { return loggerADM; }

	/**
	 * @return Менеджер боковой панели
	 */
	public PlayerSidebars getSideBars() { return sideBars; }

	/**
	 * Перезапуск некоторых элементов
	 */
	public void reload(){
    	for (WeakReference<Reloadable> item : reloadables){
    		Objects.requireNonNull(item.get()).reload();
		}
		setAltar();
	}

	/**
	 * @param item
	 */
	public void addReloadingItem(Reloadable item){
		reloadables.add(new WeakReference<>(item));
	}

	@Override
    public void onDisable() {
    	HorseController.getInstace().clear();
    }
    
    @Override
    public void onEnable() 
    {
//
		setLogger();
		loggerADM.warn("start logger");
		p=this;
		configLoader = new ConfigLoader();
		try {
			lpapi = LuckPermsProvider.get();
		}
		catch(IllegalStateException e) {
			this.getLogger().log(Level.WARNING, "ERROR LuckPerms not load");
		}
		gc = new GuildsController();
		sideBars = new PlayerSidebars();
		for(Player player : Bukkit.getOnlinePlayers()) {
			sideBars.addPlayer(player);
			sideBars.getBar(player).addViewer(player);
		}
		
		rm = new RandomManager(this);
		varManager = new VarManager(this);
		
		CommandManager.CommandRegister();
		try {
            discordBot = new DiscordBot();
		} catch (SQLException | IOException throwables) {
			throwables.printStackTrace();
		}

		loadRecipe();
		setMoonManager();
		setAltar();
		bm = new BuildManager();
		getLogger().info("AdditionalMechanics started!");
		
		getServer().getPluginManager().registerEvents(new EventsListener(), this);
		getServer().getPluginManager().registerEvents(new ScrollListener(), this);
		getServer().getPluginManager().registerEvents(new TargetListener(), this);
    }

	/**
	 * Загрузка алтаря
	 */
    public void setAltar() {
    	if (altar!=null) {
    		altar.disable();
    	}
    	String world = SettingsLoaderMain.SettingMain.ALTAR_WORLD.getString();
    	Location loc = configLoader.getMain().getConfig().getLocation("altar.location", null);
        World targetWorld;
        if (Bukkit.getWorld(world)!=null) {
        	targetWorld = Bukkit.getWorld(world);
        }
        else {
        	targetWorld = Bukkit.getWorlds().get(0);
        }
        altar = new Altar(targetWorld, loc);
    }

	/**
	 * Создание логгера
	 * логирование происходит отдельно от основного логгера и не отображается в консоли
	 */
    private void setLogger() {
		LoggerContext context = new LoggerContext("AdmLoggerContext");
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("log4j2.xml");
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! ");
		}
		XmlConfiguration xmlConfiguration = null;
		try {
			xmlConfiguration = new XmlConfiguration(context, new org.apache.logging.log4j.core.config.ConfigurationSource(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (xmlConfiguration!=null)
			context.setConfiguration(xmlConfiguration);
		loggerADM = context.getLogger("Adm");
	}

	/**
	 * Загрузка менеджера ивента "кровавая луна"
	 */
    public void setMoonManager() {
    	if(SettingsLoaderMain.SettingMain.MOON_MANAGER.getBool()){
			String worldName = SettingsLoaderMain.SettingMain.MOON_WORLD_CHECK.getString();
			World world = Bukkit.getWorld(worldName);
			if (world!=null) {
				moonManager = new MoonManager(world, this);
			}
		}

    }

	/**
	 * @return Менеджер ивента "кровавая луна"
	 */
    public MoonManager getMoonManager() {
		return moonManager;
    }

	/**
	 * Загрузка рецепта спавнера
	 */
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
        if (Bukkit.getRecipe(spawner)!=null)
        	Bukkit.addRecipe(recipe);
    }
    


}