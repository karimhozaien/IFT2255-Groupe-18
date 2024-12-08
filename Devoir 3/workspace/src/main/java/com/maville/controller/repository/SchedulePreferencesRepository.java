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

/**
 * Dépôt pour gérer les préférences horaires des résidents.
 * Permet de sauvegarder, récupérer et vérifier les préférences horaires en fonction des soumissions de projets.
 */
public class SchedulePreferencesRepository {
    List<String> scheduleConflicts = new ArrayList<>();

    /**
     * Sauvegarde les préférences horaires dans la base de données.
     *
     * @param schedulePreferences Les préférences horaires à sauvegarder, incluant la rue, le quartier et les heures de la semaine.
     */
    public void savePreferences(SchedulePreferences schedulePreferences) {
        String insertSQL = "INSERT INTO SchedulePreferences(id, street_name, neighbourhood, week_hours) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, schedulePreferences.getId());
            pstmt.setString(2, schedulePreferences.getStreet());
            pstmt.setString(3, schedulePreferences.getNeighbourhood());
            pstmt.setString(4, schedulePreferences.getWeekHours());

            pstmt.executeUpdate();
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

    /**
     * Récupère les préférences horaires pour un quartier spécifique.
     *
     * @param neighbourhood Le code du quartier (ex. "H1X").
     * @return Une liste de préférences horaires associées au quartier, ou une liste vide si aucune préférence n'existe.
     */
    public List<SchedulePreferences> getPreferencesByNeighbourhood(String neighbourhood) {
        String querySQL = "SELECT * FROM SchedulePreferences WHERE neighbourhood = ?";
        List<SchedulePreferences> preferences = new ArrayList<>();

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            pstmt.setString(1, neighbourhood);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    preferences.add(new SchedulePreferences(
                            rs.getString("street_name"),
                            rs.getString("neighbourhood"),
                            rs.getString("week_hours")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des préférences : " + e.getMessage());
        }

        return preferences;
    }

    /**
     * Vérifie si l'horaire soumis pour un projet est compatible avec les préférences des quartiers concernés.
     *
     * @param projectSubmissionDesiredSchedule L'horaire souhaité pour le projet, sous forme de chaîne.
     * @param neighbourhoods Une liste de quartiers séparés par des virgules.
     * @return {@code true} si l'horaire soumis est compatible avec toutes les préférences, sinon {@code false}.
     */
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

    /**
     * Récupère la liste des conflits horaires identifiés après une vérification.
     *
     * @return Une liste de chaînes décrivant les conflits d'horaires.
     */
    public List<String> getScheduleConflicts() {
        return scheduleConflicts;
    }
}