package ru.ardeon.additionalmechanics.util.discord;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.logging.log4j.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.ardeon.additionalmechanics.AdditionalMechanics;


import java.sql.SQLException;

public class DiscordConfirmCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if (sender instanceof Player){
            BotListener listener = DiscordBot.getListener();
            String discordid = listener.getUnconfirmed().get((Player)sender);
            if (args.length>=1&&args[0].equalsIgnoreCase(discordid)) {
                AdditionalMechanics plugin = AdditionalMechanics.getPlugin();
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        listener.link((Player) sender, discordid);
                        AdditionalMechanics.getLoggerADM().log(Level.INFO, "Пользователь " + ((Player) sender).getName()
                                +" связал аккаунт с дискордом "+ discordid);
                        BaseComponent[] message = new ComponentBuilder("Вы связали аккаунт с дискордом")
                                .color(ChatColor.GOLD)
                                .create();
                        ((Player) sender).spigot().sendMessage(message);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                return true;
            }
        }
        return false;
    }
}
