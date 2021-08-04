package ru.ardeon.additionalmechanics.util.discord;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class DiscordDatabase {

    protected final JavaPlugin plugin;

    /**
     * Creates a new database handler
     *
     * @param plugin a link to the plugin
     */
    protected DiscordDatabase(JavaPlugin plugin) {
        this.plugin = plugin;
        //this.random = new SecureRandom();
    }

    /**
     * Gets a connection for querying
     *
     * @return the connection
     * @throws SQLException if an SQL error occurs
     */
    protected abstract Connection getConnection() throws SQLException;

    /**
     * Closes a connection if it should be closed or returns it to the pool
     *
     * @param c the connection
     * @throws SQLException if an SQL error occurs
     */
    protected abstract void closeConnection(Connection c) throws SQLException;

    /**
     * Initializes the database, creating tables if needed.
     *
     * @throws SQLException if an SQL error occurs
     */
    protected void initialize() throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS `" + plugin.getConfig().getString("database.tablePrefix") + "_discordmcusers` ("
                        + "`discord_id` varchar(18) NOT NULL,"
                        + "`minecraft_uuid` varchar(36) NOT NULL,"
                        + "PRIMARY KEY (`discord_id`));"
        );

        preparedStatement.execute();

        this.closeConnection(connection);
    }

    /**
     * Links a user.
     *
     * @param discordID the Discord ID of the user
     * @param minecraftUUID the Minecraft UUID of the user
     * @throws SQLException if an SQL error occurs
     */
    public void linkUser(String discordID, String minecraftUUID) throws SQLException {
        checkAsync();

        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + plugin.getConfig().getString("database.tablePrefix") +
                        "_discordmcusers (discord_id, minecraft_uuid) SELECT ?, ? FROM (SELECT 1) " +
                        "as A WHERE NOT EXISTS(SELECT * FROM " + plugin.getConfig().getString("database.tablePrefix") +
                        "_discordmcusers WHERE minecraft_uuid = ?);"
        );

        preparedStatement.setString(1, discordID);
        preparedStatement.setString(2, minecraftUUID);
        preparedStatement.setString(3, minecraftUUID);

        preparedStatement.execute();

        this.closeConnection(connection);
    }

    /**
     * Unlinks a user.
     *
     * @param discord_id the UUID of the user to unlink
     * @throws SQLException if an SQL error occurs
     */
    public void unlink(String discord_id) throws SQLException {
        checkAsync();

        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM "
                + plugin.getConfig().getString("database.tablePrefix") + "_discordmcusers WHERE discord_id = ?");

        preparedStatement.setString(1, discord_id);

        preparedStatement.execute();

        this.closeConnection(connection);
    }

    /**
     * Runs a callback for every linked user.
     *
     * @param callback the callback. Receives a LinkedUser object.
     * @throws SQLException if an SQL error occurs
     */
    public void forAllLinkedUsers(Consumer<LinkedUser> callback) throws SQLException {
        checkAsync();

        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT discord_id, minecraft_uuid FROM "
                + plugin.getConfig().getString("database.tablePrefix") + "_discordmcusers");

        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            callback.accept(new LinkedUser(
                    result.getString(1), // discord id
                    result.getString(2)  // uuid
            ));
        }

        this.closeConnection(connection);
    }

    /**
     * Gets info from a linked user
     *
     * @param identifier the Discord ID or Minecraft UUID of the user
     * @return A LinkedUser object with information about the user
     * @throws SQLException if an SQL error occurs
     */
    public LinkedUser getLinkedUser(String identifier) throws SQLException {
        checkAsync();

        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT discord_id, minecraft_uuid FROM "
                + plugin.getConfig().getString("database.tablePrefix") + "_discordmcusers "
                + "WHERE discord_id = ? OR minecraft_uuid = ?"
        );

        preparedStatement.setString(1, identifier);
        preparedStatement.setString(2, identifier);

        ResultSet result = preparedStatement.executeQuery();

        LinkedUser user = null;
        if (result.next()) {
            user = new LinkedUser(
                    result.getString(1), // discord id
                    result.getString(2)  // minecraft uuid
            );
            preparedStatement.close();
        }


        this.closeConnection(connection);

        return user;
    }

    private void checkAsync() {
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("Attempted to execute a database operation from the server thread!");
        }
    }

    /**
     * A class with information about the user.
     */
    public static class LinkedUser {
        public final String discordId;
        public final String uuid;

        /**
         * Constructor
         *
         * @param discordId of the user
         * @param uuid of the user
         */
        LinkedUser(String discordId, String uuid) {
            this.discordId = discordId;
            this.uuid = uuid;
        }
    }
}
