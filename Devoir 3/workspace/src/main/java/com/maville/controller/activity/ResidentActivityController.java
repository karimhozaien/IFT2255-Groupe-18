package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.SchedulePreferencesRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.Authenticate;
import com.maville.controller.services.PostalCodeFinder;
import com.maville.model.Notification;
import com.maville.model.Project;
import com.maville.model.SchedulePreferences;
import com.maville.model.WorkRequestForm;
import com.maville.view.MenuView;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        boolean validInput = false;

        try {
            // Boucle pour s'assurer que l'utilisateur choisit une option valide pour le type de projet
            while (!validInput) {
                MenuView.askSimpleOptions("Souhaitez-vous consulter les projets en cours ou à venir ?",
                        "Quitter", "Projets en cours", "Projets à venir");

                try {
                    int projectTypeOption = scanner.nextInt();
                    scanner.nextLine(); // Consommer le saut de ligne

                    List<Project> projects;
                    if (projectTypeOption == 1) {
                        // Récupérer les projets en cours
                        projects = workRepo.getOngoingProjects();
                        validInput = true; // Entrée valide, sortir de la boucle
                    } else if (projectTypeOption == 2) {
                        // Récupérer les projets à venir (filtrés à moins de 3 mois)
                        projects = workRepo.getPlannedProjectsWithinThreeMonths();
                        validInput = true; // Entrée valide, sortir de la boucle
                    } else if (projectTypeOption == 0) {
                        MenuView.printMessage("Retour au menu principal.");
                        return;
                    } else {
                        MenuView.printMessage("Choix invalide. Veuillez entrer 0, 1 ou 2.");
                        continue;
                    }

                    // Boucle pour le filtrage des projets
                    boolean validFilter = false;
                    while (!validFilter) {
                        MenuView.askFilter("Quartier", "Type de travaux", "Tous les projets");

                        try {
                            int filterOption = scanner.nextInt();
                            scanner.nextLine(); // Nettoyer le tampon

                            switch (filterOption) {
                                case 1:
                                    MenuView.printMessage("Entrez le nom du quartier : ");
                                    String neighbourhood = scanner.nextLine();
                                    MenuView.showResults(workRepo.getFilteredProjects("quartier", neighbourhood, projects));
                                    validFilter = true; // Entrée valide
                                    break;
                                case 2:
                                    MenuView.printMessage("Choisissez le type de travaux : ");
                                    String workType = MenuView.askWorkType();
                                    MenuView.showResults(workRepo.getFilteredProjects("travail", workType, projects));
                                    validFilter = true; // Entrée valide
                                    break;
                                case 3:
                                    MenuView.showResults(projects);
                                    validFilter = true; // Entrée valide
                                    break;
                                default:
                                    MenuView.printMessage("Choix invalide. Veuillez entrer 1, 2 ou 3.");
                                    break;
                            }
                        } catch (InputMismatchException e) {
                            MenuView.printMessage("Entrée invalide. Veuillez entrer un nombre valide.");
                            scanner.nextLine(); // Consommer la saisie incorrecte
                        }
                    }
                } catch (InputMismatchException e) {
                    MenuView.printMessage("Entrée invalide. Veuillez entrer un nombre valide.");
                    scanner.nextLine(); // Consommer la saisie incorrecte
                }
            }
        } catch (IOException e) {
            MenuView.printMessage("Une erreur s'est produite lors de la récupération des données.");
            e.printStackTrace();
        }
    }

    /**
     * Affiche les entraves routières en cours et planifiées, avec la possibilité de filtrer
     * par rue ou type de travaux.
     * Demande à l'utilisateur de sélectionner un filtre et affiche les résultats correspondants.
     */
    public void consultRoadObstructions() {
        boolean validInput = false;
        while (!validInput) {
            try {
                MenuView.askFilter("Rue", "Type de travaux", "Autre");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne
                List<String> filteredRoadObstructions;

                switch (option) {
                    case 1:
                        validInput = true; // L'entrée est correcte
                        MenuView.printMessage("Entrez le nom de la rue : ");
                        String name = scanner.nextLine();
                        filteredRoadObstructions = workRepo.getFilteredRoadObstructions("rue", name);
                        if (!filteredRoadObstructions.isEmpty()) {
                            MenuView.showResults(filteredRoadObstructions);
                        } else {
                            MenuView.printMessage("Aucune entrave selon cette rue.");
                        }
                        break;
                    case 2:
                        validInput = true; // L'entrée est correcte
                        MenuView.printMessage("Entrez le type de travaux : ");
                        String type = MenuView.askWorkType();
                        filteredRoadObstructions = workRepo.getFilteredRoadObstructions("travail", type);
                        if (!filteredRoadObstructions.isEmpty()) {
                            MenuView.showResults(filteredRoadObstructions);
                        } else {
                            MenuView.printMessage("Aucune entrave selon ce type de travaux.");
                        }
                        break;
                    case 3:
                        validInput = true; // L'entrée est correcte
                        MenuView.showResults(workRepo.getRoadObstructions());
                        break;
                    default:
                        MenuView.printMessage("Option invalide. Veuillez entrer un nombre entre 1 et 3.");
                        break;
                }
            } catch (InputMismatchException e) {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un nombre entre 1 et 3.");
                scanner.nextLine(); // Consommer la saisie incorrecte
            } catch (IOException e) {
                MenuView.printMessage("Une erreur s'est produite lors de la récupération des données.");
                e.printStackTrace();
            }
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
        boolean exitLoop = false;
        while (!exitLoop) {
            MenuView.askSimpleOptions("Voulez-vous soumettre une nouvelle requête ou consulter vos requêtes ?",
                    "Quitter", "Ajouter", "Consulter");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Nettoie le tampon
            switch (choice) {
                case 0:
                    exitLoop = true;
                    break;
                case 1:
                    addWorkRequest();
                    break;
                case 2:
                    List<WorkRequestForm> workRequests = consultUserWorkRequests();
                    if (workRequests.isEmpty()) {
                        MenuView.printMessage("Vous n'avez soumis aucune requête pour l'instant.");
                    } else {
                        MenuView.printMessage("Voici vos requêtes soumises :");
                        MenuView.showResults(workRequests);

                        askAboutWorkRequestCandidacies(workRequests);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private void addWorkRequest() {
        List<String> workRequestInfo = MenuView.askFormInfo();
        WorkRequestForm workRequestForm = new WorkRequestForm(
                Authenticate.getUserId(),
                workRequestInfo.get(0),
                workRequestInfo.get(1),
                workRequestInfo.get(2),
                workRequestInfo.get(3)
        );
        WorkRepository workRepo = new WorkRepository();
        workRepo.saveWorkRequest(workRequestForm);
    }

    private List<WorkRequestForm> consultUserWorkRequests() {
        String userId = Authenticate.getUserId(); // Récupérer l'ID de l'utilisateur courant
        WorkRepository workRepo = new WorkRepository();

        return workRepo.fetchWorkRequestsByUserId(userId);
    }

    private void askAboutWorkRequestCandidacies(List<WorkRequestForm> workRequests) {
        boolean exitLoop = false;
        while (!exitLoop) {
            try {
                String input = MenuView.askLongInput("Voulez-vous accepter une des candidatures ?",
                        "[1] Oui", "[2] Non");
                int option = Integer.parseInt(input); // Si un lettre est entrée, erreur
                switch (option) {
                    case 1 -> acceptCandidacy(workRequests);
                    case 2 -> exitLoop = true;
                    default -> MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide et non une lettre.");
            }
        }
    }

    private void acceptCandidacy(List<WorkRequestForm> workRequests) {
        int option;
        while (true) {
            try {
                String input = MenuView.askSingleInput("Entrez le numéro de la requête dont vous voulez accepter " +
                        "la candidature : ");
                option = Integer.parseInt(input);
                if (option > 0 && option <= workRequests.size()) {
                    break;
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Numéro de la requête invalide.");
            }
        }

        WorkRequestForm workRequest = workRequests.get(option - 1);

        // Demander à l'utilisateur de sélectionner un intervenant
        List<String> submissions = workRequest.getSubmissions();
        if (submissions.isEmpty()) {
            MenuView.printMessage("Aucune soumission disponible pour cette requête.");
            return;
        }

        // Afficher les soumissions formatées
        MenuView.printMessage("Soumissions disponibles :");
        for (int i = 0; i < submissions.size(); i++) {
            String formattedSubmission = parseSingleSubmission(submissions.get(i));
            if (!formattedSubmission.isEmpty()) {
                MenuView.printMessage((i + 1) + ". " + formattedSubmission);
            }
        }

        int submissionOption;
        while (true) {
            try {
                String input = MenuView.askSingleInput("Entrez le numéro de la soumission que vous voulez accepter : ");
                submissionOption = Integer.parseInt(input);
                if (submissionOption > 0 && submissionOption <= submissions.size()) {
                    break;
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Numéro de la soumission invalide.");
            }
        }

        // Extraire uniquement l'ID de la soumission sélectionnée
        String selectedSubmission = submissions.get(submissionOption - 1);
        String selectedSubmissionId = extractSubmissionId(selectedSubmission);
        System.out.println(selectedSubmissionId);

        if (selectedSubmissionId != null) {
            // Mettre à jour la liste des soumissions avec uniquement l'ID sélectionné
            workRequest.setChosenIntervenant(selectedSubmissionId);
            MenuView.printMessage("La soumission sélectionnée a été mise à jour avec succès !");

            // Ajouter un message optionnel
            String optionalMessage = MenuView.askSingleInput("Entrez un message pour l'intervenant (optionnel) : ");
            workRequest.setClosingMessage(optionalMessage);

            // Mettre à jour la requête dans la base de données
            workRepo.updatingCandidacySubmission(workRequest);
        } else {
            MenuView.printMessage("Erreur : Impossible de trouver l'ID de la soumission sélectionnée.");
        }
    }

    public String parseSingleSubmission(String submission) {
        // Regex pour une soumission complète avec ID et dates
        String regexFull = "^([^:]+):\\{start_date:\\s*([^,]+),\\s*end_date:\\s*([^}]+)}$";
        // Regex pour une soumission contenant uniquement l'ID
        String regexIdOnly = "^([^:]+)$";

        Pattern patternFull = Pattern.compile(regexFull);
        Pattern patternIdOnly = Pattern.compile(regexIdOnly);

        Matcher matcherFull = patternFull.matcher(submission);
        Matcher matcherIdOnly = patternIdOnly.matcher(submission);

        if (matcherFull.matches()) {
            // Cas avec ID et dates
            String intervenantId = matcherFull.group(1).trim();
            String startDate = matcherFull.group(2).trim();
            String endDate = matcherFull.group(3).trim();
            return intervenantId + ", start_date: " + startDate + ", end_date: " + endDate;
        } else if (matcherIdOnly.matches()) {
            // Cas avec ID uniquement
            return matcherIdOnly.group(1).trim();
        }

        return ""; // Retourne une chaîne vide si le format est invalide
    }

    private String extractSubmissionId(String submission) {
        // Regex to capture only the UUID before the "{"
        String regex = "^([a-f0-9\\-]+):\\{";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(submission);

        if (matcher.find()) { // Use find() for partial matches
            return matcher.group(1).trim(); // Return the UUID (ID)
        }

        return null; // Return null if no match is found
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
