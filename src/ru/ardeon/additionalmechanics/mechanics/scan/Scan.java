package ru.ardeon.additionalmechanics.mechanics.scan;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.ardeon.additionalmechanics.util.ConverterMaterial;
import ru.ardeon.additionalmechanics.util.Filter;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

public abstract class Scan {
	protected Block start;
	protected Block target;
	protected Filter filter;
	protected BossBar bar;
	protected Player p;
	protected Location partloc;
	protected int max, curr;
	protected List<Block> spots = new ArrayList<Block>();//+5
	protected String endMessage;
	protected String startMessage;
	protected String title;
	protected ConverterMaterial converter;
	protected BukkitRunnable timer = new BukkitRunnable() 
	{
		@Override
		public void run()
		{
			spotScan();
		}
	};
	
	public void spotScan() {
		for (int i =-2; i<=2; i++) {
			for (int j=-2; j<=2; j++) {
				for (int k =-2; k<=2; k++) {
					Block c = spots.get(curr).getRelative(i, j, k);//;
					if (!testBlock(c))
						continue;
					if (target!=null&&filter.test(c.getType())) {
						if (partloc.distance(target.getLocation())>partloc.distance(c.getLocation())) {
							target=c;
						}
					}
					else {
						if (filter.test(c.getType()))
							target=c;
					}
				}
			}
		}
		curr++;
		bar.setProgress((double)curr/max);
		if (curr>=max) {
			bar.removeAll();
			RayTo();
			p.sendRawMessage(endMessage);
			timer.cancel();
		}
	}
	
	public abstract boolean testBlock(Block c);
	
	public Scan(Player p, Block start, Filter filter, int power, ConverterMaterial converter, String endMessage, String startMessage, String title) {
		this.p = p;
		this.start = start;
		this.filter = filter;
		this.endMessage = endMessage;
		this.startMessage = startMessage;
		this.title = title;
		this.converter = converter;
		int r=0;
		int h=0;
		if (power==1)
			r=2;
		if (power==2) {
			r=3;
			h=1;
		}
		if (power>=3) {
			r=4;
			h=2;
		}

		bar = Bukkit.createBossBar(title, BarColor.BLUE, BarStyle.SEGMENTED_20);
		bar.setProgress(0);
		bar.addPlayer(p);
		bar.setVisible(true);
		partloc = p.getEyeLocation().clone();
		for (int i = -5*r; i<=5*r; i+=5) {
			for (int j = -5*r; j<=5*r; j+=5) {
				for (int k = -5*h; k<=5*h; k+=5) {
					spots.add(start.getRelative(i, k, j));
				}
			}
		}
		max = spots.size();
		curr=0;
		p.sendRawMessage(startMessage);
		timer.runTaskTimer(AdditionalMechanics.getPlugin(), 2L, 1L);
		
	}
	
	void RayTo(){
		if (target!=null) {
			p.sendRawMessage("§5Цель - "+converter.NameOf(target.getType()));
			p.sendRawMessage("§5Получение пути");
			BukkitRunnable ray = new BukkitRunnable() 
			{
				int count=0;
				
				@Override
				public void run()
				{
					p.spawnParticle(Particle.FLAME, partloc, 5, 0.01, 0.01, 0.01, 0.01);
					if (partloc.distance(target.getLocation().clone().add(0.5,0.5,0.5))>0.2&&count<400) {
						partloc.add(target.getLocation().clone().add(0.5,0.5,0.5).subtract(partloc.clone()).toVector().normalize().multiply(0.15));
					}
					else {
						this.cancel();
					}
					count++;
				}
			};
			ray.runTaskTimer(AdditionalMechanics.getPlugin(), 2L, 6L);
		}
		else {
			p.sendRawMessage("§5Ненайдено");
		}
		
	}
	
}

