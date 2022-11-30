package org.revature.p1.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Properties properties = new Properties();

    private static ConnectionFactory factory;

    private ConnectionFactory() {
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (factory == null) {
            factory = new ConnectionFactory();
        }
        return factory;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                                                            properties.getProperty("username"),
                                                            properties.getProperty("password"));
        if (connection == null) {
            throw new RuntimeException("Could not establish connection with the database");
        }

        connection.setAutoCommit(true);
        return connection;
    }
}
