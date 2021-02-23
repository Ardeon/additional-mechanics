package ru.ardeon.additionalmechanics.mechanics.moon;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.message.AdmMessage;

public class MoonManager {
	public boolean fullMoon = false;
	boolean prefullMoon = false;
	boolean roll = false;
	
	World world;
	AdditionalMechanics plugin;
	BossBar bossbar = Bukkit.createBossBar("§fНезеритовая луна", BarColor.RED, BarStyle.SOLID);
	BukkitRunnable checkTimer = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			long fulltime = world.getFullTime();
			int days = (int) fulltime/24000;
			int phase = days%8;
			int time = (int) fulltime%24000;
			if (fullMoon) {
				for(Player player: Bukkit.getOnlinePlayers()) {
					bossbar.addPlayer(player);
				}
				bossbar.setVisible(true);
			}
			else {
				bossbar.setVisible(false);
			}
			if (phase==0) {
				if (!fullMoon) {
					if (time > 11000 && !roll) {
						if(Math.random() > 0.25) {
							AdmMessage.getInstance().broadcastEvent("§7Луна вот-вот обретёт свою полную форму. Этой ночью нежить прорвётся наружу."
									+ " Основательно подготовьтесь к её приходу или найдите надёжное укрытие!");
							roll = true;
						}
						else {
							world.setFullTime(fulltime + 24000);
							AdmMessage.getInstance().broadcastEvent("§7Незеритовая луна не появится этой ночью.");
						}
						
						
					}
					else if (time > 12000) {
						AdmMessage.getInstance().broadcastEvent("§7Незеритовая Луна начала оказывать влияние на окружающий мир."
								+ " Монстры стали гораздо сильнее и обрели магически способностию Как можно скорее найдите укрытие!");
						fullMoon = true;
					}
					
				}
				if (!prefullMoon) {
					prefullMoon = true;
					AdmMessage.getInstance().broadcastEvent("§7Этой ночью луна может обрести свою полную форму. §8Незеритовая луна появится с шансом - 75%.");
				}
			}
			else {
				if (fullMoon) {
					AdmMessage.getInstance().broadcastEvent("§7Незеритовая Луна ушла за горизонт. Монстры потеряли свои силы. Можно покидать убежище!");
				}
				roll = false;
				prefullMoon = false;
				fullMoon = false;
			}
		}
	};
	
	public MoonManager(World w, AdditionalMechanics p){
		this.world = w;
		this.plugin = p;
		checkTimer.runTaskTimer(plugin, 150, 15);
		plugin.getServer().getPluginManager().registerEvents(new MoonListener(this), plugin);
	}
}
