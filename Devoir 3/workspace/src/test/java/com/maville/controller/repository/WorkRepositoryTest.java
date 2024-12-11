package com.maville.controller.repository;

import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.model.Project;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class WorkRepositoryTest {

    @Test
    public void savePlannedProjectsTest() {
        WorkRepository workRepository = new WorkRepository();

        // Crée un projet fictif avec des horaires planifiés
        String workSchedule = "[8:00-16:00,N/A-N/A,N/A-N/A,N/A-N/A,N/A-N/A,N/A-N/A,N/A-N/A]";
        Project project = new Project(
                "Changement égout sur Édouard-Montpetit",
                "N/A",
                Project.TypeOfWork.UNDERGROUND.toString(),
                "Côtes-des-neiges",
                "Édouard-Montpetit",
                "10-11-2024",
                "07-04-2024",
                workSchedule
        );

        // Sauvegarde le projet et récupère les projets planifiés
        workRepository.savePlannedProject(project);
        List<Project> plannedProjects = workRepository.fetchPlannedProjects();

        // Vérifie que la liste des projets n'est pas nulle
        assertNotNull(plannedProjects);
        // Vérifie que le premier projet récupéré correspond à celui sauvegardé
        assertEquals(plannedProjects.getLast().toString(), project.toString());

        deleteTestProject(project.getId());
    }

    @Test
    public void getFilteredProjectsTest() throws IOException {
        WorkRepository workRepository = new WorkRepository();

        // Filtre les projets avec le mot-clé "construction" dans le titre
        List<Project> filteredProjects = workRepository.getFilteredProjects("construction", "titre", null);

        // Vérifie que la liste filtrée n'est pas nulle
        assertNotNull(filteredProjects);

        // Vérifie que chaque projet récupéré contient "construction" dans son titre
        for (Project project : filteredProjects) {
            assertTrue("Le titre devrait contenir le terme 'construction'",
                    project.getTitle().toLowerCase().contains("construction"));
        }
    }

    @Test
    public void fetchPlannedProjectsTest() {
        WorkRepository workRepository = new WorkRepository();

        // Crée un projet fictif avec des horaires planifiés
        String workSchedule = "[9:00-17:00,N/A-N/A,N/A-N/A,N/A-N/A,N/A-N/A,N/A-N/A,N/A-N/A]";
        Project project = new Project(
                "Travaux d'asphaltage",
                "Remplacement d'asphalte sur une route principale",
                Project.TypeOfWork.ROAD.toString(),
                "Saint-Laurent",
                "Boulevard Décarie",
                "2024-02-01",
                "2024-03-01",
                workSchedule
        );

        // Sauvegarde le projet dans le dépôt
        workRepository.savePlannedProject(project);

        // Récupère tous les projets planifiés
        List<Project> plannedProjects = workRepository.fetchPlannedProjects();

        // Vérifie que la liste des projets n'est pas nulle et qu'elle n'est pas vide
        assertNotNull(plannedProjects);
        assertFalse("La liste des projets planifiés ne devrait pas être vide", plannedProjects.isEmpty());

        // Vérifie que le dernier projet ajouté est correctement récupéré
        Project fetchedProject = plannedProjects.get(plannedProjects.size() - 1);
        assertEquals(project.getTitle(), fetchedProject.getTitle());
        assertEquals(project.getDescription(), fetchedProject.getDescription());

        deleteTestProject(project.getId());
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
}