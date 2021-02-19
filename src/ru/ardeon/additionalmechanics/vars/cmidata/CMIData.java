package ru.ardeon.additionalmechanics.vars.cmidata;

import org.bukkit.entity.Player;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;

public class CMIData {
	CMI cmi;
	
	public CMIData() {
		try {
			cmi = CMI.getInstance();
    	}
    	catch(IllegalStateException e) {};
	}
	
	public String data(Player player, String field) {
		CMIUser user = CMI.getInstance().getPlayerManager().getUser(player);
		return user.getMeta().getValue(field);
	}
}
