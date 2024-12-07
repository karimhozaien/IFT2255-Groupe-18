package com.maville.view;

import com.maville.model.Project;

import java.util.*;
import java.util.stream.IntStream;
import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.Color.*;

public class MenuView {
    private static final Scanner scanner = new Scanner(System.in);

    // Messages
    public static void welcomeMessage() {
        printMessage(
                """
                        __  __    _    __     __ ___ _     _     _____\s
                       |  \\/  |  / \\   \\ \\   / /|_ _| |   | |   |____|
                       | |\\/| | / _ \\   \\ \\ / /  | || |   | |   | __| \s
                       | |  | |/ ___ \\   \\ V /   | || |___| |___| |___\s
                       |_|  |_/_/   \\_\\   \\_/   |___|_____|_____|_____|
                """
        );
        printMessage("Bienvenue dans l'application Maville!\nVoulez-vous vous inscrire ou vous connecter ?");
    }

    public static void exitMessage() {
        printMessage("Vous avez quitté l'application, au revoir!");
    }

    public static void backMessage() {
        printMessage("Vous êtes revenu en arrière!");
    }

    public static void authMessage() {
        displayOptions(
                "Choisissez une option :",
                new TreeMap<>(Map.of(1, "S'enregistrer", 2, "Se connecter", 0, "Quitter l'application"))
        );
    }

    public static void residentMenuMessages() {
        displayOptions(
                "Choisissez l'une des options :",
                new TreeMap<>(Map.of(
                        1, "Consulter les travaux",
                        2, "Consulter les entraves routières",
                        3, "Rechercher des travaux",
                        4, "Participer à une planification",
                        5, "Soumettre une requête de travaux",
                        6, "Consulter mes notifications",
                        0, "Quitter"
                ))
        );
    }

    public static void intervenantMenuMessages() {
        displayOptions(
                "Choisissez l'une des options :",
                new TreeMap<>(Map.of(
                        1, "Soumettre de nouveaux travaux",
                        2, "Mettre à jour les travaux",
                        3, "Consulter les requêtes de travaux",
                        0, "Quitter"
                ))
        );
    }

    public static void askFilter(String... filterTypes) {
        TreeMap<Integer, String> options = new TreeMap<>();
        for (int i = 0; i < filterTypes.length; i++) {
            options.put(i + 1, filterTypes[i]); // Indexer à partir de 1
        }

        displayOptions("Désirez-vous filtrer par quartier ou par type de travaux ?", options);
    }

    public static List<String> askFormInfo() {
        return askInputs(
                "Entrez les informations suivantes pour la requête :",
                "Titre : ", "Description : ", "Type de travaux : ", "Date de fin espérée (AAAA-MM-JJ) : "
        );
    }

    public static List<String> askSchedulePreferences() {
        List<String> infos = new ArrayList<>();

        printMessage("Veuillez entrer vos préférences :");
        infos.add(askSingleInput("Pour quelle rue voulez-vous faire votre demande ? : "));
        infos.add(askSingleInput("Votre rue se trouve dans quel quartier ? (trois premiers caractères" +
                "du code postal) : "));
        infos.add(collectWeeklySchedules());

        return infos;
    }

    public static List<String> askInfoForCandidacySubmission() {
        List<String> infos = new ArrayList<>();

        printMessage("Veuillez entrer la date de début et de fin.");
        infos.add(askSingleInput("Date de début : "));
        infos.add(askSingleInput("Date de fin : "));

        return infos;
    }

    public static List<String> askFormInfoForProjectSubmission() {
        List<String> infos = new ArrayList<>();
        printMessage("Entrez les informations suivantes pour soumettre un projet :");

        infos.add(askSingleInput("Titre : "));
        infos.add(askSingleInput("Description : "));
        infos.add(askWorkType());
        infos.add(askSingleInput("Date de fin espérée (AAAA-MM-JJ) : "));
        infos.add(askSingleInput("Entrez les trois premiers caractères du code postal (séparés par des virgules) : "));
        infos.add(askSingleInput("Rues concernées (séparées par des virgules) : "));
        infos.add(askSingleInput("Date de début (AAAA-MM-JJ) : "));

        return infos;
    }

    public static String collectWeeklySchedules() {
        String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};
        String[] schedules = new String[7];

