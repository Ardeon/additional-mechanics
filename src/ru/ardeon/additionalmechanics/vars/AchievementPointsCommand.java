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
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    Objective obj = scoreboard.getObjective("bac_advancements");
                    Set<String> ent = scoreboard.getEntries();
                    ArrayList<PlayerAchievement> playerAchievementTop = new ArrayList<>();
                    for(String s : ent) {
                        playerAchievementTop.add(new PlayerAchievement(s,obj.getScore(s).getScore()));
                        AdditionalMechanics.getPlugin().getLogger().info(""+obj.getScore(s).getScore());
                    }
                    PlayerVarManager.getInstance().setPlayerAchievementTop(playerAchievementTop);
                }
                case "get":{
                    ArrayList<PlayerAchievement> playerAchievementTop = PlayerVarManager.getInstance().getPlayerAchievementTop();
                    for (PlayerAchievement pa : playerAchievementTop) {
                        AdditionalMechanics.getPlugin().getLogger().info(""+ pa.getValue() +" ---- "+pa.getPlayer());
                    }
                    return true;
                }
            }

        }
        return false;
    }
}
