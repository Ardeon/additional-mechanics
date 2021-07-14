package ru.ardeon.additionalmechanics;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class StopTimerCommand implements CommandExecutor {

	static BossBar bar = Bukkit.createBossBar("Рестарт через", BarColor.RED, BarStyle.SOLID);
	static int time=0;//2seconds
	static int maxtime=150;
	static BukkitRunnable stoptimer;
	static BukkitTask task;
	
	public StopTimerCommand() {
		reset();
	}
	
	void reset() {
		bar.setProgress(1);
		bar.setVisible(false);
		bar.removeAll();
		if (task!=null)
			task.cancel();
		stoptimer = new BukkitRunnable() 
		{
			@Override
			public void run()
			{
				String mess = "§8[§c⌛§8] §7 Рестарт через: §c§l";
				if ((time%150)==0) {
					mess = mess + time/30 + " минут";
					Bukkit.broadcastMessage(mess);
				}
				if (time==90) {
					mess = mess + "3 минуты";
					Bukkit.broadcastMessage(mess);
				}
				if (time==30) {
					mess = mess + "1 минуту";
					Bukkit.broadcastMessage(mess);
				}
				time--;
				if (time<0) {
					Bukkit.shutdown();
					return;
				}
				if (time<150) {
					bar.setProgress((double)time/maxtime);
					bar.setTitle("Рестарт через: " + time/30 + " минут " + (time%30)*2 + " секунд");
					bar.setVisible(true);
					for (Player p : Bukkit.getOnlinePlayers()) {
						bar.addPlayer(p);
					}
				}
				
			}
		};
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length==1)
		{
			if (args[0].equals("cancel")) {
				reset();
				Bukkit.broadcastMessage("§8[§c⌛§8] §7 Рестарт отменён");
				return true;
			}
			int mins;
			try {
			   mins = Integer.parseInt(args[0]);
			   if (mins<1)
				   return false;
			   time = mins*30;
			   maxtime = time;
			   if (maxtime>150)
				   maxtime=150;
			   reset();
			   task = stoptimer.runTaskTimer(AdditionalMechanics.getPlugin(), 0L, 40L);
			   if ((mins%5)!=0&&mins!=3&&mins!=1)
				   Bukkit.broadcastMessage("§8[§c⌛§8] §7 Рестарт через: §c§l"+mins+" минут");
			   return true;
			}
			catch (NumberFormatException e)
			{
				return false;
			}
		}
		return false;
	}

}
