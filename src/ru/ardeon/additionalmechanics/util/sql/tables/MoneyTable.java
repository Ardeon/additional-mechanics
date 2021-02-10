package ru.ardeon.additionalmechanics.util.sql.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.DataBaseTable;
import ru.ardeon.additionalmechanics.util.sql.Database;
import ru.ardeon.additionalmechanics.vars.playerdata.MoneyData;

public class MoneyTable extends DataBaseTable{

	static String creating = "CREATE TABLE IF NOT EXISTS vars (" +
			"`player` varchar(36) NOT NULL," +
			"`var1` int NOT NULL DEFAULT 0," +
			"`var2` int NOT NULL DEFAULT 0," +
			"`var3` int NOT NULL DEFAULT 0," +
			"`var4` int NOT NULL DEFAULT 0," +
			"`var5` int NOT NULL DEFAULT 0," +
			"PRIMARY KEY (`player`)" +
			");";
	
	public MoneyTable(Database dataBase) {
		super(dataBase, creating);
	}

	public MoneyData getOrCreate(String uuid) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
        	connection = dataBase.getSQLConnection();
        	preparedStatement = connection.prepareStatement("SELECT * FROM vars WHERE player = '"+uuid+"';");
            boolean playerExist = false;
            ResultSet rs = preparedStatement.executeQuery();
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
            	setDefault(uuid);
            	return new MoneyData();
            }
            return new MoneyData(var1, var2, var3, var4, var5);
        } catch (SQLException ex) {
        	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Couldn't execute SQL statement:", ex);
        } finally {
            try {
                if (preparedStatement != null)
                	preparedStatement.close();
                if (connection != null)
                	connection.close();
            } catch (SQLException ex) {
            	AdditionalMechanics.getPlugin().getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return null;
	}

	public void setDefault(String uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataBase.getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO vars (player,var1,var2,var3,var4,var5) VALUES(?,?,?,?,?,?)");
            ps.setString(1, uuid);                                                                                                            
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.executeUpdate();
            return;
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
        return;
	}

	public void saveData(String uuid, MoneyData moneyData) {
		PreparedStatement ps = null;
		Connection connection = null;
		if (moneyData==null) {
        	return;
        }
        
        int var1 = moneyData.var[0];
        int var2 = moneyData.var[1];
        int var3 = moneyData.var[2];
        int var4 = moneyData.var[3];
        int var5 = moneyData.var[4];
        try {
        	connection = dataBase.getSQLConnection();
            ps = connection.prepareStatement("REPLACE INTO vars (player,var1,var2,var3,var4,var5) VALUES(?,?,?,?,?,?)");                                                                                                            
            ps.setString(1, uuid);
            ps.setInt(2, var1);
            ps.setInt(3, var2);
            ps.setInt(4, var3);
            ps.setInt(5, var4);
            ps.setInt(6, var5);
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
