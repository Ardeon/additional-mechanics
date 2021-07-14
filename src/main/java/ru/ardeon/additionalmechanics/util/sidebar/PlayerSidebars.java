package ru.ardeon.additionalmechanics.util.sidebar;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.ScoreboardVars;

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
	final List<String> tips = Arrays.asList("§f/sidebar §7- панель",
			"§f/menu §7- меню",
			
			"§f/setup §7- настройки",
			
			"§f/wiki §7- википедия",
			
			"§f/discord §7- дискорд",
			
			"§f/vk §7- наш вк",
			
			"§f/tpa §7- тп к игроку"
			
			//"§f/sethome §7- определить дом",
			
			//"§f/suicide §7 - убить себя"
			);
	
	private String[] cache = new String[15];
	
	Map<Player, AdmSideBar> bars = new HashMap<Player, AdmSideBar>();
	BukkitRunnable tipsChanger = new BukkitRunnable() {
		int n = 0;
		@Override
		public void run() {
			if (tips.size()>n) {
				
				for (AdmSideBar bar : getAllBars()){
					bar.setString(tips.get(n), 12);;
				}
				n++;
			}
			else
				n=0;
			
		}
	};
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
		tipsChanger.runTaskTimer(AdditionalMechanics.getPlugin(), 400, 600);
	}
	
	public void addPlayer(Player player) {
		AdmSideBar bar = new AdmSideBar(cache);
		bars.put(player, bar);
		if(ScoreboardVars.getVar(player.getName(), "adm_sidebar")==0){
			bar.addViewer(player);
		}
	}
	
	public void removePlayer(Player player) {
		AdmSideBar bar = bars.get(player);
		if (bar!=null) {
			bar.obj.unregister();
		}
		bars.remove(player);
	}
	
	public void sendEventToAll(String string) {
		for (AdmSideBar bar : getAllBars()){
			bar.pushString(string);
		}
		putToCache(string);
	}
	
	public Collection<AdmSideBar> getAllBars() {
		return bars.values();
	}
	public AdmSideBar getBar(Player player) {
		return bars.get(player);
	}
	private void putToCache(String string) {
		for(int i = 1; i < cache.length; i++) {
			cache[i-1] = cache[i];
		}
		cache[cache.length - 1] = string;
	}
}
