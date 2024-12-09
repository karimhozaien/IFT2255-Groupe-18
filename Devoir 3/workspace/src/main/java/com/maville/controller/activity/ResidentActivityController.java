package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.SchedulePreferencesRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.Authenticate;
import com.maville.controller.services.PostalCodeFinder;
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
        SchedulePreferencesRepository preferencesRepository = new SchedulePreferencesRepository();

        // Obtenir le quartier de l'utilisateur à partir de son code postal
        PostalCodeFinder postalCodeFinder = new PostalCodeFinder();
        String neighbourhood = postalCodeFinder.getPostalCode(Authenticate.getFetchedUserInfo()[2]);

        if (neighbourhood == null) {
            MenuView.printMessage("Votre quartier n'existe pas."); // si l'utilisateur n'a pas une adresse au quebec
            return;
        }

        String parseNeighbourhood = neighbourhood.split(" ")[0];

        MenuView.printMessage("Bienvenue dans les préférences horaires.");
        MenuView.printMessage("Ici, vous pouvez partager une intervalle de " +
                "vous préférez que les travaux se fassent dans votre quartier.");
        // Demande à l'utilisateur s'il veut ajouter ou modifier une plage horaire

        boolean exitLoop = false;
        while (!exitLoop) {
            MenuView.askSimpleOptions("Souhaitez-vous ajouter une nouvelle plage horaire ou modifier une plage existante ?",
                    "Quitter", "Ajouter", "Modifier");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Nettoie le tampon
            switch (choice) {
                case 0:
                    exitLoop = true;
                    break;

                case 1: // Ajouter une nouvelle plage horaire
                    List<String> scheduleInfoAdd = MenuView.askSchedulePreferences();
                    SchedulePreferences newPreferences = new SchedulePreferences(
                            scheduleInfoAdd.get(0),
                            parseNeighbourhood,
                            scheduleInfoAdd.get(1)
                    );
                    preferencesRepository.savePreferences(newPreferences);
                    MenuView.printMessage("Vos préférences ont été ajoutées avec succès !");
                    break;

                case 2: // Modifier une plage horaire existante
                    // Récupérer les préférences existantes pour ce quartier
                    List<SchedulePreferences> existingPreferences = preferencesRepository.getPreferencesByNeighbourhood(parseNeighbourhood);

                    if (existingPreferences.isEmpty()) {
                        MenuView.printMessage("Aucune préférence trouvée pour votre quartier.");
                    } else {
                        // Afficher les rues disponibles dans les préférences
                        MenuView.printMessage("Les rues disponibles dans ce quartier sont :");
                        existingPreferences.forEach(pref -> MenuView.printMessage("- " + pref.getStreet()));

                        // Demander le nom de la rue pour laquelle modifier les préférences
                        MenuView.printMessage("Entrez le nom de la rue pour laquelle vous souhaitez modifier les préférences :");
                        String streetName = MenuView.askSingleInput("");

                        // Vérifier si des préférences existent pour cette rue
                        SchedulePreferences preferencesToUpdate = existingPreferences.stream()
                                .filter(p -> p.getStreet().equalsIgnoreCase(streetName))
                                .findFirst()
                                .orElse(null);

                        if (preferencesToUpdate == null) {
                            MenuView.printMessage("Aucune préférence trouvée pour cette rue et votre quartier. Vous pouvez " +
                                    "l'ajouter en tant que nouvelle préférence.");
                        } else {
                            // Modifier les préférences
                            MenuView.printMessage("Entrez les nouvelles plages horaires :");
                            String newSchedule = MenuView.collectWeeklySchedules();
                            preferencesToUpdate.setWeekHours(newSchedule);

                            // Mettre à jour les préférences dans la base de données
                            boolean updateSuccessful = preferencesRepository.updatePreferences(preferencesToUpdate);
                            if (updateSuccessful) {
                                MenuView.printMessage("Vos préférences ont été mises à jour avec succès !");
                            } else {
                                MenuView.printMessage("La mise à jour des préférences a échoué. Veuillez réessayer.");
                            }
                        }
                    }
                    break;

                default:
                    MenuView.printMessage("Choix invalide. Veuillez réessayer.");
            }
        }
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
                MenuView.printMessage("[Vue] " + notification.getDescription());
            } else {
                MenuView.printMessage(notification.getDescription());
                // Marquer comme vu après affichage
                notifRepo.markNotificationAsSeen(notification.getId(), userId);
            }
        }
    }
}
