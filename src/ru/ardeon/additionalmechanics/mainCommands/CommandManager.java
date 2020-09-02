package ru.ardeon.additionalmechanics.mainCommands;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.StopTimerCommand;

public class CommandManager {
	public static void CommandRegister() {
		AdditionalMechanics t = AdditionalMechanics.getPlugin();
        t.getServer().getPluginCommand("tost").setExecutor(new TostCommand());
        t.getServer().getPluginCommand("pblock").setExecutor(new PblockCommand());
        t.getServer().getPluginCommand("altar").setExecutor(new AltarCommand());
        t.getServer().getPluginCommand("stoptimer").setExecutor(new StopTimerCommand());
	}
}
