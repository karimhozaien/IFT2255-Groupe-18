package com.maville.controller.activity;

import com.maville.controller.repository.NotificationRepository;
import com.maville.controller.repository.SchedulePreferencesRepository;
import com.maville.controller.repository.UserRepository;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.Authenticate;
import com.maville.controller.services.PostalCodeFinder;
import com.maville.model.Notification;
import com.maville.model.Project;
import com.maville.model.WorkRequestForm;
import com.maville.view.MenuView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Contrôleur d'activités pour les intervenants. Fournit des fonctionnalités permettant
 * de gérer les projets, les demandes de travaux et les candidatures dans le cadre de l'application.
 */
public class IntervenantActivityController {
    private final Scanner scanner;
    private final WorkRepository workRepository;
    private final SchedulePreferencesRepository schedulePreferencesRepository;

    public IntervenantActivityController() {
        scanner = new Scanner(System.in);
        workRepository = new WorkRepository();
        schedulePreferencesRepository = new SchedulePreferencesRepository();
    }

    /**
     * Permet à un intervenant de soumettre un nouveau projet.
     * Les informations du projet sont recueillies auprès de l'utilisateur, validées,
     * et sauvegardées dans le dépôt de projets planifiés.
     * Une notification est également créée pour les résidents concernés.
     */
    public void submitProject() {
        try {
            List<String> projectInfo = MenuView.askFormInfoForProjectSubmission();
            String projectSchedule = collectValidSchedule(projectInfo.get(4)); // affectedNeighbourhoods

            Project project = new Project(
                    projectInfo.get(0), // titre
                    projectInfo.get(1), // description
                    projectInfo.get(2), // type de travaux
                    projectInfo.get(4), // quartiers concernés
                    projectInfo.get(5), // rues concernées
                    projectInfo.get(6), // date de début
                    projectInfo.get(3), // date de fin
                    projectSchedule
            );

            workRepository.savePlannedProject(project);

            String notiDescription = "Un nouveau projet intitulé " + projectInfo.get(0) + " a été soumis dans votre quartier";
            createNotificationForProject(projectInfo.get(4), notiDescription);
            MenuView.printMessage("Le projet a été soumis avec succès !");
        } catch (Exception e) {
            MenuView.printMessage("Une erreur est survenue : " + e.getMessage());
        }
    }

    /**
     * Permet à un intervenant de mettre à jour les détails d'un projet existant.
     * Les projets planifiés sont affichés, l'utilisateur sélectionne un projet,
     * et les informations mises à jour sont sauvegardées.
     * Une notification est générée pour les résidents concernés.
     */
    public void updateProject() {
        try {
            List<Project> plannedProjects = workRepository.getPlannedProjects();
            if (plannedProjects == null || plannedProjects.isEmpty()) {
                MenuView.printMessage("Aucun projet trouvé à mettre à jour.");
                return;
            }
            MenuView.showResults(plannedProjects);

            int option;
            while (true) {
                try {
                    String input = MenuView.askSingleInput("Sélectionnez un projet à mettre à jour : ");
                    option = Integer.parseInt(input);
                    if (option > 0 && option <= plannedProjects.size()) { // Parmi les choix possibles ?
                        break;
                    } else {
                        MenuView.printMessage("Numéro de projet invalide. Veuillez entrer un numéro dans la liste.");
                    }
                } catch (NumberFormatException e) {
                    MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                }
            }

            Project projectToUpdate = plannedProjects.get(option - 1);
            updateProjectDetails(projectToUpdate);
        } catch (Exception e) {
            MenuView.printMessage("Erreur : " + e.getMessage());
        }
    }

