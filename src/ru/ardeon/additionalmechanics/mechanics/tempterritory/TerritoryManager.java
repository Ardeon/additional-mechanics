package ru.ardeon.additionalmechanics.mechanics.tempterritory;

import java.util.List;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class TerritoryManager {
	WorldGuardPlugin wgp;
	WorldGuard wg;
	AdditionalMechanics am;
	RegionContainer container;
	RegionManager regs;
	public List<TerritoryMember> members;
	
	public TerritoryManager(AdditionalMechanics am) {
		this.am = am;
		wgp = getWGP();
		if (wgp!=null) {
			wg = WorldGuard.getInstance();
		}
	}
	
	void test() {
		container = wg.getPlatform().getRegionContainer();
		World world;
		//regs = container.get(world);
	}
	
	private WorldGuardPlugin getWGP() {
		Plugin plugin = am.getServer().getPluginManager().getPlugin("WorldGuard");
		
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}
		
		return (WorldGuardPlugin) plugin;
	}
	
}
