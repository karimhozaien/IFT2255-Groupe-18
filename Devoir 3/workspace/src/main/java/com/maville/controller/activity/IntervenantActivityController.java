package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.SchedulePreferencesRepository;
import com.maville.controller.repository.UserRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.PostalCodeFinder;
import com.maville.model.Notification;
import com.maville.model.Project;
import com.maville.view.MenuView;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;

public class IntervenantActivityController {
    private final WorkRepository workRepository = new WorkRepository();
    private final SchedulePreferencesRepository schedulePreferencesRepository = new SchedulePreferencesRepository();

    /**
     * Permet à un intervenant de soumettre un nouveau projet.
     * Cette méthode recueille les informations du projet, vérifie leur validité,
     * et enregistre le projet dans le dépôt de projets planifiés.
     */
    public void submitProject() {
        try {
            List<String> projectInfo = MenuView.askFormInfoForProjectSubmission();
            String projectSchedule = collectValidSchedule(projectInfo.get(3)); // affectedNeighbourhoods

            Project project = new Project(
                    UUID.randomUUID().toString(),
                    projectInfo.get(0), // titre
                    projectInfo.get(1), // type de travaux
                    projectInfo.get(3), // quartiers concernés
                    projectInfo.get(4), // rues concernées
                    projectInfo.get(5), // date de début
                    projectInfo.get(2), // date de fin
                    projectSchedule
            );

            workRepository.savePlannedProject(project);

            String description = "Un nouveau projet intitulé " + projectInfo.get(0) + " a été soumis dans votre quartier";
            createNotificationForProject(projectInfo.get(3), description);
            MenuView.printMessage("Le projet a été soumis avec succès !");
        } catch (Exception e) {
            MenuView.printMessage("Une erreur est survenue : " + e.getMessage());
        }
    }

    /**
     * Permet à un intervenant de mettre à jour les informations d'un projet existant.
     * Affiche les projets planifiés, demande à l'utilisateur de sélectionner un projet,
     * et met à jour ses détails en fonction des informations fournies.
     */
    public void updateProject() {
        try {
            List<Project> plannedProjects = workRepository.getPlannedProjects();
            if (plannedProjects == null || plannedProjects.isEmpty()) {
                MenuView.printMessage("Aucun projet trouvé à mettre à jour.");
                return;
            }

            MenuView.showResults(plannedProjects);
            MenuView.printMessage("Sélectionnez un projet à mettre à jour.");
            int projectIndex = Integer.parseInt(new Scanner(System.in).nextLine()) - 1;

            if (projectIndex < 0 || projectIndex >= plannedProjects.size()) {
                MenuView.printMessage("Numéro de projet invalide.");
                return;
            }

            Project projectToUpdate = plannedProjects.get(projectIndex);
            updateProjectDetails(projectToUpdate);
        } catch (Exception e) {
            MenuView.printMessage("Erreur : " + e.getMessage());
        }
    }

    /**
     * Permet à un intervenant de consulter toutes les demandes de travaux.
     * Les demandes sont affichées à l'écran en utilisant l'interface utilisateur.
     */
    public void consultWorkRequests() {
        MenuView.showResults(workRepository.fetchWorkRequests());
    }

    // Méthodes privées

    private String collectValidSchedule(String affectedNeighbourhoods) {
        while (true) {
            String projectSchedule = MenuView.collectWeeklySchedules();
            if (schedulePreferencesRepository.checkPreferences(projectSchedule, affectedNeighbourhoods)) {
                return projectSchedule;
            }
            MenuView.printMessage("Horaire incompatible avec les préférences des résidents du quartier.");
            MenuView.printMessage("Jours en conflit :");
            schedulePreferencesRepository.getScheduleConflicts().forEach(MenuView::printMessage);
            MenuView.printMessage("Veuillez réentrer les informations.");
        }
    }

    private void createNotificationForProject(String affectedNeighbourhood, String description) {
        Notification notification = new Notification(description);
        PostalCodeFinder postalCodeFinder = new PostalCodeFinder();

        UserRepository.getInstance()
                .fetchAllResidents()
                .stream()
                .filter(user -> isNeighbourhoodAffected(affectedNeighbourhood, user[1], postalCodeFinder))
                .forEach(user -> notification.addResident(user[0]));

        NotificationRepository.getInstance().saveNotification(notification);
    }

    private boolean isNeighbourhoodAffected(String affectedNeighbourhoods, String address, PostalCodeFinder postalCodeFinder) {
        return Stream.of(affectedNeighbourhoods.split(","))
                .anyMatch(neighbourhood -> postalCodeFinder.isValidCorrespondance(neighbourhood, address));
    }

    private void updateProjectDetails(Project project) {
        List<String> updatedInfo = MenuView.askFormInfoForProjectSubmission();
        project.setTitle(updatedInfo.get(0));
        project.setTypeOfWork(Project.TypeOfWork.valueOf(updatedInfo.get(1).toUpperCase()));
        project.setEndDate(updatedInfo.get(2));
        workRepository.savePlannedProject(project);
        MenuView.printMessage("Le projet a été mis à jour avec succès !");
    }
}