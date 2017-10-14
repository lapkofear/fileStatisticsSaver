package com.company.db.connections;

import com.company.db.properties.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionProvider {
	private static final Logger LOG = Logger.getLogger(ConnectionProvider.class.getName());

	@FunctionalInterface
	public interface OperationWithConnection {
		void runWithConnection(Connection connection) throws SQLException;
	}

	public static void runWithConnection(OperationWithConnection operationWithConnection) {
		try (Connection connection = DriverManager.getConnection(DatabaseProperties.getFullUrl(), DatabaseProperties.getUser(), DatabaseProperties
				.getPassword())) {
			connection.setAutoCommit(false);
			operationWithConnection.runWithConnection(connection);
			connection.commit();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error occurred while executing operation", e);
			e.getNextException().printStackTrace();
		}
	}
}