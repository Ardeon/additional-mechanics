package ru.ardeon.additionalmechanics.util.sidebar;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class PlayerSidebars {
	final List<String> title = Arrays.asList(" §6§lSenTeeNell ",
			" §e§lS§6§lenTeeNell ",
			" §e§lSe§6§lnTeeNell ",
			" §e§lSen§6§lTeeNell ",
			" §6§lS§e§lenT§6§leeNell ",
			" §6§lSe§e§lnTe§6§leNell ",
			" §6§lSen§e§lTee§6§lNell ",
			" §6§lSenT§e§leeN§6§lell ",
			" §6§lSenTe§e§leNe§6§lll ",
			" §6§lSenTee§e§lNel§6§ll ",
			" §6§lSenTeeN§e§lell ",
			" §6§lSenTeeNe§e§lll ",
			" §6§lSenTeeNel§e§ll ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ",
			" §6§lSenTeeNell ");
	Map<Player, AdmSideBar> bars = new HashMap<Player, AdmSideBar>();
	BukkitRunnable animation = new BukkitRunnable() {
		int n = 0;
		@Override
		public void run() {
			if (title.size()>n) {
				
				for (AdmSideBar bar : getAllBars()){
					bar.obj.setDisplayName(title.get(n));
				}
				n++;
			}
			else
				n=0;
			
		}
	};
	
	public PlayerSidebars(){
		animation.runTaskTimer(AdditionalMechanics.getPlugin(), 200, 1);
	}
	
	public void addPlayer(Player player) {
		bars.put(player, new AdmSideBar());
	}
	
	public void removePlayer(Player player) {
		AdmSideBar bar = bars.get(player);
		if (bar!=null) {
			bar.obj.unregister();
		}
		bars.remove(player);
	}
	
	public Collection<AdmSideBar> getAllBars() {
		return bars.values();
	}
	public AdmSideBar getBar(Player player) {
		return bars.get(player);
	}
}
