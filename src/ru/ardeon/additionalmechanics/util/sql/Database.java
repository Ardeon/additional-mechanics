package ru.ardeon.additionalmechanics.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.tables.ArenaTable;
import ru.ardeon.additionalmechanics.util.sql.tables.MoneyTable;
import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;
import ru.ardeon.additionalmechanics.vars.playerdata.MoneyData;


public abstract class Database {
	AdditionalMechanics plugin;
    Connection connection;
    public MoneyTable moneyTable;
    public ArenaTable arenatable;
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
    String query ="REPLACE INTO arenastats "
    		+ "(player,class1boot,class1leg,class1chest,class1power1,class1power2,class1power3,class1power4,class1power5,"
    		+ "class2boot,class2leg,class2chest,class2power1,class2power2,class2power3,class2power4,class2power5,"
    		+ "class3boot,class3leg,class3chest,class3power1,class3power2,class3power3,class3power4,class3power5,"
    		+ "class4boot,class4leg,class4chest,class4power1,class4power2,class4power3,class4power4,class4power5,"
    		+ "class5boot,class5leg,class5chest,class5power1,class5power2,class5power3,class5power4,class5power5,"
    		+ "class6boot,class6leg,class6chest,class6power1,class6power2,class6power3,class6power4,class6power5,"
    		+ "class7boot,class7leg,class7chest,class7power1,class7power2,class7power3,class7power4,class7power5,"
    		+ "class8boot,class8leg,class8chest,class8power1,class8power2,class8power3,class8power4,class8power5,"
    		+ "class9boot,class9leg,class9chest,class9power1,class9power2,class9power3,class9power4,class9power5,"
    		+ "class10boot,class10leg,class10chest,class10power1,class10power2,class10power3,class10power4,class10power5,"
    		+ "class11boot,class11leg,class11chest,class11power1,class11power2,class11power3,class11power4,class11power5,"
    		+ "class12boot,class12leg,class12chest,class12power1,class12power2,class12power3,class12power4,class12power5) "
    		+ "VALUES(?,"//player
    		+ "?,?,?,?,?,?,?,?,"//1
    		+ "?,?,?,?,?,?,?,?,"//2
    		+ "?,?,?,?,?,?,?,?,"//3
    		+ "?,?,?,?,?,?,?,?,"//4
    		+ "?,?,?,?,?,?,?,?,"//5
    		+ "?,?,?,?,?,?,?,?,"//6
    		+ "?,?,?,?,?,?,?,?,"//7
    		+ "?,?,?,?,?,?,?,?,"//8
    		+ "?,?,?,?,?,?,?,?,"//9
    		+ "?,?,?,?,?,?,?,?,"//10
    		+ "?,?,?,?,?,?,?,?,"//11
    		+ "?,?,?,?,?,?,?,?)";//12
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
        String CreateTableArenaStats = "CREATE TABLE IF NOT EXISTS arenastats (" +
    			"`player` varchar(36) NOT NULL,";
        for (int i = 1; i <= 12; i++) {
        	CreateTableArenaStats = CreateTableArenaStats + 
        			"`class" + i + "boot` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "leg` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "chest` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "power1` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "power2` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "power3` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "power4` tinyint NOT NULL DEFAULT 0," +
        			"`class" + i + "power5` tinyint NOT NULL DEFAULT 0,";
        }
        CreateTableArenaStats = CreateTableArenaStats + 
        		"PRIMARY KEY (`player`)" +
    			");";
        try {
			Statement s = connection.createStatement();
			s.executeUpdate(CreateTableArenaStats);
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
    
    public ArenaData getOrCreatePlayerStats(Player player) {
		String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM arenastats WHERE player = '"+uuid+"';");
            boolean playerExist = false;
            ResultSet rs = ps.executeQuery();
            ArenaData progress = new ArenaData();
            while(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(uuid.toLowerCase())){
                	playerExist = true;
                	for (int i = 1; i <= 12; i++) {
                		progress.setBoots(i, rs.getInt("class" + i + "boot"));
                		progress.setLegs(i, rs.getInt("class" + i + "leg"));
                		progress.setChest(i, rs.getInt("class" + i + "chest"));
                		for (int j = 1; j <= 5; j++) {
                			progress.setPower(i, j, rs.getInt("class" + i + "power" + j));
                		}
                	}
                }
            }
            if (!playerExist) {
            	setDefaultArenaProgress(player);
            }
            return progress;
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
    
    private void setDefaultArenaProgress(Player player) {
    	String uuid = player.getUniqueId().toString().toLowerCase();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO arenastats (player) VALUES(?)");
            ps.setString(1, uuid);                                                                                                            
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


	public MoneyData getOrCreatePlayerScore(Player player) {
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
            	return new MoneyData();
            }
            return new MoneyData(var1, var2, var3, var4, var5);
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
    
    public void saveVars(String uuid, MoneyData score) {
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
    
    public void saveStats(String uuid, ArenaData progress) {
        Connection conn = null;
        PreparedStatement ps = null;
        if (progress==null) {
        	return;
        }
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement(query);                                                                            
            ps.setString(1, uuid);
            int x = 2;
            for (int i = 1; i <= 12; i++) {
            	ps.setInt(x, progress.getBoots(i));
            	x++;
	            ps.setInt(x, progress.getLegs(i));
	            x++;
	            ps.setInt(x, progress.getChest(i));
	            x++;
	            for (int j = 1; j <= 5; j++) {
	            	ps.setInt(x, progress.getPower(i, j));
	            	x++;
	            }
            }
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