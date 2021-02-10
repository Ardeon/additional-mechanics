package ru.ardeon.additionalmechanics.util.sql.tables;

import ru.ardeon.additionalmechanics.util.sql.DataBaseTable;
import ru.ardeon.additionalmechanics.util.sql.Database;

public class AbilityAndStatsTable extends DataBaseTable{
	static String creating = "CREATE TABLE IF NOT EXISTS vars (" +
			"`player` varchar(36) NOT NULL," +
			"`horsespeed` int NOT NULL DEFAULT 0," +
			"`horsejump` int NOT NULL DEFAULT 0," +
			"`horsehp` int NOT NULL DEFAULT 0," +
			"`hookLength` int NOT NULL DEFAULT 0," +
			"`adventurerRadarSmall` int NOT NULL DEFAULT 0," +
			"`adventurerRadarMedium` int NOT NULL DEFAULT 0," +
			"`adventurerRadarBig` int NOT NULL DEFAULT 0," +
			"`minerExtradrop` int NOT NULL DEFAULT 0," +
			"`minerSize` int NOT NULL DEFAULT 0," +
			"`minerRadarSmall` int NOT NULL DEFAULT 0," +
			"`minerRadarMedium` int NOT NULL DEFAULT 0," +
			"`minerRadarBig` int NOT NULL DEFAULT 0," +
			"PRIMARY KEY (`player`)" +
			");";

	public AbilityAndStatsTable(Database dataBase) {
		super(dataBase, creating);
	}

}
