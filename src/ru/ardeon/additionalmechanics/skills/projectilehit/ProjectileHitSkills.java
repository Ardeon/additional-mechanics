package ru.ardeon.additionalmechanics.skills.projectilehit;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import ru.ardeon.additionalmechanics.skills.template.ProjectileHitSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class ProjectileHitSkills {
	static public ProjectileHitSkill explosiveArrow = new ProjectileHitSkill() {
		@Override
		public void execute(ProjectileHitEvent e) {
			Projectile projectile = e.getEntity();
			ProjectileSource shooter = projectile.getShooter();
			World w = projectile.getWorld();
			w.createExplosion(projectile.getLocation(), 3, false, false , (Entity) shooter);
			projectile.remove();
		}
	};
	static public ProjectileHitSkill fireball = new ProjectileHitSkill() {
		@Override
		public void execute(ProjectileHitEvent e) {
			Projectile projectile = e.getEntity();
			ProjectileSource shooter = projectile.getShooter();
			int power = 0;
			if (shooter instanceof Player) {
				Player player = (Player) shooter;
				power = PlayerVarManager.getInstance().getData(player).arenaData.getPower(6, 1) / 2;
			}
			
			World w = projectile.getWorld();
			PotionEffect ef = new PotionEffect(PotionEffectType.WITHER, 60 + power * 8, 2 + power/2);
			for (Entity t: projectile.getNearbyEntities(3 + power, 3 + power, 3 + power)) {
				if (t instanceof Mob) {
					Mob nt = (Mob) t;
					ef.apply(nt);
					nt.damage(1, (Entity) shooter);
					w.spawnParticle(Particle.SMOKE_LARGE, nt.getEyeLocation(), 7);
				}
			}
			projectile.remove();
		}
	};
	static public ProjectileHitSkill snowball = new ProjectileHitSkill() {
		@Override
		public void execute(ProjectileHitEvent e) {
			Projectile projectile = e.getEntity();
			ProjectileSource shooter = projectile.getShooter();
			World w = projectile.getWorld();
			PotionEffect ef = new PotionEffect(PotionEffectType.SLOW, 120, 2);
			w.playSound(projectile.getLocation(), Sound.ENTITY_SNOW_GOLEM_HURT, 1, 2);
			w.spawnParticle(Particle.SNOW_SHOVEL, projectile.getLocation(), 7);
			AreaEffectCloud cloud = (AreaEffectCloud) e.getEntity().getWorld().spawnEntity(projectile.getLocation(), EntityType.AREA_EFFECT_CLOUD);
			cloud.setDuration(1);
			cloud.setRadius(3);
			cloud.setColor(Color.WHITE);
			for (Entity t: projectile.getNearbyEntities(3, 3, 3)) {
				if (t instanceof LivingEntity && !(t instanceof Player)) {
					LivingEntity nt = (LivingEntity) t;
					ef.apply(nt);
					nt.damage(4, (Entity) shooter);
					w.spawnParticle(Particle.SNOW_SHOVEL, nt.getEyeLocation(), 7);
				}
			}
			
		}
	};
	static public ProjectileHitSkill hook = new ProjectileHitSkill() {
		@Override
		public void execute(ProjectileHitEvent e) {
			Projectile projectile = e.getEntity();
			World w = projectile.getWorld();
			Entity entity = e.getHitEntity();
			if (entity != null && entity instanceof LivingEntity)
			{
				LivingEntity target = (LivingEntity) entity;
				PotionEffect ef = new PotionEffect(PotionEffectType.WEAKNESS, 120, 3);
				if(!(target instanceof Player)) 
				{
					ef.apply(target);
					w.spawnParticle(Particle.WATER_SPLASH, target.getEyeLocation(), 20);
					Vector v  = projectile.getVelocity().multiply(-2.0d);
					target.setVelocity(v);
					w.playSound(target.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 2, 2);
				}
			}
		}
	};
}
