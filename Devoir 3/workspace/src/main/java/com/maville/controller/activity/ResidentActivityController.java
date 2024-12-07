package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.SchedulePreferencesRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.Authenticate;
import com.maville.model.Notification;
import com.maville.model.SchedulePreferences;
import com.maville.model.WorkRequestForm;
import com.maville.view.MenuView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur principal pour les activités des résidents.
 * Permet de gérer et d'afficher des informations liées aux travaux, entraves,
 * préférences de planning, notifications, et requêtes.
 */
public class ResidentActivityController {
    private final Scanner scanner;
    private final WorkRepository workRepo;

    public ResidentActivityController() {
        scanner = new Scanner(System.in);
        workRepo = new WorkRepository();
    }

    /**
     * Affiche les travaux en cours et planifiés, avec la possibilité de filtrer
     * par quartier ou type de travaux.
     * Demande à l'utilisateur de sélectionner un filtre et affiche les résultats correspondants.
     */
    public void consultWorks() {
        try {
            MenuView.askFilter("Quartier", "Type de travaux", "Autre");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    MenuView.printMessage("Entrez le nom du quartier : ");
                    String name = scanner.next();
                    MenuView.showResults(workRepo.getFilteredProjects("quartier", name));
                    break;
                case 2:
                    MenuView.printMessage("Entrez le type de travaux : ");
                    String type = scanner.next();
                    MenuView.showResults(workRepo.getFilteredProjects("travail", type));
                    break;
                case 3:
                    MenuView.showResults(workRepo.getOngoingProjects());
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche les entraves routières en cours et planifiées, avec la possibilité de filtrer
     * par rue ou type de travaux.
     * Demande à l'utilisateur de sélectionner un filtre et affiche les résultats correspondants.
     */
    public void consultRoadObstructions() {
        MenuView.askFilter("Rue", "Type de travaux", "Autre");
        try {
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    MenuView.printMessage("Entrez le nom de la rue : ");
                    String name = scanner.nextLine();
                    MenuView.showResults(workRepo.getFilteredRoadObstructions("rue", name));
                    break;
                case 2:
                    MenuView.printMessage("Entrez le type de travaux : ");
                    String type = scanner.nextLine();
                    List<String> filteredRoadObstructions = workRepo.getFilteredRoadObstructions("travail", type);
                    MenuView.showResults(filteredRoadObstructions);
                    break;
                case 3:
                    MenuView.showResults(workRepo.getRoadObstructions());
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recherche des travaux en fonction d'un terme donné par l'utilisateur,
     * qui peut être un titre, un quartier ou un type de travaux.
     * Affiche les résultats correspondant au terme de recherche.
     */
    public void searchWorks() {
        MenuView.printMessage("Entrez un terme de recherche (titre, quartier ou type de travaux) :");
        try {
            String searchTerm = scanner.nextLine();  // Get the search term
            MenuView.showResults(workRepo.getFilteredProjects(searchTerm));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet au résident de partager ses préférences horaires pour les travaux.
     * Les préférences sont sauvegardées dans la base de données.
     */
    public void participateToSchedule() {
        MenuView.printMessage("Bienvenue dans les préférences horaires. Ici, vous pouvez partager une intervalle de " +
                "temps pendant laquelle vous préférez que les travaux se fassent");
        List<String> scheduleInfo = MenuView.askSchedulePreferences();
        SchedulePreferences schedulePreferences = new SchedulePreferences(
                scheduleInfo.get(0),
                scheduleInfo.get(1),
                scheduleInfo.get(2)
        );
        SchedulePreferencesRepository preferencesRepository = new SchedulePreferencesRepository();
        preferencesRepository.savePreferences(schedulePreferences);
    }

    /**
     * Soumet une requête de travaux à partir d'informations fournies par l'utilisateur.
     * Les détails sont sauvegardés dans la base de données.
     */
    public void submitWorkRequest() {
        List<String> workRequestInfo = MenuView.askFormInfo();
        WorkRequestForm workRequestForm = new WorkRequestForm(
                workRequestInfo.get(0),
                workRequestInfo.get(1),
                workRequestInfo.get(2),
                workRequestInfo.get(3)
        );
        WorkRepository workRepo = new WorkRepository();
        workRepo.saveWorkRequest(workRequestForm);
    }

    /**
     * Consulte et affiche toutes les notifications associées au résident actuellement authentifié.
     * Marque automatiquement les notifications comme "vues" après affichage.
     */
    public void consultNotifications() {
        String userId = Authenticate.getUserId();
        MenuView.printMessage("Current user id : " + userId); //helper

        NotificationRepository notifRepo = NotificationRepository.getInstance();
        List<Notification> notifications = notifRepo.fetchNotificationsByResidentId(userId);

        // Regarde si le current user (resident) a des notifications
        for (Notification notification : notifications) {
            boolean seen = notification.getSeenResidents().contains(userId);

            // Afficher la description avec le flag [Vue] si vu
            if (seen) {
                System.out.println("[Vue] " + notification.getDescription());
            } else {
                System.out.println(notification.getDescription());
                // Marquer comme vu après affichage
                notifRepo.markNotificationAsSeen(notification.getId(), userId);
            }
        }
    }
}
