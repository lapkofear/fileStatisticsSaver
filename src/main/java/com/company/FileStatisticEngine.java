package com.company;

import com.company.dao.FileStatisticDao;
import com.company.db.init.CreateDatabase;
import com.company.db.properties.DatabaseProperties;
import com.company.service.FileScanner;
import com.company.service.FileStatisticsCalculator;
import org.flywaydb.core.Flyway;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStatisticEngine {
	private static final Logger LOG = Logger.getLogger(FileStatisticEngine.class.getName());
	private static final String FLYWAY_MIGRATIONS_FOLDER = "classpath:flyway/migrations";

	public static void main(String... args) {
		createDb();
		migrateDb();

		FileStatisticDao.insertStatistics(FileStatisticsCalculator.calculateFileStatistics(FileScanner.readFilesFromFolders(args)));
	}


	private static void createDb() {
		try {
			CreateDatabase.createDatabaseIfNotExists(
					DriverManager.getConnection(DatabaseProperties.getServerUrl(), DatabaseProperties.getUser(), DatabaseProperties.getPassword()),
					DatabaseProperties.getDbName());
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Cannot connect to database ", e);
		}
	}

	private static void migrateDb() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(DatabaseProperties.getFullUrl(), DatabaseProperties.getUser(), DatabaseProperties.getPassword());
		flyway.setLocations(FLYWAY_MIGRATIONS_FOLDER);
		flyway.migrate();
	}
}