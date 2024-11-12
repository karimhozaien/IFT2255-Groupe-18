package com.maville.controller.repository;

import com.maville.controller.auth.PasswordUtil;
import com.maville.model.DatabaseConnectionManager;
import com.maville.model.Intervenant;
import com.maville.model.Resident;
import com.maville.model.User;

import java.sql.*;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public String fetchUser(List<String> userInfo) {
        String email = userInfo.get(0);
        String password = userInfo.get(1);
        String selectSQL = "SELECT * FROM Users WHERE email = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    String idFromDB = rs.getString("id");

                    if (PasswordUtil.getInstance().verifyPassword(password, hashedPasswordFromDB)) {
                        return idFromDB;
                    } else {
                        // Password doesn't match
                        System.out.println("Invalid password.");
                        return null;
                    }
                } else {
                    // Email doesn't exist in the database
                    System.out.println("No user found with email: " + email);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error logging user: " + e.getMessage());
            return null;
        }
    }

    public void saveUser(User user) {
        String insertSQL = "INSERT INTO Users(id, name, password, email, user_type, identifier, company_type, birthday, phone_number, residential_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Set common fields
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, PasswordUtil.getInstance().hashPassword(user.getPassword()));
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user instanceof Resident ? "resident" : "intervenant");

            // Set type-specific fields
            if (user instanceof Intervenant intervenant) {
                pstmt.setInt(6, intervenant.getIdentifier());
                pstmt.setString(7, intervenant.getCompanyType().toString());
                pstmt.setNull(8, java.sql.Types.NULL);
                pstmt.setNull(9, java.sql.Types.NULL);
                pstmt.setNull(10, java.sql.Types.NULL);
            } else if (user instanceof Resident resident) {
                pstmt.setNull(6, java.sql.Types.NULL);
                pstmt.setNull(7, java.sql.Types.NULL);
                pstmt.setString(8, resident.getBirthday());
                pstmt.setString(9, resident.getPhoneNumber());
                pstmt.setString(10, resident.getResidentialAddress());
            }

            // Execute the update
            pstmt.executeUpdate();
            System.out.println("User saved successfully.");

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
}