    /**
     * Permet à un intervenant de consulter toutes les demandes de travaux.
     * Affiche les demandes disponibles, offre la possibilité de soumettre une candidature
     * ou de retirer une candidature existante.
     */
    public void consultWorkRequests() {
        List<WorkRequestForm> workRequestForms = workRepository.fetchWorkRequests();

        MenuView.showResults(filterWorkRequests(workRequestForms));

        boolean exitLoop = false;
        while (!exitLoop) {
            try {
                String input = MenuView.askLongInput("Voulez-vous soumettre votre candidature pour l'une de " +
                                "ces requêtes ?", "[1] Oui", "[2] Non");
                int option = Integer.parseInt(input); // Si un lettre est entrée, erreur

                switch (option) {
                    case 1:
                        candidacySubmission(workRequestForms);
                        break;
                    case 2:
                        exitLoop = true;
                        break;
                    default: // dans le cas que le user entre un chiffre mais pas valude
                        MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                        break;
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide et non une lettre.");
            }
        }

        boolean exitLoop2 = false;
        while (!exitLoop2) {
            try {
                String input = MenuView.askLongInput("Voulez-vous retirer votre candidature pour l'une de " +
                        "ces requêtes ?", "[1] Oui", "[2] Non");
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1:
                        candidacyRemove(workRequestForms);
                        break;
                    case 2:
                        exitLoop2 = true;
                        break;
                    default:
                        MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                        break;
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide et non une lettre.");
            }
        }
    }

    // Méthodes privées
    private List<WorkRequestForm> filterWorkRequests(List<WorkRequestForm> workRequestForms) {
        try {
            // Afficher les options de filtrage
            MenuView.askFilter("Type de travaux", "Quartier", "Date de début", "Aucun filtrage");

            // Lire l'option choisie par l'utilisateur
            int option = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante

            // Filtrer en fonction de l'option choisie
            switch (option) {
                case 1: // Filtrage par type de travaux
                    MenuView.printMessage("Entrez le type de travaux : ");
                    String type = scanner.nextLine().trim();
                    return workRequestForms.stream()
                            .filter(request -> request.getProjectType().toString().equalsIgnoreCase(type))
                            .toList();

                case 2: // Filtrage par quartier
                    MenuView.printMessage("Entrez le nom du quartier : ");
                    String neighbourhood = scanner.nextLine().trim();
                    return workRequestForms.stream()
                            //.filter(request -> request.getNeighbourhood().equalsIgnoreCase(neighbourhood))
                            .toList();

                case 3: // Filtrage par date de début
                    MenuView.printMessage("Entrez la date de début (format YYYY-MM-DD) : ");
                    String startDate = scanner.nextLine().trim();
                    return workRequestForms.stream()
                            //.filter(request -> request.getStartDate().equals(startDate))
                            .toList();

                case 4: // Aucun filtrage
                default:
                    return workRequestForms; // Retourne la liste complète
            }
        } catch (Exception e) {
            MenuView.printMessage("Erreur lors du filtrage des requêtes : " + e.getMessage());
            return workRequestForms; // Retourner la liste complète en cas d'erreur
        }
    }

    private void candidacySubmission(List<WorkRequestForm> workRequestForms) {
        int option;
        while (true) {
            try {
                String input = MenuView.askSingleInput("Entrez le numéro de la requête dont vous voulez soumettre " +
                        "votre candidature : ");

                option = Integer.parseInt(input);
                if (option > 0 && option <= workRequestForms.size()) { // Parmi les choix possibles ?
                    break;
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Numéro de la requête invalide.");
            }
        }

        WorkRequestForm workRequestForm = workRequestForms.get(option - 1); // requete voulant etre modifier
        submitCandidacy(workRequestForm);
    }

    private void candidacyRemove(List<WorkRequestForm> workRequestForms) {
        int option;
        while (true) {
            try {
                String input = MenuView.askSingleInput("Entrez le numéro de la requête dont vous voulez retirer " +
                        "votre candidature : ");
                option = Integer.parseInt(input);
                if (option > 0 && option <= workRequestForms.size()) { // Parmi les choix possibles ?
                    break;
                }
            } catch (NumberFormatException e) {
                MenuView.printMessage("Numéro de la requête invalide. ");
            }
        }

        WorkRequestForm workRequestForm = workRequestForms.get(option - 1); // requete voulant etre modifier
        removeSubmission(workRequestForm);
    }

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
        for (String[] usersInfo : UserRepository.getInstance().fetchAllResidents()) {
            String currentUserResidentialAddress = usersInfo[1];
            for (String neighbourhood : affectedNeighbourhood.split(",")) {
                boolean isEqual = postalCodeFinder.isValidCorrespondance(neighbourhood, currentUserResidentialAddress);
                if (isEqual) {
                    notification.addResident(usersInfo[0]); // on ajoute le resident a la liste
                }
            }
        }
        NotificationRepository.getInstance().saveNotification(notification); // ajouter la notification a la DB
    }

    private void updateProjectDetails(Project project) {
        List<String> updatedInfo = MenuView.askFormInfoForProjectUpdate();
        project.setDescription(updatedInfo.get(0));
        project.setEndDate(updatedInfo.get(1));
        project.setWorkStatus(Project.WorkStatus.valueOf(updatedInfo.get(2)));

        try {
            workRepository.updatePlannedProject(project);
            String notiDescription = "La mise à jour d'un projet " + project.getTitle() + " a été fait dans votre quartier";
            createNotificationForProject(project.getAffectedNeighbourhood(), notiDescription);
            MenuView.printMessage("Le projet a été mis à jour avec succès !");
        } catch (Exception e) {
            MenuView.printMessage("Une erreur est survenue : " + e.getMessage());
        }
    }

    private void submitCandidacy(WorkRequestForm workRequestForm) {
        List<String> candidacyInfo = MenuView.askInfoForCandidacySubmission();
        workRequestForm.addSubmission(Authenticate.getUserId() +
                ":{start_date: " + candidacyInfo.getFirst() +
                ", end_date: " + candidacyInfo.get(1) + "}");

        try {
            workRepository.updatingCandidacySubmission(workRequestForm);
            MenuView.printMessage("La candidature a été soumise !");
        } catch (Exception e) {
            MenuView.printMessage("Une erreur est survenue : " + e.getMessage());
        }
    }

    private void removeSubmission(WorkRequestForm workRequestForm) {
        try {
            List<String> submissions = new ArrayList<>(workRequestForm.getSubmissions()); // mutable
            String currentUserId = Authenticate.getUserId();

            // chercher une soumission correspondant à l'utilisateur
            String userSubmission = null;
            for (String submission : submissions) {
                if (submission.startsWith(currentUserId + ":")) {
                    userSubmission = submission;
                    break;
                }
            }

            // Si aucune soumission trouvée pour l'utilisateur, afficher un message et sortir
            if (userSubmission == null) {
                MenuView.printMessage("Vous ne pouvez pas retirer votre soumission car elle n'existe pas.");
                return;
            }

            submissions.remove(userSubmission);
            workRequestForm.setSubmissions(submissions);
            workRepository.updatingCandidacySubmission(workRequestForm);

            MenuView.printMessage("Votre soumission a été retirée avec succès !");
        } catch (Exception e) {
            MenuView.printMessage("Une erreur est survenue lors du retrait de la soumission : " + e.getMessage());
        }
    }
}