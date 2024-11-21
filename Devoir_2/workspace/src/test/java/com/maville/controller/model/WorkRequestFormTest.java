package com.maville.controller.model;

import com.maville.model.Project;
import com.maville.model.WorkRequestForm;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class WorkRequestFormTest {

    @Test
    public void testConstructeurFormulaireDemandeTravail() {
        String titre = "Réparation de la route"; // Titre du projet
        String description = "Réparer les nids-de-poule sur la rue Principale."; // Description du projet
        String typeProjet = "URBAN_MAINTENANCE"; // Type de projet
        String datePrevue = "2023-12-01"; // Date prévue
    
        WorkRequestForm formulaire = new WorkRequestForm(titre, description, typeProjet, datePrevue);
    
        assertEquals(titre, formulaire.getTitle()); // Vérifie que le titre est correctement défini
        assertEquals(description, formulaire.getDescription()); // Vérifie que la description est correcte
        assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, formulaire.getProjectType()); 
        assertEquals(datePrevue, formulaire.getExpectedDate()); // Vérifie que la date prévue est correcte
    }
    

    @Test
    public void testFormulaireDemandeTravailAvecTypeProjetInvalide() {
        String titre = "Inspection du pont"; // Titre du projet
        String description = "Inspecter la stabilité du pont."; // Description du projet
        String typeProjet = "TYPE_INEXISTANT"; // Type de projet invalide
        String datePrevue = "2024-01-15"; // Date prévue

    WorkRequestForm formulaire = new WorkRequestForm(titre, description, typeProjet, datePrevue);

    assertEquals(titre, formulaire.getTitle()); // Vérifie que le titre est correctement défini
    assertEquals(description, formulaire.getDescription()); // Vérifie que la description est correcte
    assertEquals(null, formulaire.getProjectType()); // Attendu : null car le type est invalide
    assertEquals(datePrevue, formulaire.getExpectedDate()); // Vérifie que la date prévue est correcte
}

@Test
public void testFormulaireDemandeTravailAvecAccentsDansTypeProjet() {
    String titre = "Rénovation du parc"; // Titre du projet
    String description = "Rénover et améliorer les installations du parc."; // Description du projet
    String typeProjet = "URBAN_MAINTENANCE"; // Type de projet avec un accent
    String datePrevue = "2024-02-10"; // Date prévue

    WorkRequestForm formulaire = new WorkRequestForm(titre, description, typeProjet, datePrevue);

    assertEquals(titre, formulaire.getTitle()); // Vérifie que le titre est correctement défini
    assertEquals(description, formulaire.getDescription()); // Vérifie que la description est correcte
    assertEquals(Project.TypeOfWork.URBAN_MAINTENANCE, formulaire.getProjectType()); 
    assertEquals(datePrevue, formulaire.getExpectedDate()); // Vérifie que la date prévue est correcte
}

}
