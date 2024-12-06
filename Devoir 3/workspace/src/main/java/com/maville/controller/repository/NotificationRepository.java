package com.maville.controller.repository;

import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.Notification;
import com.maville.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationRepository {
    private static NotificationRepository instance;

    private NotificationRepository() {}

    public static NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

    /**
     * Enregistre une nouvelle notification dans la base de données et associe les résidents.
     *
     * @param notification La notification à enregistrer.
     */
    public void saveNotification(Notification notification) {
        String insertSQL = "INSERT INTO Notifications(id, description, residents_id, seen_residents_ids) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, notification.getId());
            pstmt.setString(2, notification.getDescription());
            pstmt.setString(3, String.join(",", notification.getResidents()));
            pstmt.setString(4, "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la notification : " + e.getMessage());
        }
    }

    /**
     * Récupère les notifications associées à un résident spécifique.
     *
     * @param residentId L'ID du résident.
     * @return Une liste de notifications associées au résident.
     */
    public List<Notification> fetchNotificationsByResidentId(String residentId) {
        List<Notification> notifications = new ArrayList<>();
        String selectSQL = "SELECT * FROM Notifications WHERE residents_id LIKE ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, "%" + residentId + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String description = rs.getString("description");
                    String residentsIds = rs.getString("residents_id");
                    String seenResidentsIds = rs.getString("seen_residents_ids");

                    Notification notification = new Notification();
                    notification.setId(id);
                    notification.setDescription(description);
                    if (residentsIds != null && !residentsIds.isEmpty()) {
                        notification.setResidents(Arrays.asList(residentsIds.split(",")));
                    }
                    if (seenResidentsIds != null && !seenResidentsIds.isEmpty()) {
                        notification.setSeenResidents(Arrays.asList(seenResidentsIds.split(",")));
                    }

                    notifications.add(notification);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des notifications : " + e.getMessage());
        }

        return notifications;
    }

    /**
     * Marque une notification comme vue pour un résident spécifique.
     *
     * @param notificationId L'ID de la notification.
     * @param residentId     L'ID du résident.
     */
    public void markNotificationAsSeen(String notificationId, String residentId) {
        String selectSQL = "SELECT seen_residents_ids FROM Notifications WHERE id = ?";
        String updateSQL = "UPDATE Notifications SET seen_residents_ids = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(selectSQL);
             PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL)) {

            // Récupérer les seen_residents_ids actuels
            pstmtSelect.setString(1, notificationId);
            String seenResidentsIds = "";
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                if (rs.next()) {
                    seenResidentsIds = rs.getString("seen_residents_ids");
                }
            }

            List<String> seenResidents = new ArrayList<>();
            if (seenResidentsIds != null && !seenResidentsIds.isEmpty()) {
                seenResidents = new ArrayList<>(Arrays.asList(seenResidentsIds.split(",")));
            }

            // Ajouter le residentId s'il n'est pas déjà présent
            if (!seenResidents.contains(residentId)) {
                seenResidents.add(residentId);
            }

            // Mettre à jour la colonne seen_residents_ids
            String updatedSeenResidentsIds = String.join(",", seenResidents);
            pstmtUpdate.setString(1, updatedSeenResidentsIds);
            pstmtUpdate.setString(2, notificationId);
            pstmtUpdate.executeUpdate();

            System.out.println("La notification a été marquée comme vue.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du flag 'vu' : " + e.getMessage());
        }
    }
}
