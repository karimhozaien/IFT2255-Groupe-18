package com.maville.controller.activity;

import com.maville.controller.repository.WorkRepository;
import com.maville.model.Project;
import com.maville.model.WorkRequestForm;
import com.maville.view.MenuView;

import java.util.List;
import java.util.Scanner;

public class IntervenantActivityController {
    private final WorkRepository workRepository = new WorkRepository();

    public void submitProject() {
        try {
            // Obtenir toutes les informations du projet depuis le formulaire
            List<String> projectInfo = MenuView.askFormInfoForProjectSubmission();

            // Valider les informations récupérées
            if (projectInfo == null || projectInfo.size() < 7) {
                throw new IllegalArgumentException("Les informations du projet sont incomplètes.");
            }

            // Extraire les détails du formulaire
            String title = projectInfo.get(0);  // Titre
            String description = projectInfo.get(1);  // Description
            String typeOfWork = projectInfo.get(2);  // Type de travaux
            String endDate = projectInfo.get(3);  // Date de fin
            String affectedNeighbourhood = projectInfo.get(4);  // Arrondissement
            String affectedStreets = projectInfo.get(5);  // Rues
            String startDate = projectInfo.get(6);  // Date de début

            // Valider les champs (des validations supplémentaires peuvent être ajoutées ici)
            if (title.isEmpty() || description.isEmpty() || typeOfWork.isEmpty() || endDate.isEmpty()
                    || affectedNeighbourhood.isEmpty() || affectedStreets.isEmpty() || startDate.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            // Convertir le type de travaux en enum en utilisant la méthode getTypeOfWork du projet
            Project.TypeOfWork workType = Project.getTypeOfWork(typeOfWork);

            // Créer un nouvel objet Projet avec les informations collectées
            Project project = new Project(
                    generateProjectId(), // ID unique du projet
                    title,
                    workType,
                    affectedNeighbourhood,
                    affectedStreets,
                    startDate,
                    endDate,
                    List.of("9:00-17:00"), // Horaires de travail par défaut (peut être personnalisé)
                    Project.WorkStatus.PLANNED // Statut par défaut du projet
            );

            // Sauvegarder le projet dans le dépôt (base de données ou stockage des données)
            workRepository.savePlannedProject(project);

            // Afficher un message de succès
            MenuView.printMessage("Le projet a été soumis avec succès !");
        } catch (IllegalArgumentException e) {
            // Gérer les erreurs de validation (comme les champs vides ou les données invalides)
            MenuView.printMessage("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            // Gérer les erreurs générales (comme les exceptions inattendues)
            MenuView.printMessage("Une erreur est survenue : " + e.getMessage());
        }
    }

    public void updateProject() {
        try {
            // Récupérer tous les projets planifiés
            List<Project> plannedProjects = workRepository.getPlannedProjects();
            if (plannedProjects == null || plannedProjects.isEmpty()) {
                // Si aucun projet n'est trouvé, afficher un message
                MenuView.printMessage("Aucun projet trouvé à mettre à jour.");
                return;
            }

            // Afficher les projets existants et demander à l'utilisateur de choisir celui qu'il souhaite mettre à jour
            MenuView.showResults(plannedProjects);
            MenuView.printMessage("Sélectionnez un projet à mettre à jour.");
            int projectIndex = Integer.parseInt(new Scanner(System.in).nextLine()) - 1;

            // Vérifier si l'index du projet est valide
            if (projectIndex < 0 || projectIndex >= plannedProjects.size()) {
                MenuView.printMessage("Numéro de projet invalide.");
                return;
            }

            // Récupérer le projet à mettre à jour
            Project projectToUpdate = plannedProjects.get(projectIndex);

            // Demander les informations mises à jour pour le projet en utilisant le formulaire
            List<String> updatedInfo = MenuView.askFormInfoForProjectSubmission();

            // Mettre à jour les informations du projet
            projectToUpdate.setTitle(updatedInfo.get(0));  // Titre mis à jour
            projectToUpdate.setTypeOfWork(Project.TypeOfWork.valueOf(updatedInfo.get(2).toUpperCase()));  // Type de travail mis à jour
            projectToUpdate.setEndDate(updatedInfo.get(3));  // Date de fin mise à jour

            // Sauvegarder le projet mis à jour dans le dépôt
            workRepository.savePlannedProject(projectToUpdate);
            MenuView.printMessage("Le projet a été mis à jour avec succès !");
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si l'utilisateur entre une valeur invalide pour l'index
            MenuView.printMessage("Veuillez entrer un numéro de projet valide.");
        } catch (IllegalArgumentException e) {
            // Afficher un message d'erreur si une valeur invalide a été saisie
            MenuView.printMessage("Valeur invalide saisie : " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            // Afficher un message d'erreur si l'index du projet est hors limites
            MenuView.printMessage("Index du projet en dehors des limites.");
        }
    }

    public void consultWorkRequests() {
        // TODO
        WorkRepository workRepository = new WorkRepository();
        List<WorkRequestForm> workRequests= workRepository.fetchWorkRequests();
        MenuView.showResults(workRequests);
    }

    private static int projectIdCounter = 1;  // Compteur statique pour générer des IDs uniques

    // Méthode pour générer un ID unique pour le projet
    private String generateProjectId() {
        return String.valueOf(projectIdCounter++);  // Retourner un ID unique sous forme d'entier
    }
}
