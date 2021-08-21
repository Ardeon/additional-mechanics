package ru.ardeon.additionalmechanics.mechanics.worldeffects.effects;

import com.garbagemule.MobArena.framework.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.integrations.mobarena.MobArenaIntegration;
import ru.ardeon.additionalmechanics.mechanics.worldeffects.WorldEffectManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

public class SnowWall implements WorldEffect{

    private Arena arena;
    private final MobArenaIntegration mobArenaIntegration;
    private final BukkitTask task;
    private final Player player;
    private final World world;
    private final BoundingBox[] box = new BoundingBox[5];
    private int lifeTime = 0;
    private final PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 30, 4);
    private Predicate<Entity> entityPredicate;

    public SnowWall(Player player) {
        task = Bukkit.getServer().getScheduler().runTaskTimer(AdditionalMechanics.getPlugin(),this::tick,0,6);
        this.player = player;
        world = player.getWorld();
        entityPredicate = (entity) -> (entity instanceof LivingEntity && !(entity instanceof Player));
        mobArenaIntegration = AdditionalMechanics.getPlugin().getMobArenaIntegration();
        if (mobArenaIntegration!=null){
            arena = mobArenaIntegration.getArenaAtLocation(player.getLocation());
            if (arena!=null) {
                entityPredicate = (entity) -> (entity instanceof LivingEntity
                        && !(entity instanceof Player)
                        && !mobArenaIntegration.isPet(arena, entity));
            }
        }
        float yaw = player.getEyeLocation().getYaw();
        Location location = player.getEyeLocation().clone();
        location.setYaw(yaw);
        location.setPitch(0);
        Vector direction = location.getDirection().multiply(2);
        location.add(direction); //Место перед игроком на расстоянии 1 блока на той же высоте, что и игрок
        Vector directionToSide = direction.clone();
        directionToSide.normalize();
        double x = directionToSide.getX();
        directionToSide.setX(-direction.getZ());
        directionToSide.setZ(x);//эти действия развернут вектор на 90 градусов
        setCollision(location, directionToSide);
        WorldEffectManager.getMainManager().addEffect(this);
    }

    private void setCollision(Location location, Vector direction){
        for(int i = 0; i<5; i++) {
            Location locationOfBox = location.clone().add(direction.clone().normalize().multiply(2-i));
            double x1, x2, y1, y2, z1, z2;
            x1 = locationOfBox.getX()-0.5;
            x2 = locationOfBox.getX()+0.5;
            y1 = locationOfBox.getY()-1.5;
            y2 = locationOfBox.getY()+1.5;
            z1 = locationOfBox.getZ()-0.5;
            z2 = locationOfBox.getZ()+0.5;
            box[i] = new BoundingBox(x1,y1,z1,x2,y2,z2);
        }
    }

    private void tick(){
        lifeTime++;
        if (lifeTime>40){
            remove();
        }
        Collection<Entity> overlapping = new HashSet<>();
        for (BoundingBox boundingBox : box){
            overlapping.addAll(world.getNearbyEntities(boundingBox, entityPredicate));
            world.spawnParticle(Particle.SNOW_SHOVEL, boundingBox.getCenter().toLocation(world), 20, 0.3, 0.8,0.3);
        }
        if (player!=null){
            for(Entity entity : overlapping){
                potionEffect.apply((LivingEntity)entity);
                ((LivingEntity)entity).damage(0.1, player);

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
        return player;
    }
}
