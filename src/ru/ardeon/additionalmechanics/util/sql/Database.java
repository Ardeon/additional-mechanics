package ru.ardeon.additionalmechanics.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.util.sql.tables.ArenaTable;
import ru.ardeon.additionalmechanics.util.sql.tables.MoneyTable;


public abstract class Database {
	AdditionalMechanics plugin;
    Connection connection;
    public MoneyTable moneyTable;
    public ArenaTable arenatable;
    
    public Database(AdditionalMechanics instance){
        plugin = instance;
    }
    

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        moneyTable = new MoneyTable(this);
        arenatable = new ArenaTable(this);
    }
    
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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