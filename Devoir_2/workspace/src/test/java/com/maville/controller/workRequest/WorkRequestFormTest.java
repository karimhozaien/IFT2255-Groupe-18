package com.maville.controller.workRequest;

import com.maville.model.Project;
import com.maville.model.WorkRequestForm;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class WorkRequestFormTest {

    @Test
    public void testWorkRequestFormConstructor() {
        // Arrange
        String title = "Road Repair";
        String description = "Fix potholes on Main St.";
        String projectType = "Road";
        String expectedDate = "2023-12-01";

        // Act
        WorkRequestForm workRequestForm = new WorkRequestForm(title, description, projectType, expectedDate);

        // Assert
        assertEquals(title, workRequestForm.getTitle());
        assertEquals(description, workRequestForm.getDescription());
        assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, workRequestForm.getProjectType());
        assertEquals(expectedDate, workRequestForm.getExpectedDate());
    }

    @Test
    public void testParseProjectType() {
        // Arrange
        String projectType = "Street Maintenance";

        // Act
        WorkRequestForm workRequestForm = new WorkRequestForm("Test Title", "Test Description", projectType, "2023-12-01");

        // Assert
        assertNotNull(workRequestForm.getProjectType());
        assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, workRequestForm.getProjectType());
    }

    @Test
    public void testParseProjectTypeWithAccents() {
        // Arrange
        String projectTypeWithAccents = "Rénovation de Route";

        // Act
        WorkRequestForm workRequestForm = new WorkRequestForm("Test Title", "Test Description", projectTypeWithAccents, "2023-12-01");

        // Assert
        assertNotNull(workRequestForm.getProjectType());
        assertEquals(Project.TypeOfWork.ROAD, workRequestForm.getProjectType());
    }
}