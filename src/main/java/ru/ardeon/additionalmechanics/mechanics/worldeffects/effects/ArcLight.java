package ru.ardeon.additionalmechanics.mechanics.worldeffects.effects;

import com.garbagemule.MobArena.framework.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.integrations.mobarena.MobArenaIntegration;
import ru.ardeon.additionalmechanics.mechanics.worldeffects.WorldEffectManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class ArcLight implements WorldEffect{
    private final int MAX_BOUNCE = SettingsLoaderUseableItems.SettingItems.ARC_LIGHT_BOUNCE.getInt();
    private final double LENGTH = SettingsLoaderUseableItems.SettingItems.ARC_LIGHT_LENGTH.getDouble();
    private final double RADIUS = SettingsLoaderUseableItems.SettingItems.ARC_LIGHT_RADIUS.getDouble();
    private final double DAMAGE = SettingsLoaderUseableItems.SettingItems.ARC_LIGHT_DAMAGE.getInt();
    private int bounce = 1;
    private final BukkitTask task;
    private final Set<LivingEntity> affected = new HashSet<>();
    private Player owner = null;
    private Location lastLocation = null;
    private Arena arena;
    private MobArenaIntegration mobArenaIntegration = null;

    public ArcLight(Player player){
        owner = player;
        Predicate<Entity> entityPredicate = (entity) -> (entity instanceof LivingEntity && !(entity instanceof Player));
        mobArenaIntegration = AdditionalMechanics.getPlugin().getMobArenaIntegration();
        if (mobArenaIntegration!=null){
            arena = mobArenaIntegration.getArenaAtLocation(player.getLocation());
            if (arena!=null) {
                entityPredicate = (entity) -> (entity instanceof LivingEntity
                        && !(entity instanceof Player)
                        && !mobArenaIntegration.isPet(arena, entity));
            }
        }
        RayTraceResult rayTraceResult = owner.getWorld().rayTraceEntities(owner.getEyeLocation(),
                owner.getEyeLocation().getDirection(),
                LENGTH,
                entityPredicate);
        if (rayTraceResult!=null){
            LivingEntity livingEntity = (LivingEntity) rayTraceResult.getHitEntity();
            affect(livingEntity);
        } else {
            rayTraceResult = owner.getWorld().rayTraceBlocks(owner.getEyeLocation(),
                    owner.getEyeLocation().getDirection(),
                    LENGTH);
            if (rayTraceResult!=null){
                Location location = rayTraceResult.getHitPosition().toLocation(owner.getWorld());
                drawLine(location);
                lastLocation = location;
            } else {
                Location location = owner.getEyeLocation().add(owner.getEyeLocation().getDirection().multiply(LENGTH));
                drawLine(location);
                lastLocation = location;
            }
        }
        task = Bukkit.getServer().getScheduler().runTaskTimer(AdditionalMechanics.getPlugin(),this::tick,6,6);
        WorldEffectManager.getMainManager().addEffect(this);
    }

    private void drawLine(Location locationTo){
        if (lastLocation == null){
            if (owner==null)
                return;
            lastLocation = owner.getEyeLocation();
        }
        World world = lastLocation.getWorld();
        Vector vectorFrom = lastLocation.toVector();
        Vector vectorTo = locationTo.toVector();
        Vector direction = vectorTo.clone().subtract(vectorFrom);
        direction.normalize();
        double distance = vectorTo.distance(vectorFrom);
        for (int i = 0; i < 20; i++){
            Location locationOfParticle = lastLocation.clone().add(direction.clone().multiply(i*distance/20));
            world.spawnParticle(Particle.SPELL_WITCH, locationOfParticle, 1, 0,0,0,0);
        }
        world.spawnParticle(Particle.SPELL_WITCH, locationTo, 10, 0.3, 0.3, 0.3,0);
        //
    }

    private void drawLine(LivingEntity targetEntity){
        Location locationTo = targetEntity.getEyeLocation();
        drawLine(locationTo);
    }

    private void affect(LivingEntity livingEntity){
        livingEntity.damage(DAMAGE, owner);
        if (livingEntity instanceof Creeper){
            Creeper creeper = (Creeper) livingEntity;
            if (!creeper.isPowered()){
                creeper.setPowered(true);
            } else {
                creeper.ignite();
            }
        }
        drawLine(livingEntity);
        lastLocation = livingEntity.getEyeLocation();
        affected.add(livingEntity);
    }

    private void tick(){
        if (bounce < MAX_BOUNCE) {
            bounce++;
            Predicate<Entity> entityPredicate = (entity) -> !(entity instanceof Player)
                    &&(entity instanceof LivingEntity)
                    &&!(affected.contains(entity));
            if (mobArenaIntegration!=null && arena!=null){
                entityPredicate = (entity) -> !(entity instanceof Player)
                        && (entity instanceof LivingEntity)
                        && !(affected.contains(entity))
                        && !mobArenaIntegration.isPet(arena, entity);
            }
            Collection<Entity> entities = lastLocation.getWorld().getNearbyEntities(lastLocation,
                    RADIUS, RADIUS, RADIUS, entityPredicate);
            Optional<Entity> optional = entities.stream().findAny();
            if (optional.isPresent()){
                LivingEntity target = (LivingEntity) optional.get();
                affect(target);
            }

        } else {
            remove();
        }
    }

    @Override
    public void remove() {
        if (task!=null&&!task.isCancelled()){
            task.cancel();
        }
        WorldEffectManager.getMainManager().removeEffect(this);
    }

    @Override
    public Player getOwner() {
        return owner;
    }
}
