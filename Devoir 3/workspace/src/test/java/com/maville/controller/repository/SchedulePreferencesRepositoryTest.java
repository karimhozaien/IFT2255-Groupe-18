package com.maville.controller.repository;

import com.maville.model.SchedulePreferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SchedulePreferencesRepositoryTest {

    @Test
    public void checkPreferencesNoConflictTest() {
        SchedulePreferencesRepository scheduleRepo = new SchedulePreferencesRepository();

        // Ajouter des préférences pour le quartier "H2X"
        scheduleRepo.savePreferences(new SchedulePreferences("Saint-Denis", "H2X", "08:00-16:00,08:00-16:00,N/A,N/A,N/A,N/A,N/A"));

        // Horaire de projet correspondant aux préférences définies
        String projectSchedule = "08:00-16:00,08:00-16:00,N/A,N/A,N/A,N/A,N/A";
        String neighbourhoods = "H2X"; // Quartier de Montréal

        // Vérifier que l'horaire est compatible avec les préférences
        boolean result = scheduleRepo.checkPreferences(projectSchedule, neighbourhoods);

        // L'horaire devrait être compatible
        assertTrue(result, "Les préférences devraient être compatibles");
    }

    @Test
    public void checkPreferencesWithConflictTest() {
        SchedulePreferencesRepository scheduleRepo = new SchedulePreferencesRepository();

        // Ajouter des préférences pour le quartier "H3A"
        scheduleRepo.savePreferences(new SchedulePreferences("Mont-Royal", "H3A", "08:00-16:00,08:00-16:00,08:00-10:00,N/A,N/A,N/A,N/A"));

        // Horaire de projet en conflit avec les préférences définies
        String projectSchedule = "08:00-16:00,08:00-16:00,07:00-10:00,N/A,N/A,N/A,N/A";
        String neighbourhoods = "H3A"; // Quartier de Montréal

        // Vérifier qu'un conflit est détecté
        boolean result = scheduleRepo.checkPreferences(projectSchedule, neighbourhoods);

        // Les préférences ne devraient pas être compatibles
        assertFalse(result, "Les préférences ne devraient pas être compatibles");
        assertFalse(scheduleRepo.getScheduleConflicts().isEmpty(), "Il devrait y avoir au moins un conflit");
    }

    @Test
    public void checkPreferencesNoNeighbourhoodPreferencesTest() {
        SchedulePreferencesRepository scheduleRepo = new SchedulePreferencesRepository();

        // Horaire de projet
        String projectSchedule = "08:00-16:00,N/A,N/A,N/A,N/A,N/A,N/A";
        String neighbourhoods = "H1X"; // Quartier sans préférences définies

        // Vérifier que l'horaire est compatible par défaut
        boolean result = scheduleRepo.checkPreferences(projectSchedule, neighbourhoods);

        // Si aucune préférence n'existe, l'horaire est considéré comme compatible
        assertTrue(result, "Si aucune préférence n'existe, l'horaire est compatible par défaut");
    }
}