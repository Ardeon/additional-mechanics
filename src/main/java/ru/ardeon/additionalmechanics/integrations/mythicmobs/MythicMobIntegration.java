package ru.ardeon.additionalmechanics.integrations.mythicmobs;

import com.garbagemule.MobArena.framework.Arena;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import ru.ardeon.additionalmechanics.AdditionalMechanics;


public class MythicMobIntegration {

    private final MythicMobs MythicMobsInstance;

    private static MythicMobIntegration instance;
    public static MythicMobIntegration getInstance() {
        if (instance==null && AdditionalMechanics.getPlugin().getServer().getPluginManager().isPluginEnabled("MythicMobs")){
            instance = new MythicMobIntegration();
        } else {
            AdditionalMechanics.getLoggerADM().warn("плагин MythicMobs не найден");
        }

        return instance;
    }


    private MythicMobIntegration(){
        MythicMobsInstance = MythicMobs.inst();
    }

    public ActiveMob spawnMob(String name, Location location){
        return MythicMobsInstance.getMobManager().getMythicMob(name).spawn(BukkitAdapter.adapt(location), 1);
    }

    private void f(Location location){
        ActiveMob a = MythicMobsInstance.getMobManager().getMythicMob("wolf2").spawn(BukkitAdapter.adapt(location), 1);
        Entity e = a.getEntity().getBukkitEntity();
        //instance.getMobManager().getMythicMob("wolf2").spawn(BukkitAdapter.adapt());
    }
}
