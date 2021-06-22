package ru.ardeon.additionalmechanics.vars.playerdata;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerAchievement {
    String player = "-";
    int value = 0;

    public String getPlayer() {
        return player;
    }

    public int getValue() {
        return value;
    }

    public PlayerAchievement(String player, int value) {
        this.player = player;
        this.value = value;
    }
}
