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

public class ResidentActivityController {
    final Scanner scanner;
    final WorkRepository workRepo;

    public ResidentActivityController() {
        scanner = new Scanner(System.in);
        workRepo = new WorkRepository();
    }

    public void consultWorks() {
        // TODO
        // Demander à l'utilisateur s'il veut filter par 1. Quartier, 2. Type de travaux
        // Afficher tous les travaux en cours et planné
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

    public void consultRoadObstructions() {
        // TODO
        // Demander à l'utilisateur il veut chercher par 1. Rue, 2. Type de travaux
        // Afficher toutes les entraves en cours et plannées
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

    public void searchWorks() {
        // TODO
        // Demander à l'utilisateur s'il veut chercher par 1. Titre, 2. Quartier, 3. Type de travaux
        MenuView.printMessage("Entrez un terme de recherche (titre, quartier ou type de travaux) :");
        try {
            String searchTerm = scanner.nextLine();  // Get the search term
            MenuView.showResults(workRepo.getFilteredProjects(searchTerm));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void submitWorkRequest() {
        // TODO : finir l'ajout de la requête à la DB et avant, initialiser la table
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
     * Consulte et affiche toutes les notifications associées au résident authentifié.
     */
    public void consultNotifications() {
        String userId = Authenticate.getUserId();

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
