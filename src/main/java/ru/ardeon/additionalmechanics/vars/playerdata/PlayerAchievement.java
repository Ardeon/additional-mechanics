package ru.ardeon.additionalmechanics.vars.playerdata;

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
