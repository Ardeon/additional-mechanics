package ru.ardeon.additionalmechanics.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.vars.Score;


public abstract class Database {
	AdditionalMechanics plugin;
    Connection connection;
    public String table = "vars";
    public String CreateTable = "CREATE TABLE IF NOT EXISTS vars (" +
			"`player` varchar(36) NOT NULL," +
			"`var1` int NOT NULL," +
			"`var2` int NOT NULL," +
			"`var3` int NOT NULL," +
			"`var4` int NOT NULL," +
			"`var5` int NOT NULL," +
			"PRIMARY KEY (`player`)" +
			");";
    
    
    public Database(AdditionalMechanics instance){
        plugin = instance;
    }
    

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try {
			Statement s = connection.createStatement();
			s.executeUpdate(CreateTable);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }
    
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public Integer getVar(String uuid, int varID) {//legacy
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT player,var" + varID + " FROM " + table + " WHERE player = '"+uuid+"';");
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(uuid.toLowerCase())){
                    return rs.getInt(2); 
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return null;
    }
    
    public Score getOrCreatePlayer(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE player = '"+uuid+"';");
            boolean playerExist = false;
            ResultSet rs = ps.executeQuery();
            int var1 = 0, var2 = 0, var3 = 0, var4 = 0, var5 = 0;
            while(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(uuid.toLowerCase())){
                	playerExist = true;
                	var1 = rs.getInt(2);
                	var2 = rs.getInt(3);
                	var3 = rs.getInt(4);
                	var4 = rs.getInt(5);
                	var5 = rs.getInt(6);
                }
            }
            if (!playerExist) {
            	setDefaultVars(player);
            	return new Score(uuid);
            }
            return new Score(uuid, var1, var2, var3, var4, var5);
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return null;      
    }
    
    public void setDefaultVars(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,var1,var2,var3,var4,var5) VALUES(?,?,?,?,?,?)");
            ps.setString(1, uuid);                                                                                                            
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return;      
    }
    
    public void saveVars(String uuid, Score score) {
        Connection conn = null;
        PreparedStatement ps = null;
        if (score==null) {
        	return;
        }
        int var1 = score.var[0];
        int var2 = score.var[1];
        int var3 = score.var[2];
        int var4 = score.var[3];
        int var5 = score.var[4];
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO vars (player,var1,var2,var3,var4,var5) VALUES(?,?,?,?,?,?)");                                                                                                            
            ps.setString(1, uuid);
            ps.setInt(2, var1);
            ps.setInt(3, var2);
            ps.setInt(4, var3);
            ps.setInt(5, var4);
            ps.setInt(6, var5);
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return;      
    }

	public void setVar(String uuid, int varID, Integer value) {//legacy
		if (varID < 1 || varID >5) {
			return;
		}
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("UPDATE vars SET var"+varID+"=? WHERE player = '"+uuid+"';");                                                                                                            
            ps.setInt(1, value); 
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return;      
    }


    public void close(PreparedStatement ps,ResultSet rs){
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