package ru.ardeon.additionalmechanics.mainCommands;

import org.bukkit.command.PluginCommand;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.mechanics.builds.RegisterBuildCommand;
import ru.ardeon.additionalmechanics.mechanics.builds.TestBuildCommand;
import ru.ardeon.additionalmechanics.util.discord.DiscordConfirmCmd;
import ru.ardeon.additionalmechanics.vars.DonateAddCommand;
import ru.ardeon.additionalmechanics.StopTimerCommand;
import ru.ardeon.additionalmechanics.mainCommands.roll.dicecmd;
import ru.ardeon.additionalmechanics.mainCommands.roll.flipcmd;
import ru.ardeon.additionalmechanics.mainCommands.roll.rollcmd;
import ru.ardeon.additionalmechanics.mechanics.tempterritory.TestRegionCommand;
import ru.ardeon.additionalmechanics.randomchest.Fakeinv;

public class CommandManager {
	public static void CommandRegister() {
		AdditionalMechanics t = AdditionalMechanics.getPlugin();
        PluginCommand mainCommand = t.getServer().getPluginCommand("adm");
        mainCommand.setExecutor(new ADMCommand(mainCommand));
        t.getServer().getPluginCommand("altar").setExecutor(new AltarCommand());
        t.getServer().getPluginCommand("stoptimer").setExecutor(new StopTimerCommand());
        t.getServer().getPluginCommand("gr").setExecutor(new GradientCommand());
        //t.getServer().getPluginCommand("permkit").setExecutor(new PermkitCommand());
        t.getServer().getPluginCommand("registerbuild").setExecutor(new RegisterBuildCommand());
        t.getServer().getPluginCommand("testbuild").setExecutor(new TestBuildCommand());
        t.getServer().getPluginCommand("trg").setExecutor(new TestRegionCommand());
        t.getServer().getPluginCommand("donateadd").setExecutor(new DonateAddCommand(t.getVarManager()));
        t.getServer().getPluginCommand("fakeinv").setExecutor(new Fakeinv(t.getRandomManager()));
        t.getServer().getPluginCommand("prefix").setExecutor(new PrefixCommand(t.getLP()));
        t.getServer().getPluginCommand("setitemtag").setExecutor(new SetItemTagCommand());
        t.getServer().getPluginCommand("getitemtag").setExecutor(new GetItemTagCommand());
        
		t.getServer().getPluginCommand("flip").setExecutor(new flipcmd());
		t.getServer().getPluginCommand("roll").setExecutor(new rollcmd());
		t.getServer().getPluginCommand("dice").setExecutor(new dicecmd());
		t.getServer().getPluginCommand("guild").setExecutor(new guildregister());
		t.getServer().getPluginCommand("giveperm").setExecutor(new GivePerm());
		t.getServer().getPluginCommand("moonskip").setExecutor(new MoonskipCommand());
		
		t.getServer().getPluginCommand("sidebar").setExecutor(new AdmSidebarCommand());
		
		t.getServer().getPluginCommand("portaladd").setExecutor(new PortalAddCmd());
		t.getServer().getPluginCommand("portalmenu").setExecutor(new PortalMenuCmd());

        t.getServer().getPluginCommand("discordconfirm").setExecutor(new DiscordConfirmCmd());

	}
}
