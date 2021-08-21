package ru.ardeon.additionalmechanics.integrations.mobarena;


import com.garbagemule.MobArena.framework.Arena;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class MobArenaListener implements Listener {
    private static MobArenaListener instance;
    public static MobArenaListener getInstance(){
        if (instance==null)
            instance = new MobArenaListener();

        return instance;
    }
    private MobArenaListener(){

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPetDamaged(EntityDamageEvent e){
        Entity pet = e.getEntity();
        Arena arena = MobArenaIntegration.getInstance().getArenaAtLocation(pet.getLocation());
        if (arena==null)
            return;
        if (arena.getMonsterManager().hasPet(pet))
            e.setCancelled(false);
    }
}
