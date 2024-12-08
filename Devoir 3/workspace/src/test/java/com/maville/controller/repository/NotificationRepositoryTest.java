package com.maville.controller.repository;

import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.Notification;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class NotificationRepositoryTest {

    // Référence à l'instance unique de NotificationRepository utilisée pour les tests
    NotificationRepository notificationRepo = NotificationRepository.getInstance();

    @Before
    public void cleanUp() {
        // Nettoie les notifications de test de la base de données avant chaque test
        deleteTestNotifications();
    }

    @Test
    public void testMarkNotificationAsSeen() {
        // Arrange : Création d'une notification de test
        String notificationId = UUID.randomUUID().toString();
        String residentId = "resident1";

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setDescription("Nouvelle notification");
        notification.setResidents(List.of(residentId));
        notificationRepo.saveNotification(notification);

        // Act : Marquer la notification comme vue
        notificationRepo.markNotificationAsSeen(notificationId, residentId);

        // Assert : Vérifie que la notification est correctement marquée comme vue
        List<Notification> notifications = notificationRepo.fetchNotificationsByResidentId(residentId);
        assertNotNull(notifications);
        assertEquals(1, notifications.size());
        assertTrue("La notification devrait être marquée comme vue",
                notifications.getFirst().getSeenResidents().contains(residentId));
    }

    @Test
    public void testMarkMultipleNotificationsAsSeen() {
        // Arrange : Création de plusieurs notifications de test
        String residentId = "resident2";

        Notification notif1 = new Notification();
        notif1.setId(UUID.randomUUID().toString());
        notif1.setDescription("Première notification");
        notif1.setResidents(List.of(residentId));

        Notification notif2 = new Notification();
        notif2.setId(UUID.randomUUID().toString());
        notif2.setDescription("Deuxième notification");
        notif2.setResidents(List.of(residentId));

        notificationRepo.saveNotification(notif1);
        notificationRepo.saveNotification(notif2);

        // Act : Marquer les notifications comme vues
        notificationRepo.markNotificationAsSeen(notif1.getId(), residentId);
        notificationRepo.markNotificationAsSeen(notif2.getId(), residentId);

        // Assert : Vérifie que toutes les notifications sont marquées comme vues
        List<Notification> notifications = notificationRepo.fetchNotificationsByResidentId(residentId);
        assertNotNull(notifications);
        assertEquals(2, notifications.size());

        for (Notification notif : notifications) {
            assertTrue("Toutes les notifications devraient être marquées comme vues",
                    notif.getSeenResidents().contains(residentId));
        }
    }

    @Test
    public void testMarkNotificationForMultipleResidents() {
        // Arrange : Création d'une notification partagée entre plusieurs résidents
        String notificationId = UUID.randomUUID().toString();
        String resident1 = "residentA";
        String resident2 = "residentB";

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setDescription("Notification pour plusieurs résidents");
        notification.setResidents(List.of(resident1, resident2));
        notificationRepo.saveNotification(notification);

        // Act : Chaque résident marque la notification comme vue
        notificationRepo.markNotificationAsSeen(notificationId, resident1);
        notificationRepo.markNotificationAsSeen(notificationId, resident2);

        // Assert : Vérifie que la notification est marquée comme vue pour les deux résidents
        List<Notification> notificationsResident1 = notificationRepo.fetchNotificationsByResidentId(resident1);
        List<Notification> notificationsResident2 = notificationRepo.fetchNotificationsByResidentId(resident2);

        assertNotNull(notificationsResident1);
        assertNotNull(notificationsResident2);

        assertEquals(1, notificationsResident1.size());
        assertEquals(1, notificationsResident2.size());

        assertTrue("La notification devrait être marquée comme vue par le résident 1",
                notificationsResident1.getFirst().getSeenResidents().contains(resident1));
        assertTrue("La notification devrait être marquée comme vue par le résident 2",
                notificationsResident2.getFirst().getSeenResidents().contains(resident2));
    }

    private void deleteTestNotifications() {
        // Supprime toutes les notifications de test de la base de données
        String deleteSQL = "DELETE FROM Notifications";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("Deleted notifications: " + rowsDeleted);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des notifications : " + e.getMessage());
        }
    }
}