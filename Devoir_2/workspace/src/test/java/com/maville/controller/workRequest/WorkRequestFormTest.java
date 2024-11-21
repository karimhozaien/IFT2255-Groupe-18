package com.maville.controller.workRequest;

import com.maville.model.Project;
import com.maville.model.WorkRequestForm;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class WorkRequestFormTest {

    @Test
    public void testWorkRequestFormConstructor() {
        // Arrange
        String title = "Road Repair";
        String description = "Fix potholes on Main St.";
        String projectType = "URBAN_MAINTENANCE";
        String expectedDate = "2023-12-01";

        // Act
        WorkRequestForm workRequestForm = new WorkRequestForm(title, description, projectType, expectedDate);

        // Assert
        assertEquals(title, workRequestForm.getTitle());
        assertEquals(description, workRequestForm.getDescription());
        assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, workRequestForm.getProjectType());
        assertEquals(expectedDate, workRequestForm.getExpectedDate());
    }

}