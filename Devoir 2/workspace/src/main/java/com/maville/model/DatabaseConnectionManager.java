package com.maville.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
    private static final String URL = "jdbc:sqlite:data/mydatabase.db";
    private static DatabaseConnectionManager instance;
    private Connection connection;

    // Singleton instance access
    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    // Establish and return the shared connection
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }

    // Initialize the database and create tables if they don't exist
    public static void initialize() {
        try {
            DatabaseConnectionManager dbManager = getInstance();
            Connection conn = dbManager.getConnection();
            System.out.println("Connected to SQLite database.");
            dbManager.initializeDatabaseTables(conn); // Initialize tables if needed
        } catch (SQLException e) {
            System.out.println("Error during database initialization: " + e.getMessage());
        }
    }

    // Create necessary tables in the database
    public void initializeDatabaseTables(Connection conn) throws SQLException {
        if (isTableInitialized(conn, "Users")) {
            System.out.println("Table User already exists.");
        } else {
            System.out.println("Creating table User...");
            String userTableSQL =
                    "CREATE TABLE IF NOT EXISTS Users (" +
                            "id TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "password TEXT NOT NULL," +
                            "email TEXT NOT NULL UNIQUE," +
                            "user_type TEXT NOT NULL CHECK (user_type IN ('resident', 'intervenant'))," +
                            "identifier INT," +
                            "company_type TEXT," +
                            "birthday TEXT," +
                            "phone_number TEXT," +
                            "residential_address TEXT," +
                            "CHECK ((user_type = 'resident' AND identifier IS NULL AND company_type IS NULL AND " +
                            "birthday IS NOT NULL AND phone_number IS NOT NULL AND residential_address IS NOT NULL) OR" +
                            " (user_type = 'intervenant' AND identifier IS NOT NULL AND company_type IS NOT NULL " +
                            "AND birthday IS NULL AND phone_number IS NULL AND residential_address IS NULL))" +
                    ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(userTableSQL);
            }
            System.out.println("Table User created.");
        }

        if (isTableInitialized(conn, "Works")) {
            System.out.println("Table Work already exists.");
        } else {
            // TODO: Create the "Work" table here.
        }
    }

    // Check if a table exists
    private boolean isTableInitialized(Connection conn, String tableName) throws SQLException {
        var metaData = conn.getMetaData();
        try (var rs = metaData.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }
}
