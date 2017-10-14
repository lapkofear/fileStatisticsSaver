package com.company.db.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
	private static final Logger LOG = Logger.getLogger(DatabaseProperties.class.getName());
	private static final String DB_PROPERTIES = "src/main/resources/db.properties";
	private static final String DB_URL = "database.url";
	private static final String DB_NAME = "database.name";
	private static final String DB_USER = "database.user";
	private static final String DB_PASSWORD = "database.password";
	private static Properties properties;
	private static String url;
	private static String name;
	private static String user;
	private static String password;

	private static Properties getDbProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(new FileInputStream(DB_PROPERTIES));
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Error occurred while db properties reading", e);
			}
		}
		return properties;
	}

	public static String getFullUrl() {
		return getServerUrl() + getDbName();
	}

	public static String getServerUrl() {
		if (url == null) {
			url = getDbProperties().getProperty(DB_URL);
		}
		return url;
	}

	public static String getDbName() {
		if (name == null) {
			name = getDbProperties().getProperty(DB_NAME);
		}
		return name;
	}

	public static String getUser() {
		if (user == null) {
			user = getDbProperties().getProperty(DB_USER);
		}
		return user;
	}

	public static String getPassword() {
		if (password == null) {
			password = getDbProperties().getProperty(DB_PASSWORD);
		}
		return password;
	}
}