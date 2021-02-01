package ru.ardeon.additionalmechanics.mainCommands;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.StopTimerCommand;
import ru.ardeon.additionalmechanics.mechanics.builds.RegisterBuildCommand;
import ru.ardeon.additionalmechanics.mechanics.builds.TestBuildCommand;
import ru.ardeon.additionalmechanics.mechanics.tempterritory.TestRegionCommand;
import ru.ardeon.additionalmechanics.randomchest.Fakeinv;
import ru.ardeon.additionalmechanics.vars.DonateAddCommand;

public class CommandManager {
	public static void CommandRegister() {
		AdditionalMechanics t = AdditionalMechanics.getPlugin();
        t.getServer().getPluginCommand("tost").setExecutor(new TostCommand());
        t.getServer().getPluginCommand("pblock").setExecutor(new PblockCommand());
        t.getServer().getPluginCommand("altar").setExecutor(new AltarCommand());
        t.getServer().getPluginCommand("stoptimer").setExecutor(new StopTimerCommand());
        t.getServer().getPluginCommand("gr").setExecutor(new GradientCommand());
        t.getServer().getPluginCommand("permkit").setExecutor(new PermkitCommand());
        t.getServer().getPluginCommand("registerbuild").setExecutor(new RegisterBuildCommand());
        t.getServer().getPluginCommand("testbuild").setExecutor(new TestBuildCommand());
        t.getServer().getPluginCommand("trg").setExecutor(new TestRegionCommand());
        t.getServer().getPluginCommand("donateadd").setExecutor(new DonateAddCommand(t.varManager));
        t.getServer().getPluginCommand("fakeinv").setExecutor(new Fakeinv(t.rm));
        t.getServer().getPluginCommand("prefix").setExecutor(new PrefixCommand(t.getLP()));
        t.getServer().getPluginCommand("setitemtag").setExecutor(new SetItemTagCommand());
        t.getServer().getPluginCommand("getitemtag").setExecutor(new GetItemTagCommand());
	}
}
