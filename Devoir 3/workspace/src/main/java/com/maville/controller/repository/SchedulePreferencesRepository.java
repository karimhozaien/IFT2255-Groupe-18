package com.maville.controller.repository;

import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.SchedulePreferences;
import com.maville.view.MenuView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulePreferencesRepository {
    List<String> scheduleConflicts = new ArrayList<>();

    public void savePreferences(SchedulePreferences schedulePreferences) {
        String insertSQL = "INSERT INTO SchedulePreferences(street_name, neighbourhood, week_hours) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, schedulePreferences.getStreet());
            pstmt.setString(2, schedulePreferences.getNeighbourhood());
            pstmt.setString(3, schedulePreferences.getWeekHours());

            pstmt.executeUpdate();
            //System.out.println("La requête a été sauvegardée."); // Message helper
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la requête : " + e.getMessage());
        }
    }

    private String fetchPreferences(String neighbourhood) {
        String selectSQL = "SELECT * FROM SchedulePreferences WHERE neighbourhood = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, neighbourhood);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("week_hours");
                } else {
                    // Il n'y a pas de préférences dans ce quartier
                    System.out.println("Aucune préférence trouvée dans ce quartier : " + neighbourhood);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            return null;
        }
    }

    public boolean checkPreferences(String projectSubmissionDesiredSchedule, String neighbourhoods) {
        scheduleConflicts.clear(); // Réinitialiser les conflits avant chaque vérification
        boolean hasConflicts = false;

        for (String neighbourhood : neighbourhoods.split(",")) {
            String preferences = fetchPreferences(neighbourhood);

            if (preferences != null) {
                String[] preferenceHours = preferences.split(",");
                String[] submissionHours = projectSubmissionDesiredSchedule.split(",");

                for (int i = 0; i < Math.min(preferenceHours.length, submissionHours.length); i++) {
                    if (!isScheduleCompatible(preferenceHours[i], submissionHours[i])) {
                        hasConflicts = true;
                        scheduleConflicts.add(getDayName(i) +
                                " (Préférence : " + preferenceHours[i] +
                                ", Soumission : " + submissionHours[i] + ")");
                    }
                }
            }
        }

        return !hasConflicts; // Retourner true si aucun conflit, false sinon
    }

    private boolean isScheduleCompatible(String preference, String submission) {
        if (preference.equals("N/A") || submission.equals("N/A")) {
            return true;
        }

        String[] prefRange = preference.split("-");
        String[] subRange = submission.split("-");

        if (prefRange.length == 2 && subRange.length == 2) {
            int prefStart = parseTime(prefRange[0]);
            int prefEnd = parseTime(prefRange[1]);
            int subStart = parseTime(subRange[0]);
            int subEnd = parseTime(subRange[1]);

            // Vérifier si les plages horaires ne se chevauchent PAS
            return subStart >= prefStart && subEnd <= prefEnd; // Conflit détecté
        }

        // Si les formats ne sont pas valides, considérer comme conflit
        return false;
    }

    private int parseTime(String time) {
        String[] parts = time.split(":");
        if (parts.length == 2) {
            return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        }
        throw new IllegalArgumentException("Format d'heure invalide : " + time);
    }

    private String getDayName(int dayIndex) {
        String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};
        return days[dayIndex];
    }

    public List<String> getScheduleConflicts() {
        return scheduleConflicts;
    }
}