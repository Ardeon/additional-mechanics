package ru.ardeon.additionalmechanics.vars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerAchievement;

import java.util.ArrayList;
import java.util.Set;

public class AchievementPointsCommand implements CommandExecutor {
    PlayerVarManager playerVarManager;
    public AchievementPointsCommand(PlayerVarManager playerVarManager) {
        this.playerVarManager = playerVarManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String arg, @NotNull String[] args) {
        if (args.length>=1 && commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String uuid = player.getUniqueId().toString().toLowerCase();
            switch (args[0]){
                case "refresh":{

                    PlayerVarManager.getInstance().refreshTop();
                }
                case "get":{
                    ArrayList<PlayerAchievement> playerAchievementTop = PlayerVarManager.getInstance().getPlayerAchievementTop();
                    for (PlayerAchievement pa : playerAchievementTop) {
                        int score = pa.getValue();
                        if (score==0)
                            continue;
                        AdditionalMechanics.getPlugin().getLogger().info(""+ pa.getValue() +" ---- "+pa.getPlayer());
                    }
                    return true;
                }
            }

        }
        return false;
    }
}
