package ru.ardeon.additionalmechanics.skills;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.ardeon.additionalmechanics.myEntity.Bait;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;

public class Combat {
	public static ItemSkill agro = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.RED_DYE))) 		
			{
				//power 5 cd 7
				int power = PlayerVarManager.getInstance().getData(player).arenaData.getPower(4, 1);
				int cd = (int) (power * 1.5);
				player.setCooldown(Material.RED_DYE, 1200 - cd * 60);
				AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(2+power);
				cloud.setColor(Color.RED);
				List<Entity> mobs = player.getNearbyEntities(10, 2 + power, 10);
				int counter=0;
				for (Entity m: mobs)
				{
					if (m instanceof Mob)
					{
						Mob mob = (Mob) m;
						mob.setTarget(player);
						counter++;
						world.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
					}
				}
				counter/=4;
				if (counter>4)
					counter = 4;
				PotionEffect ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300 + power * 50, counter);
				ef.apply(player);
				world.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 2, 0.7f);
				world.playSound(player.getLocation(), Sound.BLOCK_NETHER_WART_BREAK, 2, 1);
			}
		}
	};
	public static ItemSkill damagingSnowball = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.STICK))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(5, 1);
				Class<Snowball> ball = Snowball.class;
				Snowball r = e.getPlayer().launchProjectile(ball);
				r.setShooter(e.getPlayer());
				r.addScoreboardTag("snowball");
				player.setCooldown(Material.STICK, 40 - cd * 3);
				world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
			}
		}
		
	};
	public static ItemSkill explosion = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.FIREWORK_STAR))) 
			{
				//power 3 cd 7
				int power = PlayerVarManager.getInstance().getData(player).arenaData.getPower(4, 2);
				int cd = power * 2;
				player.setCooldown(Material.FIREWORK_STAR, 800 - cd * 40);
				world.createExplosion(player.getLocation(), 2 + power, false, false, player);
			}
		}
		
	};
	public static ItemSkill fireballWithEffect = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.QUARTZ))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(6, 1);
				Class<SmallFireball> ball = SmallFireball.class;
				SmallFireball r = e.getPlayer().launchProjectile(ball);
				r.setShooter(e.getPlayer());
				r.addScoreboardTag("fireball");
				player.setCooldown(Material.QUARTZ, 40 - cd * 3);
				world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 2);
			}
		}
		
	};
	public static ItemSkill rage = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.BLACK_DYE))) 
			{
				int power = PlayerVarManager.getInstance().getData(player).arenaData.getPower(2, 2);
				int cd = power/3;
				player.setCooldown(Material.BLACK_DYE, 600 - cd * 50);
				double newhealth = player.getHealth()-3;
				if (newhealth<1)
					newhealth=1;
				player.setHealth(newhealth);
				PotionEffect ef = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 150, 1 + power);
				ef.apply(player);
				ef = new PotionEffect(PotionEffectType.REGENERATION, 150 + power * 20, 2 + power/3);
				ef.apply(player);
				ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 150, 1);
				ef.apply(player);
				world.spawnParticle(Particle.SQUID_INK, player.getEyeLocation(), 10);
				world.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_HURT, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_ON_FIRE, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 2, 1.2f);
			}
		}
	};
	public static ItemSkill scarecrow = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.PLAYER_HEAD))) 
			{
				//cd 7
				int cd = PlayerVarManager.getInstance().getData(player).arenaData.getPower(3, 2);
				player.setCooldown(Material.PLAYER_HEAD, 1600 - cd * 120);
				world.playSound(player.getLocation(), Sound.ENTITY_EVOKER_AMBIENT, 2, 1);
				world.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 2, 1.2f);
				Entity a = world.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
				a.setInvulnerable(true);
				ArmorStand la = (ArmorStand) a;
				la.addScoreboardTag("untouchable");
				equipstand(la);
				Bait b = new Bait(player,(ArmorStand) a);
				b.agrostart();
				e.setCancelled(true);
			}
		}
		
		public void equipstand(ArmorStand la) 
		{
			EntityEquipment eq = la.getEquipment();
			ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
			ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1);
			eq.setBoots(boots);
			eq.setLeggings(leggings);
			eq.setChestplate(chestplate);
			eq.setHelmet(helmet);
		}
	};
	public static ItemSkill soulAgro = new ItemSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.BROWN_DYE))) 		
			{
				//for delete
				player.setCooldown(Material.BROWN_DYE, 400);
				AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(5);
				cloud.setColor(Color.OLIVE);
				List<Entity> mobs = player.getNearbyEntities(10, 4, 10);
				PotionEffect wea = new PotionEffect(PotionEffectType.WEAKNESS, 800, 0);
				for (Entity m: mobs)
				{
					if (m instanceof Mob)
					{
						Mob mob = (Mob) m;
						mob.setTarget(player);

						world.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
						wea.apply(mob);
					}
				}
				world.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 2, 0.7f);
				item.setAmount(item.getAmount()-1);
			}
		}
	};
}
