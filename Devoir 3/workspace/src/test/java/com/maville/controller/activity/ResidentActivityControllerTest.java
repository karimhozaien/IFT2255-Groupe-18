package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.SchedulePreferencesRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.Notification;
import com.maville.model.SchedulePreferences;
import com.maville.model.WorkRequestForm;
import com.maville.model.Project;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ResidentActivityControllerTest {
    // Dépôt pour gérer les travaux
    private WorkRepository workRepo;
    // Dépôt pour gérer les notifications
    private NotificationRepository notificationRepo;
    // Dépôt pour gérer les préférences de planification
    private SchedulePreferencesRepository preferencesRepo;

    @Before
    public void setUp() {
        // Initialisation des dépôts avant chaque test
        workRepo = new WorkRepository();
        notificationRepo = NotificationRepository.getInstance();
        preferencesRepo = new SchedulePreferencesRepository();
    }

    @Test
    public void testConsultWorks() throws IOException {
        // Création d'un projet fictif pour les tests
        Project project = new Project(
                "Test Work",
                "Description",
                Project.TypeOfWork.ROAD.toString(),
                "H1X",
                "Test Street",
                "2024-12-01",
                "2025-01-01",
                "08:00-16:00,N/A,N/A,N/A,N/A,N/A,N/A"
        );
        // Sauvegarde du projet dans le dépôt
        workRepo.savePlannedProject(project);
        String projectId = project.getId();

        // Récupération de tous les projets
        List<Project> projects = workRepo.getAllProjects();

        // Vérification que le projet existe bien dans la liste
        assertNotNull(projects);
        assertTrue(projects.stream().anyMatch(p -> p.getId().equals(projectId)));

        // Suppression du projet après le test
        deleteTestProject(projectId);
    }

    @Test
    public void testSubmitWorkRequest() {
        // Création d'une requête de travail fictive
        WorkRequestForm workRequest = new WorkRequestForm(
                "Fix Pothole",
                "Repair the pothole on Test Street",
                Project.TypeOfWork.ROAD.toString(),
                "2024-12-15"
        );

        // Sauvegarde de la requête dans le dépôt
        workRepo.saveWorkRequest(workRequest);

        // Récupération de toutes les requêtes de travail
        List<WorkRequestForm> requests = workRepo.fetchWorkRequests();
        // Vérification que la requête a bien été sauvegardée
        assertNotNull(requests);
        assertTrue(requests.stream().anyMatch(r -> r.getTitle().equals("Fix Pothole")));

        // Suppression de la requête après le test
        deleteTestWorkRequest(workRequest.getId());
    }

    @Test
    public void testParticipateToSchedule() {
        // Création de préférences de planification fictives
        SchedulePreferences preferences = new SchedulePreferences(
                "Test Street",
                "H1X",
                "08:00-12:00,14:00-16:00,N/A,N/A,N/A,N/A,N/A"
        );

        // Sauvegarde des préférences
        preferencesRepo.savePreferences(preferences);

        // Récupération des préférences par quartier
        List<SchedulePreferences> savedPreferences = preferencesRepo.getPreferencesByNeighbourhood("H1X");
        // Vérification que les préférences ont bien été sauvegardées
        assertNotNull(savedPreferences);
        assertTrue(savedPreferences.stream().anyMatch(p -> p.getStreet().equals("Test Street")));

        // Suppression des préférences après le test
        deleteTestPreferences(preferences.getId());
    }

    @Test
    public void testConsultNotifications() {
        String userId = "testUser";
        // Création d'une notification fictive
        Notification notification = new Notification("Test Notification");
        notification.addResident(userId);
        // Sauvegarde de la notification
        notificationRepo.saveNotification(notification);

        // Récupération des notifications par utilisateur
        List<Notification> notifications = notificationRepo.fetchNotificationsByResidentId(userId);
        // Vérification que la notification a bien été sauvegardée
        assertNotNull(notifications);
        assertTrue(notifications.stream().anyMatch(n -> n.getDescription().equals("Test Notification")));

        // Suppression de la notification après le test
        deleteTestNotification(notification.getId());
    }

    private void deleteTestProject(String projectId) {
        // Suppression d'un projet de test par son ID
        String deleteSQL = "DELETE FROM Projects WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, projectId);
            pstmt.executeUpdate();
            System.out.println("Deleted project: " + projectId);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du projet : " + e.getMessage());
        }
    }

    private void deleteTestPreferences(String preferencesId) {
        // Suppression de préférences de test par leur ID
        String deleteSQL = "DELETE FROM SchedulePreferences WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, preferencesId);
            pstmt.executeUpdate();
            System.out.println("Deleted schedule preferences: " + preferencesId);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des préférences : " + e.getMessage());
        }
    }

    private void deleteTestNotification(String notificationId) {
        // Suppression d'une notification de test par son ID
        String deleteSQL = "DELETE FROM Notifications WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, notificationId);
            pstmt.executeUpdate();
            System.out.println("Deleted notification: " + notificationId);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la notification : " + e.getMessage());
        }
    }

    private void deleteTestWorkRequest(String workRequestId) {
        // Suppression d'une requête de travail de test par son ID
        String deleteSQL = "DELETE FROM WorkRequests WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, workRequestId);
            pstmt.executeUpdate();
            System.out.println("Deleted work request: " + workRequestId);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la demande de travail : " + e.getMessage());
        }
    }
}