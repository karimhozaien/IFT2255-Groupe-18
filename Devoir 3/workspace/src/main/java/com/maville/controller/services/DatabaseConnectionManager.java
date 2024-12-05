package com.maville.controller.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
    private static final String URL = "jdbc:sqlite:data/mydatabase.db";
    private static DatabaseConnectionManager instance;
    private Connection connection;

    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }

    // Initialize the database and create tables if they don't exist
    public static void connect() {
        try {
            DatabaseConnectionManager dbManager = getInstance();
            Connection conn = dbManager.getConnection();
            //System.out.println("Connected to SQLite database."); // helper
            dbManager.initializeDatabaseTables(conn); // Initialize tables if needed
        } catch (SQLException e) {
            System.out.println("Error during database initialization: " + e.getMessage());
        }
    }

    public static void close() {
        if (instance != null && instance.connection != null) {
            try {
                if (!instance.connection.isClosed()) {
                    instance.connection.close();
                    //System.out.println("Database connection closed."); // helper
                }
            } catch (SQLException e) {
                System.out.println("Error closing the database connection: " + e.getMessage());
            } finally {
                instance = null; // Reset the instance to allow a new connection in the future
            }
        }
    }

    // Create necessary tables in the database
    public void initializeDatabaseTables(Connection conn) throws SQLException {
        if (isTableInitialized(conn, "Users")) {
             //System.out.println("La table User existe déjà."); // helper
        } else {
            //System.out.println("Création de la table Users..."); // helper
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
            //System.out.println("La table Users a été créée."); // helper
        }

        if (isTableInitialized(conn, "Projects")) {
            //System.out.println("La table Projects existe déja."); // helper
        } else {
            //System.out.println("Création de la table Projects..."); // helper
            String userTableSQL =
                    "CREATE TABLE IF NOT EXISTS Projects (" +
                        "id TEXT PRIMARY KEY," +
                        "title TEXT NOT NULL," +
                        "type_of_work TEXT NOT NULL CHECK(type_of_work IN (" +
                            "'ROAD', " +
                            "'GAS/ELECTRICITY', " +
                            "'CONSTRUCTION/RENOVATION', " +
                            "'LANDSCAPE', " +
                            "'PUBLIC_TRANSPORT', " +
                            "'SIGNAGE/LIGHTING', " +
                            "'UNDERGROUND', " +
                            "'RESIDENTIAL', " +
                            "'URBAN_MAINTENANCE', " +
                            "'TELECOMMUNICATIONS_NETWORKS', " +
                            "'OTHER'" +
                            "))," +
                        "affected_neighbourhood TEXT NOT NULL," +
                        "affected_streets TEXT NOT NULL," +
                        "start_date TEXT NOT NULL," +
                        "end_date TEXT NOT NULL," +
                        "work_schedule TEXT," +
                        "work_status TEXT CHECK(work_status IN ('ONGOING', 'PLANNED'))" +
                    ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(userTableSQL);
            }
            // System.out.println("La table Projects a été créée."); // helper
        }

        if (isTableInitialized(conn, "Notifications")) {
            //System.out.println("La table Notifications existe déjà."); // helper
        } else {
            //System.out.println("Création de la table Notifications..."); // helper
            String notificationTableSQL =
                    "CREATE TABLE IF NOT EXISTS Notifications (" +
                            "id TEXT PRIMARY KEY," +
                            "description TEXT NOT NULL," +
                            "residents_id TEXT," + // peut-etre null si aucun resident dans le quartier est auth
                            "seen_residents_ids TEXT" + // null dès le départ
                    ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(notificationTableSQL);
            }
            //System.out.println("La table Notifications a été créée."); // helper
        }

        if (isTableInitialized(conn, "WorkRequests")) {
            // System.out.println("La table WorkRequests existe déjà."); // helper
        } else {
            // System.out.println("Création de la table WorkRequests..."); // helper
            String userTableSQL =
                    "CREATE TABLE IF NOT EXISTS WorkRequests (" +
                        "title TEXT NOT NULL," +
                        "description TEXT," +
                        "project_type TEXT NOT NULL," +
                        "expected_date TEXT NOT NULL" +
                    ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(userTableSQL);
            }
            // System.out.println("La table WorkRequests a été créée."); // helper
        }
    }

    private boolean isTableInitialized(Connection conn, String tableName) throws SQLException {
        var metaData = conn.getMetaData();
        try (var rs = metaData.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }
}
