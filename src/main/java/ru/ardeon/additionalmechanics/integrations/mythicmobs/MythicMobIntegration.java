package ru.ardeon.additionalmechanics.integrations.mythicmobs;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;


public class MythicMobIntegration {

    private MythicMobs instance;

    public MythicMobIntegration(){
        instance = MythicMobs.inst();
    }

    private void f(){
        //instance.getMobManager().getMythicMob("wolf2").spawn(BukkitAdapter.adapt())
    }
}
