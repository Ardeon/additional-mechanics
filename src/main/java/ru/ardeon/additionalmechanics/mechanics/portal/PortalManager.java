package ru.ardeon.additionalmechanics.mechanics.portal;

import java.util.ArrayList;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PortalManager {
	static private PortalManager portalManager = null;
	static public PortalManager getInstance() {
		if (portalManager==null) {
			portalManager = new PortalManager();
		}
		return portalManager;
	}
	
	private FileConfiguration config;
	private ArrayList<Portal> portals = new ArrayList<Portal>();
	
	private PortalManager() {
		config = AdditionalMechanics.getPlugin().configLoader.getConfigPortals();
		load();
	}
	
	private void load() {
		ConfigurationSection portalsconfig = config.getConfigurationSection("portals");
		if (portalsconfig==null) {
			portalsconfig = config.createSection("portals");
		}
		for (String portalconfig : portalsconfig.getKeys(false)) {
			portals.add(new Portal(portalsconfig.getConfigurationSection(portalconfig)));
		}
	}
	
	public void createPortal(Player player) {
		Location location = player.getLocation();
		String owner = player.getName();
		addPortal(new Portal(location, owner));
	}
	
	private void addPortal(Portal portal) {
		if (!config.isInt("lastID")) {
			config.set("lastID", 0);
		}
		int lastID = config.getInt("lastID");
		int id = lastID + 1;
		config.set("lastID", id);
		ConfigurationSection portalsconfig = config.getConfigurationSection("portals");
		ConfigurationSection newPortal = portalsconfig.createSection(""+id);
		portal.fillConfigSection(newPortal);
	}
	
	public PortalMenu getMenuAllPortals() {
		PortalMenu menu = new PortalMenu("§aПорталы", getPortals());
		return menu;
	}
	
	public Portal[] getPortals() {
		int lastIndex = 45;
		if (portals.size()<45)
			lastIndex = portals.size();
		return portals.subList(0, lastIndex).toArray(new Portal[0]);
	}
}
