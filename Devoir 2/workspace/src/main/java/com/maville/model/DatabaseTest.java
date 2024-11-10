package com.maville.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {
        Database db = new Database();

        try (Connection conn = db.connect()) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Read SQL from file
                String sql = new String(Files.readAllBytes(Paths.get("data/schema.sql")));

                // Split SQL commands by semicolons and execute each one
                String[] statements = sql.split(";");

                try (Statement stmt = conn.createStatement()) {
                    for (String statement : statements) {
                        statement = statement.trim();
                        if (!statement.isEmpty()) {
                            stmt.execute(statement + ";");
                        }
                    }
                }

                System.out.println("Tables created successfully from schema.sql.");
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
