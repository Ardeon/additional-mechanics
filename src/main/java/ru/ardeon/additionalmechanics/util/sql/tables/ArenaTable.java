package ru.ardeon.additionalmechanics.util.sql.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.DataBaseTable;
import ru.ardeon.additionalmechanics.util.sql.Database;
import ru.ardeon.additionalmechanics.vars.playerdata.ArenaData;

public class ArenaTable extends DataBaseTable {
	static String creatingString() {
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
        return CreateTableArenaStats;
	}
	static String query ="REPLACE INTO arenastats "
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

	public ArenaTable(Database dataBase) {
		super(dataBase, creatingString());
	}
	
	public ArenaData getOrCreate(String uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataBase.getSQLConnection();
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
            	setDefault(uuid);
            }
            return progress;
        } catch (SQLException ex) {
        	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
            	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return null;      
    }
	
	private void setDefault(String uuid) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
        	connection = dataBase.getSQLConnection();
            ps = connection.prepareStatement("REPLACE INTO arenastats (player) VALUES(?)");
            ps.setString(1, uuid);                                                                                                            
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
        	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (connection != null)
                	connection.close();
            } catch (SQLException ex) {
            	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return;
	}
	
	public void saveData(String uuid, ArenaData progress) {
        Connection connection = null;
        PreparedStatement ps = null;
        if (progress==null) {
        	return;
        }
        try {
        	connection = dataBase.getSQLConnection();
            ps = connection.prepareStatement(query);                                                                            
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
        	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (connection != null)
                	connection.close();
            } catch (SQLException ex) {
            	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return;      
    }
}
