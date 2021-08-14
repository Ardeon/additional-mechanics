package ru.ardeon.additionalmechanics.integrations.mobarena;

import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.framework.Arena;
import org.bukkit.Location;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class MobArenaIntegration {
    private MobArena mobArena;

    public MobArenaIntegration() {
        mobArena = (MobArena) AdditionalMechanics.getPlugin().getServer().getPluginManager().getPlugin("MobArena");
        if (mobArena == null || !mobArena.isEnabled()) {
            throw new NullPointerException("This extension requires core plugin MobArena installed and enabled");
        }
    }

    public Arena getArenaAtLocation(Location location) {
        return mobArena.getArenaMaster().getArenaAtLocation(location);
    }
}
