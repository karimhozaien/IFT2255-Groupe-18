package com.maville.controller.repository;

import com.maville.model.Project;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WorkRepositoryTest {
    @Test
    public void savePlannedProjectsTest() {
        WorkRepository workRepository = new WorkRepository();

        // Fausse donnée
        List<String> workSchedule = new ArrayList<>();
        workSchedule.add("[8:00-16:00");
        workSchedule.add(" N/A-N/A");
        workSchedule.add(" N/A-N/A");
        workSchedule.add(" N/A-N/A");
        workSchedule.add(" N/A-N/A");
        workSchedule.add(" N/A-N/A");
        workSchedule.add(" N/A-N/A]");
        Project project = new Project(
                "1",
                "Changement égout sur Édouard-Montpetit",
                Project.TypeOfWork.UNDERGROUND,
                "Côtes-des-neiges",
                "Édouard-Montpetit",
                "10-11-2024",
                "07-04-2024",
                workSchedule
        );

        workRepository.savePlannedProject(project);
        List<Project> plannedProjects = workRepository.fetchPlannedProjects();

        assertEquals(plannedProjects.getFirst().toString(), project.toString());
    }
}
