package ru.ardeon.tost.mainCommands;

import ru.ardeon.tost.StopTimerCommand;
import ru.ardeon.tost.Tost;

public class CommandManager {
	public static void CommandRegister() {
		Tost t = Tost.getPlugin();
        t.getServer().getPluginCommand("tost").setExecutor(new TostCommand());
        t.getServer().getPluginCommand("pblock").setExecutor(new PblockCommand());
        t.getServer().getPluginCommand("altar").setExecutor(new AltarCommand());
        t.getServer().getPluginCommand("stoptimer").setExecutor(new StopTimerCommand());
	}
}
