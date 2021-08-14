package ru.ardeon.additionalmechanics.mechanics.worldeffects;

import org.bukkit.entity.Player;
import ru.ardeon.additionalmechanics.mechanics.worldeffects.effects.WorldEffect;

import java.util.HashSet;

public class WorldEffectManager {
    private static final HashSet<WorldEffect> all = new HashSet<>();
    private static WorldEffectManager main;
    public static WorldEffectManager getMainManager(){
        if (main==null){
            main = new WorldEffectManager();
        }
        return main;
    }

    public WorldEffectManager(){

    }

    public void addEffect(WorldEffect effect){
        all.add(effect);
    }

    public void removeAll(){
        all.forEach(WorldEffect::remove);
    }

    public void removeAllOfPlayer(Player owner){
        all.forEach((effect)->{
            if (effect.getOwner()==owner){
                effect.remove();
            }
        });
    }

    public void removeEffect(WorldEffect effect){
        all.remove(effect);
    }

}
