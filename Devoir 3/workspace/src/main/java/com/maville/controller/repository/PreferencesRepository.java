package com.maville.controller.repository;

import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.PreferencesHoraire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreferencesRepository {

    public void savePreferences(PreferencesHoraire preferencesHoraire) {
        String insertSQL = "INSERT INTO PreferencesHoraire(rue, quartier, heures_en_semaine, heures_en_fin_de_semaine) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, preferencesHoraire.getStreet());
            pstmt.setString(2, preferencesHoraire.getNeighbourhood());
            pstmt.setString(3, preferencesHoraire.getWeekHours());
            pstmt.setString(4, preferencesHoraire.getWeekendHours());

            pstmt.executeUpdate();
            //System.out.println("La requête a été sauvegardée."); // Message helper
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la requête : " + e.getMessage());
        }
    }
}
