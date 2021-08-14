package ru.ardeon.additionalmechanics.mechanics.worldeffects.effects;

import org.bukkit.entity.Player;

public interface WorldEffect {
    /**
     * Удаляет эффект
     * */
    public void remove();
    /**
     * Получает игрока создавшего этот эффект
     * @return создателя или null если создан не игроком
     * */
    public Player getOwner();
}
