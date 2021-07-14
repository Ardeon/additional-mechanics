package ru.ardeon.additionalmechanics.util.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseTable {
	protected Database dataBase;
	
	protected DataBaseTable(Database dataBase, String creating){
		this.dataBase=dataBase;
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

}
