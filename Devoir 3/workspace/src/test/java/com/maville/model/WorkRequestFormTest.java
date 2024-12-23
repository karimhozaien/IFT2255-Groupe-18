package com.maville.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WorkRequestFormTest {

    @Test
    public void WorkRequestFormConstructorTest() {
        String fakeId = java.util.UUID.randomUUID().toString();
        String title = "Réparation routière";
        String description = "Réparer les nids de poule sur Main St.";
        String projectType = "Road";
        String expectedDate = "2023-12-01";

        WorkRequestForm workRequestForm = new WorkRequestForm(fakeId, title, description, projectType, expectedDate);

        assertEquals(title, workRequestForm.getTitle()); // Vérifie le titre
        assertEquals(description, workRequestForm.getDescription()); // Vérifie la description
        assertEquals(expectedDate, workRequestForm.getExpectedDate()); // Vérifie la date attendue
    }

    @Test
    public void parseProjectTypeTest() {
        String projectType = "Street Maintenance";
        String fakeId = java.util.UUID.randomUUID().toString();

        WorkRequestForm workRequestForm = new WorkRequestForm(fakeId, "Test Titre", "Test Description", projectType, "2023-12-01");

        assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, workRequestForm.getProjectType()); // Vérifie la correspondance avec l'enum
    }

    @Test
    public void parseProjectTypeWithAccentsTest() {
        String projectTypeWithAccents = "Rénovation de Route";
        String fakeId = java.util.UUID.randomUUID().toString();

        WorkRequestForm workRequestForm = new WorkRequestForm(fakeId, "Test Titre", "Test Description", projectTypeWithAccents, "2023-12-01");

        assertEquals(Project.TypeOfWork.CONSTRUCTION_RENOVATION, workRequestForm.getProjectType()); // Vérifie la correspondance avec l'enum
    }
}