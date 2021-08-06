package ru.ardeon.additionalmechanics.util.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.logging.log4j.Level;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;

public class DiscordBot {
    private JDA jda = null;
    private static BotListener listener = null;
    public static BotListener getListener() { return listener; }

    public DiscordBot() throws SQLException, IOException {
        listener = new BotListener(AdditionalMechanics.getPlugin());
        startBot();
    }

    private void startBot() {
        AdditionalMechanics plugin = AdditionalMechanics.getPlugin();
        if (plugin.getConfig().getString("botInfo.token")==null)
            return;
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                plugin.getLogger().info("Initializing bot");
                AdditionalMechanics.getLoggerADM().log(Level.INFO, "Initializing bot");
                JDABuilder builder = JDABuilder
                        .create(plugin.getConfig().getString("botInfo.token"),
                                GatewayIntent.GUILD_MEMBERS,
                                GatewayIntent.GUILD_MESSAGES,
                                GatewayIntent.DIRECT_MESSAGES,
                                GatewayIntent.GUILD_BANS)
                        .disableCache(
                                CacheFlag.EMOTE,
                                CacheFlag.VOICE_STATE,
                                CacheFlag.ACTIVITY,
                                CacheFlag.CLIENT_STATUS
                        );

                builder.addEventListeners(listener);
                this.jda = builder.build();

            } catch (LoginException e) {
                plugin.getLogger().log(java.util.logging.Level.WARNING, "error", e);
            }
        });
    }
}
