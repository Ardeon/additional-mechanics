package ru.ardeon.additionalmechanics.mechanics.moon;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class MoonListener implements Listener {
	
	World world;
	MoonManager mm;
	
	MoonListener(MoonManager mm){
		this.mm = mm;
		this.world = mm.world;
	}
	
	@EventHandler
	public void onPlayUseBed(PlayerBedEnterEvent e) {
		Player player = e.getPlayer();
		World w = player.getWorld();
		if (mm.fullMoon&&(w.equals(world))) {
			e.setUseBed(Result.DENY);
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("§c▎ §fЭтой ночью вам не удастся спокойно заснуть §c▎").create());
		}
	}
	
}
