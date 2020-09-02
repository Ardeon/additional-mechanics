package ru.ardeon.tost.myEntity;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public abstract class Totem {
	public Player p;
	public ArmorStand a;
	public int timer;
	
	public Totem() {
		
	}
	public Totem(Player p) {
		this.p = p;
		this.a = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		a.setSmall(true);
		a.addScoreboardTag("untouchable");
		timer = 16;
		a.setInvulnerable(true);
	}

}
