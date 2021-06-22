package ru.ardeon.additionalmechanics.util.sql;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class AchievementDBSQLite extends AchievementDB{
    String dbname = "Achievement";
    File dataFolder;
    public AchievementDBSQLite(AdditionalMechanics instance){
        super(instance);
        dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        load();
        dbname = plugin.configLoader.getVars().getString("AchievementDBSQLite.Filename", "Achievement");
    }

    private boolean createFileIfNotExist() {
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
                AdditionalMechanics.getPlugin().getLogger().info("create");
                return true;
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        return false;
    }

    public Connection getSQLConnection() {
        createFileIfNotExist();
        try {
            //AdditionalMechanics.getPlugin().getLogger().info("connection");
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }


    public void load() {
        initialize();
    }
}
