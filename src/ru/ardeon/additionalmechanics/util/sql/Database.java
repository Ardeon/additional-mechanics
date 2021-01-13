package ru.ardeon.additionalmechanics.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;


public abstract class Database {
	AdditionalMechanics plugin;
    Connection connection;
    public String table = "vars";
    public Database(AdditionalMechanics instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
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

    public Integer getVar(String uuid, int varID) {
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
    
    public void newPlayer(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT player FROM " + table + " WHERE player = '"+uuid+"';");
            int count = ps.executeUpdate();
            if (count == 0) {
            	setDefaultVars(player);
            }
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
    
    public void setDefaultVars(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,var1,var2,var3,var4,var5,) VALUES(?,?,?,?,?)");
            ps.setString(1, uuid);                                                                                                            
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
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

	public void setVar(Player player, int varID, Integer value) {
		String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("UPDATE vars SET ?=? WHERE player = '"+uuid+"';");
            ps.setString(1, "var"+varID);                                                                                                            
            ps.setInt(2, value); 
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