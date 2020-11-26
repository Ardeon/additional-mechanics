package ru.ardeon.additionalmechanics.mechanics.tempterritory;

import org.bukkit.scheduler.BukkitRunnable;

public class TScheduler extends BukkitRunnable {
	
	private final TerritoryManager manager;

    public TScheduler(TerritoryManager manager) {
        this.manager = manager;
    }
    
	@Override
	public void run() {
		for(TerritoryMember m : manager.members) {
			if (m.endDate < System.currentTimeMillis()) {
				deleteMember(m);
				manager.members.remove(m);
			}
		}
		
	}
	public void deleteMember(TerritoryMember m) {
		
	}
}
