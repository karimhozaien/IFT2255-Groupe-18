package com.maville.controller.repository;

import com.maville.controller.services.PasswordUtil;
import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.Intervenant;
import com.maville.model.Resident;
import com.maville.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dépôt responsable de la gestion des utilisateurs dans la base de données.
 * Fournit des méthodes pour l'authentification, l'enregistrement et la récupération des utilisateurs.
 */
public class UserRepository {
    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    /**
     * Recherche un utilisateur dans la base de données à partir des informations fournies.
     * Vérifie si le mot de passe fourni correspond à celui stocké dans la base de données.
     *
     * @param userInfo Une liste contenant l'email et le mot de passe de l'utilisateur.
     * @return L'ID de l'utilisateur si les informations sont valides ; {@code null} sinon.
     */
    public String[] fetchUser(List<String> userInfo) {
        String email = userInfo.get(0);
        String password = userInfo.get(1);
        String selectSQL = "SELECT * FROM Users WHERE email = ?";

        // Établir la connexion
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("password");
                    String idFromDB = rs.getString("id");
                    String userType = rs.getString("user_type");

                    // Vérification du hachage du mot de passe
                    if (PasswordUtil.verifyPassword(password, hashedPasswordFromDB)) {
                        return new String[] {idFromDB, userType};
                    } else {
                        // Mot de passe incorrect ?
                        System.out.println("Mot de passe invalide.");
                        return null;
                    }
                } else {
                    // Le email n'existe pas dans la DB
                    System.out.println("Aucun utilisateur trouvé avec cette adresse : " + email);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            return null;
        }
    }

    /**
     * Enregistre un nouvel utilisateur dans la base de données. Gère les différences
     * entre les types d'utilisateurs {@code Resident} et {@code Intervenant}.
     *
     * @param user L'utilisateur à enregistrer, qui peut être un {@code Resident} ou un {@code Intervenant}.
     */
    public void saveUser(User user) throws SQLException {
        String insertSQL = "INSERT INTO Users(id, name, password, email, user_type, identifier, company_type, birthday, phone_number, residential_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, user.getID());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user instanceof Resident ? "resident" : "intervenant");

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

            pstmt.executeUpdate();
            //System.out.println("L'utilisateur a été sauvegardé."); // Message helper
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Récupère tous les utilisateurs de la base de données. Uniquement leur ID et adresse
     *
     * @return Une liste de tableaux de chaînes contenant les informations des utilisateurs.
     */
    public List<String[]> fetchAllResidents() {
        List<String[]> usersInfo = new ArrayList<>();
        String selectSQL = "SELECT id, residential_address FROM Users WHERE user_type = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            // Set the filter for user_type
            pstmt.setString(1, "resident");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String residentialAddress = rs.getString("residential_address");
                    usersInfo.add(new String[]{id, residentialAddress});
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs de type résident : " + e.getMessage());
        }

        return usersInfo;
    }
}