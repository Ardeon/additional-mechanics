package ru.ardeon.additionalmechanics.guild.adventurers.portals;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Portalslistener implements Listener {
	Portals portals;
	
	public Portalslistener(Portals portals) {
		this.portals = portals;
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
		UUID id = e.getPlayer().getUniqueId();
		
		portals.map.put(id,portals.get(id));
		
    }
	
	@EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
		UUID id = e.getPlayer().getUniqueId();
		portals.save(id);
		portals.map.remove(id);
    }
}
