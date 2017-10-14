package com.company.db.init;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.callback.BaseFlywayCallback;

import java.sql.*;
import java.util.regex.Pattern;

public class CreateDatabase extends BaseFlywayCallback {
	@Override
	public void beforeMigrate(Connection connection) {
		createDatabaseIfNotExists(connection, flywayConfiguration.getPlaceholders().get("DATABASE.NAME"));
	}

	public static void createDatabaseIfNotExists(Connection connection, String databaseName) {
		if (!Pattern.matches("^[a-zA-Z0-9_]+$", databaseName)) {
			throw new IllegalArgumentException("databaseName may only contain alphanumeric characters.\n" + "Actual: " + databaseName);
		}
		try (PreparedStatement databaseExists = connection.prepareStatement("SELECT 1 FROM pg_database WHERE datname = ?")) {
			databaseExists.setString(1, databaseName);
			try (ResultSet rs = databaseExists.executeQuery()) {
				if (!rs.next()) {
					try (Statement createDatabase = connection.createStatement()) {
						boolean transactionsEnabled = !connection.getAutoCommit();
						if (transactionsEnabled)
							connection.setAutoCommit(true);
						createDatabase.executeUpdate("CREATE DATABASE " + databaseName);
						if (transactionsEnabled)
							connection.setAutoCommit(false);
					}
				}
			}
		} catch (SQLException e) {
			throw new FlywayException(e);
		}
	}
}