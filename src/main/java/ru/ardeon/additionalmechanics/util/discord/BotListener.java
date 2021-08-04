package ru.ardeon.additionalmechanics.util.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.types.PermissionNode;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.logging.log4j.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class BotListener extends ListenerAdapter {
    private final JavaPlugin plugin;
    private DiscordCommand discordCommand = null;
    private DiscordDatabase db = null;
    private JDA bot = null;
    private final Map<Player, String> unconfirmed = new HashMap<>();
    private final Map<String, String> rolesToPermissions = new HashMap<>();
    private final Map<String, String> groupToRoles = new HashMap<>();
    public Map<Player, String> getUnconfirmed(){ return unconfirmed; }

    public BotListener(JavaPlugin plugin) throws SQLException, IOException {
        super();
        ConfigurationSection roleToPerm = plugin.getConfig().getConfigurationSection("roleToPerm");
        if (roleToPerm!=null){
            for (String name : roleToPerm.getKeys(false)){
                ConfigurationSection section = roleToPerm.getConfigurationSection(name);
                if (section != null) {
                    String role = section.getString("role");
                    String permission = section.getString("permission");
                    if (role!=null&permission!=null) {
                        rolesToPermissions.put(role, permission);
                    }
                }
            }
        }
        ConfigurationSection permToRole = plugin.getConfig().getConfigurationSection("groupToRole");
        if (roleToPerm!=null){
            for (String name : permToRole.getKeys(false)){
                ConfigurationSection section = permToRole.getConfigurationSection(name);
                if (section != null) {
                    String role = section.getString("role");
                    String group = section.getString("group");
                    if (role!=null&group!=null) {
                        groupToRoles.put(group, role);
                    }
                }
            }
        }

        this.plugin = plugin;
        this.db = new SQLiteDiscordDatabase(AdditionalMechanics.getPlugin());
        plugin.getLogger().info("Finished initializing bot.");
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            refresh();
        }, 1200, 2400);
    }

    public void link(Player player, String discordId) throws SQLException {
        unconfirmed.remove(player);
        db.linkUser(discordId, player.getUniqueId().toString());
    }

    public void refresh(){
        try {
            db.forAllLinkedUsers((userInfo) ->
                    Objects.requireNonNull(bot.getGuildById(this.plugin.getConfig().getString("botInfo.server", "867093500713107457")))
                            .retrieveMemberById(userInfo.discordId).queue(member -> {
                        if (member != null) {
                            Set<String> rolesSet = rolesToPermissions.keySet();
                            List<String> permissionToAdd = new ArrayList<>();
                            for (Role role : member.getRoles()){
                                for(String testedRole : rolesSet)
                                    if (role.getId().equalsIgnoreCase(testedRole)){
                                        permissionToAdd.add(rolesToPermissions.get(testedRole));
                                    }
                            }

                            String uuid = userInfo.uuid;
                            UserManager userManager = AdditionalMechanics.getPlugin().getLP().getUserManager();
                            CompletableFuture<User> userFuture = userManager.loadUser(UUID.fromString(uuid));
                            userFuture.thenAcceptAsync(user -> {
                                for(String permission : permissionToAdd){
                                    PermissionNode node = PermissionNode.builder(permission).build();
                                    user.data().add(node);
                                }
                                Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
                                for(Group group : inheritedGroups){
                                    for(String groupTest : groupToRoles.keySet()){
                                        if (group.getName().equalsIgnoreCase(groupTest)) {
                                            String roleId = groupToRoles.get(groupTest);
                                            if (roleId!=null){
                                                Role role = member.getGuild().getRoleById(roleId);
                                                if (role!=null){
                                                    member.getGuild().addRoleToMember(member, role).queue(null,
                                                            error -> plugin.getLogger().warning("Error while adding role: " + error.getMessage()));
                                                }
                                            }
                                        }
                                    }
                                }
                                AdditionalMechanics.getPlugin().getLP().getUserManager().saveUser(user);
                            });

                            member.modifyNickname(Objects
                                    .requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(uuid))).getName()).queue(null, error -> { });;
                            AdditionalMechanics.getLoggerADM().log(Level.INFO, "Пользователь ");
                        }
                    }, error -> { }));
        } catch (SQLException e) {
            plugin.getLogger().severe("An error occurred while checking all users. " +
                    "Please check the stack trace below and contact the developer.");
            e.printStackTrace();
        } catch (PermissionException e) {
            plugin.getLogger().warning("Бот не имеет прав изменять данные этого пользователя");
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        this.bot = event.getJDA();
        this.discordCommand = new DiscordCommand();
        plugin.getLogger().info("Logged in: " + event.getJDA().getSelfUser().getName());
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return; // ignore bots

        String message = event.getMessage().getContentRaw();

        String prefix = this.plugin.getConfig().getString("botInfo.prefix","-");
        if (message.length() < prefix.length() || !message.startsWith(prefix)) return; // ignore if no prefix

        if (!plugin.getConfig().getStringList("botInfo.channelsToListen").contains(event.getChannel().getId())
                && !JDAUtils.hasRoleFromList(event.getMember(), plugin.getConfig().getStringList("adminCommandRoles"))) {
            return; // ignore
        }

        String[] argv = message.split(" ");
        argv[0] = argv[0].substring(prefix.length()); // remove prefix

        if (argv[0].equalsIgnoreCase("link")) {
            discordCommand.link(argv, event);
        }

    }

    class DiscordCommand {

        void link(String[] argv, MessageReceivedEvent event) {
            if (argv.length < 2) {
                JDAUtils.reactAndDelete(AdditionalMechanics.getPlugin().getConfig().getString("react.onUserError", "U+274C"), event.getMessage(), AdditionalMechanics.getPlugin().getConfig());
                return;
            }

            try {
                this.linkUser(event.getAuthor().getId(), argv[1], event);
            } catch (SQLException | IOException e) {
                JDAUtils.reactAndDelete(AdditionalMechanics.getPlugin().getConfig().getString("react.onBotError", "U+26A0"), event.getMessage(), AdditionalMechanics.getPlugin().getConfig());
                AdditionalMechanics.getPlugin().getLogger().severe("An error occurred while trying to check link the user. " +
                        "Please check the stack trace below and contact the developer.");
                e.printStackTrace();
            }
        }

        private void linkUser(String discordId, String mcUsername, MessageReceivedEvent event) throws IOException, SQLException {
            DiscordDatabase.LinkedUser userInfo = db.getLinkedUser(discordId);
            if (userInfo != null) {
                JDAUtils.reactAndDelete(AdditionalMechanics.getPlugin().getConfig().getString("react.onUserError"), event.getMessage(), AdditionalMechanics.getPlugin().getConfig());
                event.getAuthor().openPrivateChannel().queue(
                        channel -> channel.sendMessage("Дискорд аккаунт уже привязан")
                                .queue(null, err -> {
                                }));

                return;
            }

            Player player = Bukkit.getPlayer(mcUsername);

            String uuid = null;
            if (player!=null)
                uuid = player.getUniqueId().toString();
            if (uuid == null) {
                JDAUtils.reactAndDelete(AdditionalMechanics.getPlugin().getConfig().getString("react.onUserError"), event.getMessage(), AdditionalMechanics.getPlugin().getConfig());

                return;
            }

            userInfo = db.getLinkedUser(uuid);
            if (userInfo != null) {
                JDAUtils.reactAndDelete(AdditionalMechanics.getPlugin().getConfig().getString("react.onUserError"), event.getMessage(), AdditionalMechanics.getPlugin().getConfig());
                event.getAuthor().openPrivateChannel().queue(
                        channel -> channel.sendMessage("Майнкрафт аккаунт уже привязан")
                                .queue(null, err -> {
                                }));

                return;
            }

            if (!unconfirmed.containsKey(player)) {
                unconfirmed.put(player, discordId);
                BaseComponent[] message = new ComponentBuilder("Связать с дискордом "
                        + event.getAuthor().getName()).color(ChatColor.BLUE)
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/discordconfirm "+discordId) ).create();
                player.spigot().sendMessage(message);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    if (unconfirmed.containsKey(player))
                        unconfirmed.remove(player);
                }, 1200);
                event.getAuthor().openPrivateChannel().queue(
                        channel -> channel.sendMessage("Подтвердите действие в майнкрафте")
                                .queue(null, err -> {
                                }));
            }
            else{
                event.getAuthor().openPrivateChannel().queue(
                        channel -> channel.sendMessage("Уже была попытка подключить этот аккаунт, подождите минуту")
                                .queue(null, err -> {
                                }));
                JDAUtils.reactAndDelete(AdditionalMechanics.getPlugin().getConfig().getString("react.onUserError"), event.getMessage(), AdditionalMechanics.getPlugin().getConfig());
            }
        }
    }

}
