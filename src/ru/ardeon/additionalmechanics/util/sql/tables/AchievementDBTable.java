package ru.ardeon.additionalmechanics.util.sql.tables;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.AchievementDB;
import ru.ardeon.additionalmechanics.vars.playerdata.PlayerAchievement;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class AchievementDBTable {
    private AchievementDB dataBase;
//"SELECT * FROM Achievements ORDER BY AchievementPoint DESC LIMIT 20;"
    static private String creating = "CREATE TABLE IF NOT EXISTS Achievements (" +
            "`player` varchar(36) NOT NULL," +
            "`AchievementPoint` int NOT NULL DEFAULT 0," +
            "PRIMARY KEY (`player`)" +
            ");";

    public AchievementDBTable(AchievementDB dataBase) {
        this.dataBase = dataBase;
        Connection connection = dataBase.getSQLConnection();
        create(connection, creating);
    }

    private void create(Connection connection, String creating) {
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(creating);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PlayerAchievement> getTop() {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = dataBase.getSQLConnection();
            ps = connection.prepareStatement("SELECT * FROM Achievements ORDER BY AchievementPoint DESC LIMIT 20;");
            ResultSet rs = ps.executeQuery();
            ArrayList<PlayerAchievement> top = new ArrayList<>();

            while(rs.next()){
                String uuidString = rs.getString("player");
                int points = rs.getInt("AchievementPoint");
                PlayerAchievement pa = new PlayerAchievement(uuidString, points);
                top.add(pa);
            }
            return top;
        } catch (SQLException ex) {
            AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } //finally {
            //try {
                //if (ps != null)
                //    ps.close();
                //if (connection != null)
                //    connection.close();
            //} catch (SQLException ex) {
            //    AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            //}
        //}
        return null;
    }

    public int getOrCreate(String uuid) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dataBase.getSQLConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Achievements WHERE player = '"+uuid+"';");
            boolean playerExist = false;
            ResultSet rs = preparedStatement.executeQuery();
            int AchievementPoint = 0;
            while(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(uuid.toLowerCase())){
                    playerExist = true;
                    AchievementPoint = rs.getInt(2);
                }
            }
            if (!playerExist) {
                setDefault(uuid);
                return 0;
            }
            return AchievementPoint;
        } catch (SQLException ex) {
            AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        //} finally {
        //    try {
        //        if (preparedStatement != null)
        //            preparedStatement.close();
        //        if (connection != null)
        //            connection.close();
        //    } catch (SQLException ex) {
        //        AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
        //    }
        }
        return 0;
    }

    public void setDefault(String uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataBase.getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO Achievements (player,AchievementPoint) VALUES(?,?)");
            ps.setString(1, uuid);
            ps.setInt(2, 0);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        //} finally {
        //    try {
        //        if (ps != null)
        //            ps.close();
        //        if (conn != null)
        //            conn.close();
        //    } catch (SQLException ex) {
        //        AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
        //    }
        }
        return;
    }

    public void saveData(String uuid, int AchievementPoint) {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = dataBase.getSQLConnection();
            ps = connection.prepareStatement("REPLACE INTO Achievements (player,AchievementPoint) VALUES(?,?)");
            ps.setString(1, uuid);
            ps.setInt(2, AchievementPoint);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        //} finally {
        //    try {
        //        if (ps != null)
        //            ps.close();
        //        if (connection != null)
        //            connection.close();
        //    } catch (SQLException ex) {
        //        AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
        //    }
        }
        return;
    }
}
