package com.maville.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class WorkRequestFormTest {

    @Test
    public void WorkRequestFormConstructorTest() {
        String title = "Réparation routière";
        String description = "Réparer les nids de poule sur Main St.";
        String projectType = "Road";
        String expectedDate = "2023-12-01";

        WorkRequestForm workRequestForm = new WorkRequestForm(title, description, projectType, expectedDate);

        assertEquals(title, workRequestForm.getTitle()); // Vérifie le titre
        assertEquals(description, workRequestForm.getDescription()); // Vérifie la description
        assertEquals(expectedDate, workRequestForm.getExpectedDate()); // Vérifie la date attendue
    }

    @Test
    public void parseProjectTypeTest() {
        String projectType = "Street Maintenance";

        WorkRequestForm workRequestForm = new WorkRequestForm("Test Titre", "Test Description", projectType, "2023-12-01");

        assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, workRequestForm.getProjectType()); // Vérifie la correspondance avec l'enum
    }

    @Test
    public void parseProjectTypeWithAccentsTest() {
        String projectTypeWithAccents = "Rénovation de Route";

        WorkRequestForm workRequestForm = new WorkRequestForm("Test Titre", "Test Description", projectTypeWithAccents, "2023-12-01");

        assertEquals(Project.TypeOfWork.CONSTRUCTION_RENOVATION, workRequestForm.getProjectType()); // Vérifie la correspondance avec l'enum
    }
}