        for (int i = 0; i < days.length; i++) {
            String input = askSingleInput("Quelles plages horaires sont disponibles pour " + days[i]
                    + "? (format : XX:XX-XX:XX,...) : ");
            if (input.isEmpty()) { // Permettre au user de ne rien entrer
                schedules[i] = "N/A";
            } else {
                schedules[i] = input;
            }
        }
        return String.join(",", schedules); // Retourner les horaires sous forme de chaîne unique
    }

    public static List<String> askFormInfoForProjectUpdate() {
        List<String> inputs = new ArrayList<>();
        printMessage("Entrez les informations suivantes pour mettre à jour le projet :");

        inputs.add(askSingleInput("Description : "));
        inputs.add(askSingleInput("Date de fin espérée (AAAA-MM-JJ) : "));

        // Display options for project statuses
        printMessage("Statut du projet :");
        Project.WorkStatus[] workStatuses = Project.WorkStatus.values();
        for (int i = 0; i < workStatuses.length; i++) {
            printMessage("[" + (i + 1) + "] " + workStatuses[i]);
        }

        // Collect and validate the user's choice
        int option;
        while (true) {
            try {
                String input = askSingleInput("Sélectionnez le statut du projet : ");
                option = Integer.parseInt(input) - 1; // Adjust for 0-based index
                if (option >= 0 && option < workStatuses.length) {
                    inputs.add(workStatuses[option].toString());
                    break;
                } else {
                    printMessage("Option invalide. Veuillez entrer un numéro valide.");
                }
            } catch (NumberFormatException e) {
                printMessage("Entrée invalide. Veuillez entrer un numéro.");
            }
        }

        return inputs;
    }

    public static <T> void showResults(List<T> items) {
        IntStream.range(0, items.size())
                .mapToObj(i -> (i + 1) + ". " + items.get(i))
                .toList()
                .forEach(MenuView::printMessage);
    }

    // Helper Methods
    private static void displayOptions(String header, TreeMap<Integer, String> options) {
        printMessage(header); // En-tête en couleur
        System.out.println("****************************************");
        options.forEach((key, value) ->
                System.out.println(Ansi.ansi().fg(CYAN).a("[" + key + "] ").reset() + value));
        System.out.println("****************************************");
    }

    public static String askSingleInput(String prompt) {
        printMessageInline(prompt);
        return scanner.nextLine();
    }

    private static List<String> askInputs(String header, String... prompts) {
        List<String> inputs = new ArrayList<>();
        printMessage(header);
        for (String prompt : prompts) {
            inputs.add(askSingleInput(prompt));
        }
        return inputs;
    }

    public static String askLongInput(String header, String... prompts) {
        printMessage(header);
        for (String prompt : prompts) {
            printMessage(prompt);
        }
        return scanner.nextLine();
    }

    private static String askWorkType() {
        TreeMap<Integer, Project.TypeOfWork> typeOfWorkMap = new TreeMap<>(Map.of(
                1, Project.TypeOfWork.ROAD,
                2, Project.TypeOfWork.GAS_ELECTRICITY,
                3, Project.TypeOfWork.CONSTRUCTION_RENOVATION,
                4, Project.TypeOfWork.LANDSCAPE,
                5, Project.TypeOfWork.PUBLIC_TRANSPORT,
                6, Project.TypeOfWork.SIGNAGE_LIGHTING,
                7, Project.TypeOfWork.UNDERGROUND,
                8, Project.TypeOfWork.RESIDENTIAL,
                9, Project.TypeOfWork.URBAN_MAINTENANCE,
                10, Project.TypeOfWork.TELECOMMUNICATION_NETWORKS
        ));

        displayOptions("Type de travaux :", new TreeMap<>(Map.of(
                1, "Travaux routiers", 2, "Travaux de gaz ou électricité",
                3, "Construction ou rénovation", 4, "Entretien paysager",
                5, "Travaux liés aux transports en commun", 6, "Travaux de signalisation et éclairage",
                7, "Travaux souterrains", 8, "Travaux résidentiels",
                9, "Entretien urbain", 10, "Entretien des réseaux de télécommunication"
        )));

        int option = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        Project.TypeOfWork selectedWork = typeOfWorkMap.get(option);
        if (selectedWork != null) {
            return selectedWork.toString();
        } else {
            printMessage("Option invalide. Veuillez réessayer.");
            return askWorkType();
        }
    }

    private static void printMessageInline(String message) {
        System.out.print(message);
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}