package ru.ardeon.additionalmechanics.util.sidebar;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class AdmSideBar {
	Scoreboard board;
	Objective obj;
	String[] strings = new String[15];
	Team[] messages = new Team[15];
	//DeathMessages deathMessages;
	
	public AdmSideBar(){
		Arrays.fill(strings, "");
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("events", "dummy", "События");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		for(int i = 14; i >= 0; i--) {
			obj.getScore("§" + getMask(i)).setScore(i+1);
			messages[i] = board.registerNewTeam(""+i);
			messages[i].addEntry("§" + getMask(i));
			messages[i].setPrefix("");
		}
		//try {
		//	deathMessages = DeathMessages.plugin;
		//}
		//catch(IllegalStateException e) {
		//	AdditionalMechanics.getPlugin().getLogger().log(Level.WARNING, "ERROR LuckPerms not load");
		//};
	}
	
	public void addViewer(Player player) {
		player.setScoreboard(board);
	}
	
	public void removeViewer(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
	}
	
	public void pushString(String string) {
		for(int i = 1; i <= 14; i++) {
			messages[i-1].setPrefix(messages[i].getPrefix());
			messages[i-1].setSuffix(messages[i].getSuffix());
		}
		if (string.length()>64) {
			messages[14].setPrefix(string.substring(0, 64));
			//string.lastIndexOf("§r");
			//string.lastIndexOf("§", 0)
			if (string.length()>126)
				messages[14].setSuffix("§7" + string.substring(64, 126));
			else
				messages[14].setSuffix("§7" + string.substring(64));
		}
		else {
			messages[14].setPrefix(string);
			messages[14].setSuffix("");
		}
			
	}
	
	private String getMask(int i) {
		switch (i) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return "" + i;
		case 10:
			return "a";
		case 11:
			return "b";
		case 12:
			return "c";
		case 13:
			return "d";
		case 14:
			return "e";
		case 15:
			return "f";
		}
		return null;
	}
}
