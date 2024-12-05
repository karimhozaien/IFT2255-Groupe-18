package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.UserRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.PostalCodeFinder;
import com.maville.model.Notification;
import com.maville.model.Project;
import com.maville.model.WorkRequestForm;
import com.maville.view.MenuView;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class IntervenantActivityController {
    private final WorkRepository workRepository = new WorkRepository();

    public void submitProject() {
        try {
            List<String> projectInfo = MenuView.askFormInfoForProjectSubmission();

            // Valider les informations récupérées
            /*if (projectInfo == null || projectInfo.size() < 7) {
                throw new IllegalArgumentException("Les informations du projet sont incomplètes.");
            }*/

            // Extraire les détails du formulaire
            String title = projectInfo.get(0);
            String typeOfWork = projectInfo.get(1);
            String endDate = projectInfo.get(2);
            String affectedNeighbourhood = projectInfo.get(3);
            String affectedStreets = projectInfo.get(4);
            String startDate = projectInfo.get(5);
            String workSchedule = projectInfo.get(6);

            // Valider les champs (des validations supplémentaires peuvent être ajoutées ici)
            /*if (title.isEmpty() || description.isEmpty() || typeOfWork.isEmpty() || endDate.isEmpty()
                    || affectedNeighbourhood.isEmpty() || affectedStreets.isEmpty() || startDate.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }*/

            Project project = new Project(
                    UUID.randomUUID().toString(),
                    title,
                    Project.getTypeOfWork(typeOfWork),
                    affectedNeighbourhood,
                    affectedStreets,
                    startDate,
                    endDate,
                    workSchedule, // Horaires de travail par défaut (peut être personnalisé)
                    Project.WorkStatus.PLANNED
            );

            workRepository.savePlannedProject(project);

            createNotificationForProject(affectedNeighbourhood, "Un nouveau projet intitulé " + title + " a soumis dans votre quartier");

            MenuView.printMessage("Le projet a été soumis avec succès !");
        } catch (IllegalArgumentException e) {
            MenuView.printMessage("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            // Gérer les erreurs générales (comme les exceptions inattendues)
            MenuView.printMessage("Une erreur est survenue : " + e.getMessage());
        }
    }

    private void createNotificationForProject(String affectedNeighbourhood, String description) {
        Notification notification = new Notification(description);
        PostalCodeFinder postalCodeFinder = new PostalCodeFinder();

        for (String[] usersInfo : UserRepository.getInstance().fetchAllResidents()) {
            String currentUserResidentialAddress = usersInfo[1];
            System.out.println("Adresse résidentielle : " + currentUserResidentialAddress); // helper
            for (String neighbourhood : affectedNeighbourhood.split(",")) {
                boolean isEqual = postalCodeFinder.isValidCorrespondance(neighbourhood, currentUserResidentialAddress);
                if (isEqual) {
                    notification.addResident(usersInfo[0]); // on ajoute le resident a la liste
                }
            }
        }

        NotificationRepository.getInstance().saveNotification(notification); // ajouter la notification a la DB
    }

    public void updateProject() {
        try {
            List<Project> plannedProjects = workRepository.getPlannedProjects();
            if (plannedProjects == null || plannedProjects.isEmpty()) {
                MenuView.printMessage("Aucun projet trouvé à mettre à jour.");
                return;
            }

            // Afficher les projets existants et demander à l'utilisateur de choisir celui qu'il souhaite mettre à jour
            MenuView.showResults(plannedProjects);
            MenuView.printMessage("Sélectionnez un projet à mettre à jour.");
            int projectIndex = Integer.parseInt(new Scanner(System.in).nextLine()) - 1;

            if (projectIndex < 0 || projectIndex >= plannedProjects.size()) {    // Vérifier si l'index du projet est valide
                MenuView.printMessage("Numéro de projet invalide.");
                return;
            }

            Project projectToUpdate = plannedProjects.get(projectIndex);             // Récupérer le projet à mettre à jour

            // Demander les informations mises à jour pour le projet en utilisant le formulaire
            List<String> updatedInfo = MenuView.askFormInfoForProjectSubmission();

            // Mettre à jour les informations du projet
            projectToUpdate.setTitle(updatedInfo.get(0));
            projectToUpdate.setTypeOfWork(Project.TypeOfWork.valueOf(updatedInfo.get(2).toUpperCase()));
            projectToUpdate.setEndDate(updatedInfo.get(3));

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
        WorkRepository workRepository = new WorkRepository();
        List<WorkRequestForm> workRequests= workRepository.fetchWorkRequests();
        MenuView.showResults(workRequests);
    }
}
