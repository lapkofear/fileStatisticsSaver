package com.company.utils;

import com.company.db.connections.ConnectionProvider;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.sql.ResultSet;

public class DbChecker {
	/**
	 * check whether db state satisfied boolean expression, calculated by sql query
	 *
	 * @param sqlQuery should return one boolean 'true' for passing
	 * @return
	 */

	public static void checkDbByQuery(String sqlQuery) {
		ConnectionProvider.runWithConnection(connection -> {
			ResultSet resultSet = connection.createStatement().executeQuery(sqlQuery);
			resultSet.next();
			boolean result = resultSet.getBoolean(1);
			Assert.assertThat(result, Is.is(true));
		});
	}

	public static void executeQuery(String sqlQuery) {
		ConnectionProvider.runWithConnection(connection -> connection.createStatement().execute(sqlQuery));
	}
}
