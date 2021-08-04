package ru.ardeon.additionalmechanics.util.discord;

import org.bukkit.plugin.java.JavaPlugin;
import ru.ardeon.additionalmechanics.AdditionalMechanics;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDiscordDatabase extends DiscordDatabase{
    private final File dataBaseFile;
    private Connection connection;

    public SQLiteDiscordDatabase(JavaPlugin plugin) throws IOException, SQLException {
        super(plugin);
        dataBaseFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), "database.db");

        if (!dataBaseFile.exists()) {
            dataBaseFile.createNewFile();
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.initialize();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        connection = DriverManager.getConnection("jdbc:sqlite:" + dataBaseFile);
        return connection;
    }

    @Override
    protected void closeConnection(Connection c) {

    }
}
