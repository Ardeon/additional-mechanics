package ru.ardeon.additionalmechanics.util.sql;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.entity.Player;
import ru.ardeon.additionalmechanics.util.sql.tables.AchievementDBTable;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerAchievement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public abstract class AchievementDB {
    AdditionalMechanics plugin;
    Connection connection;
    AchievementDBTable table;

    public AchievementDB(AdditionalMechanics instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        table = new AchievementDBTable(this);
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateTop(){
        //plugin.varManager.uservars.playerAchievementTop = getTop();
    }

    public ArrayList<PlayerAchievement> getTop(){
        return table.getTop();
    }

    public int addPoints(Player player, int adding) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        int value = table.getOrCreate(uuid);
        value +=adding;
        table.saveData(uuid, value);
        updateTop();
        return value;
    }

    public void clearPoints(Player player) {
        String uuid = player.getUniqueId().toString().toLowerCase();
        table.saveData(uuid, 0);
        updateTop();
    }

    public void close(PreparedStatement ps, ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection:", ex);
        }
    }
}
