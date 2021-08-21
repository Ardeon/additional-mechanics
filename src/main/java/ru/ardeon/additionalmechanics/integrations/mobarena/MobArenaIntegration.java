package ru.ardeon.additionalmechanics.integrations.mobarena;

import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.framework.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.integrations.mythicmobs.MythicMobIntegration;

public class MobArenaIntegration {
    private final MobArena mobArena;
    private static MobArenaIntegration instance;
    public static MobArenaIntegration getInstance(){
        if (instance==null)
            instance = new MobArenaIntegration();
        return instance;
    }

    private MobArenaIntegration() {
        AdditionalMechanics adm = AdditionalMechanics.getPlugin();
        mobArena = (MobArena) adm.getServer().getPluginManager().getPlugin("MobArena");
        if (mobArena == null || !mobArena.isEnabled()) {
            throw new NullPointerException("This extension requires core plugin MobArena installed and enabled");
        }
        adm.getServer().getPluginManager().registerEvents(MobArenaListener.getInstance(), adm);
    }

    public Arena getArenaAtLocation(Location location) {
        return mobArena.getArenaMaster().getArenaAtLocation(location);
    }

    /***
     * Создаёт моба и регистрирует его в качестве питомца
     * если игрок не на арене то ничего не произойдёт
     */
    public void addMythicMobPetToArena(Player owner, String mythicMobName){
        Arena arena = getArenaAtLocation(owner.getLocation());
        if (arena==null||!arena.isRunning())
            return;
        Entity pet = MythicMobIntegration.getInstance().spawnMob(mythicMobName, owner.getLocation()).getEntity().getBukkitEntity();
        arena.getMonsterManager().addPet(owner, pet);
    }

    public boolean isPet(Arena arena, Entity entity){
        if (arena!=null&&arena.isRunning()){
            return arena.getMonsterManager().hasPet(entity);
        }
        return false;
    }

    public void addPetToArena(Player owner, Entity pet, Arena arena){
        arena.getMonsterManager().addPet(owner, pet);
    }

}
