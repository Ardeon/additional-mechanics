package ru.ardeon.additionalmechanics.skills.interact;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import com.garbagemule.MobArena.framework.Arena;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.integrations.mobarena.MobArenaIntegration;
import ru.ardeon.additionalmechanics.mechanics.worldeffects.effects.ArcLight;
import ru.ardeon.additionalmechanics.mechanics.worldeffects.effects.SnowWall;
import ru.ardeon.additionalmechanics.myEntity.Bait;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;
import ru.ardeon.additionalmechanics.vars.PlayerVarManager;
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

public class Combat {
	public static InteractSkill agro = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();

			Material material = SettingsLoaderUseableItems.SettingItems.AGRO_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material))) {
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.AGRO_COOLDOWN.getInt());
				AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(3);
				cloud.setColor(Color.RED);
				Predicate<Entity> entityPredicate = (entity) -> entity instanceof Mob;
				MobArenaIntegration mobArenaIntegration = AdditionalMechanics.getPlugin().getMobArenaIntegration();
				if (mobArenaIntegration!=null){
					Arena arena = mobArenaIntegration.getArenaAtLocation(player.getLocation());
					if (arena!=null) {
						entityPredicate = (entity) -> (entity instanceof Mob && !mobArenaIntegration.isPet(arena, entity));
					}
				}
				Collection<Entity> mobs = world.getNearbyEntities(player.getLocation(), 10, 3, 10, entityPredicate);
				int counter=0;
				for (Entity m: mobs) {
					Mob mob = (Mob) m;
					mob.setTarget(player);
					counter++;
					world.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
				}
				counter/=4;
				if (counter>4)
					counter = 4;
				PotionEffect ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, counter);
				ef.apply(player);
				world.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 2, 0.7f);
				world.playSound(player.getLocation(), Sound.BLOCK_NETHER_WART_BREAK, 2, 1);
				ItemStack item = e.getItem();
				item.setAmount(item.getAmount()-1);
			}
		}
	};

	public static InteractSkill explosion = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.EXPLOSION_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material))) {
				int power = SettingsLoaderUseableItems.SettingItems.EXPLOSION_POWER.getInt();
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.EXPLOSION_COOLDOWN.getInt());
				world.createExplosion(player.getLocation(), 2 + power, false, false, player);
				ItemStack item = e.getItem();
				item.setAmount(item.getAmount()-1);
			}
		}

	};
	public static InteractSkill fireballWithEffect = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material = SettingsLoaderUseableItems.SettingItems.FIREBALL_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				Class<Snowball> ball = Snowball.class;
				Snowball projectile = e.getPlayer().launchProjectile(ball);
				projectile.setGravity(false);
				ItemStack item = new ItemStack(Material.FIRE_CHARGE);
				projectile.setItem(item);
				projectile.setVisualFire(true);
				projectile.setShooter(e.getPlayer());
				projectile.addScoreboardTag("fireball");
				player.setCooldown(material, 40);
				world.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 2);
			}
		}

	};
	public static InteractSkill rage = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			if (!(player.hasCooldown(Material.BLACK_DYE)))
			{
				//7
				int power = PlayerVarManager.getInstance().getData(player).arenaData.getPower(2, 2);
				int cd = power;
				player.setCooldown(Material.BLACK_DYE, 600 - cd * 50);
				double newhealth = player.getHealth()-3;
				if (newhealth<1)
					newhealth=1;
				player.setHealth(newhealth);
				PotionEffect ef = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 150, 1 + power/2);
				ef.apply(player);
				ef = new PotionEffect(PotionEffectType.REGENERATION, 150 + power * 20, 2 + power/2);
				ef.apply(player);
				ef = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 150, 1);
				ef.apply(player);
				world.spawnParticle(Particle.SQUID_INK, player.getEyeLocation(), 10);
				world.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_HURT, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_ON_FIRE, 2, 1.2f);
				world.playSound(player.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 2, 1.2f);
				ItemStack item = e.getItem();
				item.setAmount(item.getAmount()-1);
			}
		}
	};
	public static InteractSkill scarecrow = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material;
			material = SettingsLoaderUseableItems.SettingItems.SCARECROW_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material))) {
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.SCARECROW_COOLDOWN.getInt());
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
				ItemStack item = e.getItem();
				item.setAmount(item.getAmount()-1);
			}
		}

		private void equipstand(ArmorStand la)
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
	public static InteractSkill soulAgro = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem();
			World world = player.getWorld();
			Material material;
			material = SettingsLoaderUseableItems.SettingItems.SOUL_AGRO_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.SOUL_AGRO_COOLDOWN.getInt());
				AreaEffectCloud cloud = (AreaEffectCloud) world.spawnEntity(player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
				cloud.setDuration(1);
				cloud.setRadius(5);
				cloud.setColor(Color.OLIVE);
				Predicate<Entity> entityPredicate = (entity) -> entity instanceof Mob;
				MobArenaIntegration mobArenaIntegration = AdditionalMechanics.getPlugin().getMobArenaIntegration();
				if (mobArenaIntegration!=null){
					Arena arena = mobArenaIntegration.getArenaAtLocation(player.getLocation());
					if (arena!=null) {
						entityPredicate = (entity) -> (entity instanceof Mob && !mobArenaIntegration.isPet(arena, entity));
					}
				}
				Collection<Entity> mobs = world.getNearbyEntities(player.getLocation(),10, 4, 10, entityPredicate);
				PotionEffect wea = new PotionEffect(PotionEffectType.WEAKNESS, 800, 0);
				for (Entity m: mobs)
				{
					Mob mob = (Mob) m;
					mob.setTarget(player);

					world.spawnParticle(Particle.VILLAGER_ANGRY, mob.getEyeLocation(), 2);
					wea.apply(mob);
				}
				world.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 2, 0.7f);
				item.setAmount(item.getAmount()-1);
			}
		}
	};
	public static InteractSkill snowball = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material;
			material = SettingsLoaderUseableItems.SettingItems.SNOWBALL_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				int cooldown = SettingsLoaderUseableItems.SettingItems.SNOWBALL_COOLDOWN.getInt();
				Class<Snowball> ball = Snowball.class;
				Snowball projectile = e.getPlayer().launchProjectile(ball);
				projectile.setShooter(e.getPlayer());
				projectile.addScoreboardTag("snowball");
				player.setCooldown(material, cooldown);
				world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
			}
		}
	};

	public static InteractSkill snowWall = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material;
			material = SettingsLoaderUseableItems.SettingItems.SNOW_WALL_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				new SnowWall(player);
				int cooldown = SettingsLoaderUseableItems.SettingItems.SNOW_WALL_COOLDOWN.getInt();
				player.setCooldown(material, cooldown);
				world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
			}
		}
	};

	public static InteractSkill arcLight = new InteractSkill() {
		@Override
		public void execute(PlayerInteractEvent e) {
			Player player = e.getPlayer();
			World world = player.getWorld();
			Material material;
			material = SettingsLoaderUseableItems.SettingItems.ARC_LIGHT_MATERIAL.getMaterial();
			if (!(player.hasCooldown(material)))
			{
				int cooldown = SettingsLoaderUseableItems.SettingItems.ARC_LIGHT_COOLDOWN.getInt();
				new ArcLight(player);
				player.setCooldown(material, cooldown);
				world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
				world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
			}
		}
	};
}
