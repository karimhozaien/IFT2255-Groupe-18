package com.maville.controller.services;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import com.maville.controller.repository.WorkRepository.Result.Record;
import com.maville.model.Project;

public class ParsingTest {

    @Test
    public void testInitializeParsingProjects() {

        List<Record> records = new ArrayList<>();

        Record record1 = new Record("1", "Construction", "Quartier A", "Rue 1", "2023-11-01T00:00:00", "2023-12-01T00:00:00", "Horaire 1", "EN COURS");
        Record record2 = new Record("2", "Rénovation", "Quartier B", "Rue 2", "2023-12-01T00:00:00", "2024-01-01T00:00:00", "Horaire 2", "PLANIFIÉ");

        records.add(record1);
        records.add(record2);

        Parser<Project> parser = new Parser<>(records);


        List<Project> projects = parser.initializeParsing("works", Project.class);


        assertNotNull("La liste des projets ne doit pas être nulle", projects);
        assertEquals("La liste des projets doit contenir 2 éléments", 2, projects.size());
        assertEquals("Le titre du premier projet doit correspondre", "Construction direction Rue 1", projects.get(0).getTitle());
        assertEquals("Le titre du deuxième projet doit correspondre", "Rénovation direction Rue 2", projects.get(1).getTitle());
    }
}