package ru.ardeon.additionalmechanics.util;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardVars {
    public static int getVar(String player, String objective){
        try{
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Objective obj = scoreboard.getObjective(objective);
            if (obj==null) {
                obj = scoreboard.registerNewObjective(objective, "dummy", objective);
            }
            return obj.getScore(player).getScore();
        }
        catch (IllegalArgumentException | IllegalStateException | NullPointerException e){
            return 0;
        }
    }

    public static void setVar(String player, String objective, int value){
        try{
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Objective obj = scoreboard.getObjective(objective);
            if (obj==null) {
                obj = scoreboard.registerNewObjective(objective, "dummy", objective);
            }
            obj.getScore(player).setScore(value);
        }
        catch (IllegalArgumentException | IllegalStateException | NullPointerException e){
            //
        }
    }
}
