package ru.ardeon.additionalmechanics.util.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import ru.ardeon.additionalmechanics.AdditionalMechanics;


public class SQLite extends Database{
	String dbname = "vars";
	public SQLite(AdditionalMechanics instance){
		super(instance);
		table = "vars";
		load();
		dbname = plugin.configLoader.getVars().getString("SQLite.Filename", "vars");
	}

	public String SQLiteCreateTable = "CREATE TABLE IF NOT EXISTS vars (" +
			"`player` varchar(36) NOT NULL," +
			"`var1` int NOT NULL," +
			"`var2` int NOT NULL," +
			"`var3` int NOT NULL," +
			"`var4` int NOT NULL," +
			"`var5` int NOT NULL," +
			"PRIMARY KEY (`player`)" +
			");";

	public Connection getSQLConnection() {
		File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
		if (!dataFolder.exists()){
			try {
				dataFolder.createNewFile();
				AdditionalMechanics.getPlugin().getLogger().info("create");
			} catch (IOException e) {
				plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
			}
		}
		try {
			AdditionalMechanics.getPlugin().getLogger().info("connection");
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
		connection = getSQLConnection();
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(SQLiteCreateTable);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
    }
}