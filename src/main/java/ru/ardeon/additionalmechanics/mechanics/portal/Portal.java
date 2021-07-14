package ru.ardeon.additionalmechanics.mechanics.portal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Portal {
	private Location location;
	private String owner;
	private List<String> members;
	private boolean publicUse;
	private Material iconMaterial;
	
	public Portal(ConfigurationSection portalSection) {
		location = portalSection.getLocation("location");
		members = portalSection.getStringList("members");
		owner = portalSection.getString("owner");
		publicUse = portalSection.getBoolean("public", false);
		try {
			iconMaterial = Material.valueOf(portalSection.getString("icon"));
		}catch(Exception e) {
			iconMaterial = Material.APPLE;
		}
		
	}
	
	public Portal(Location location, String owner) {
		this.location = location;
		roundLocation();
		this.owner = owner;
		members = new ArrayList<String>();
		publicUse = false;
		iconMaterial = Material.APPLE;
	}
	
	private void roundLocation() {
		location.setX(Math.floor(location.getX())+0.5);
		location.setY(Math.floor(location.getY())+0.5);
		location.setZ(Math.floor(location.getZ())+0.5);
		location.setPitch(0);
		location.setYaw(0);
	}
	
	public void fillConfigSection(ConfigurationSection section) {
		section.set("members", members);
		section.set("owner", owner);
		section.set("public", publicUse);
		section.set("icon", iconMaterial.name());
		section.set("location", location);
	}
	
	public boolean playerHasAccess(Player p) {
		if (publicUse)
			return true;
		return false;
	}
	
	public ItemStack getItemIcon() {
		ItemStack icon = new ItemStack(iconMaterial);
		ItemMeta itemMeta;
		if (icon.hasItemMeta())
			itemMeta = icon.getItemMeta();
		else
			itemMeta = Bukkit.getItemFactory().getItemMeta(iconMaterial);
		List<String> lore = new ArrayList<String>();
		if(location!=null) {
			lore.add(location.getWorld().getName());
			lore.add("->X = " + location.getBlockX());
			lore.add("->Y = " + location.getBlockY());
			lore.add("->Z = " + location.getBlockZ());
		}
		if(owner!=null) {
			lore.add("Владелец "+owner);
		}
		if(members!=null&&members.size()>0) {
			lore.add("Имеют доступ");
			for(String name : members) {
				lore.add("-> "+name);
			}
		}
		itemMeta.setLore(lore);
		icon.setItemMeta(itemMeta);
		return icon;
	}
}
