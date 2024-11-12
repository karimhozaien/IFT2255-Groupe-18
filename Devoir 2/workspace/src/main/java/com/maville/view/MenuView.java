package com.maville.view;

public class MenuView {
    private static final String WELCOME_MESSAGE = "Bienvenue dans l'application Maville!\n" +
                                                  "Voulez-vous vous inscrire ou vous connecter ?";
    private static final String EXIT_MESSAGE = "Vous avez quitté l'application, au revoir!";
    private static final String BACK_MESSAGE = "Vous êtes revenu en arrière!";
    private static final String[] RESIDENT_MENU_MESSAGES = {
           "Consultation des travaux",
            "Rechercher des travaux",
            "Signaler un problème",
            "Soumettre une requête de travaux",
            "Recevoir des notifications",
            "Quitter"
    };
    private static final String[] INTERVENANT_MENU_MESSAGES = {
            "Soumettre de nouveaux travaux",
            "Mettre à jour les travaux",
            "Quitter"
    };
    private static final String USER_CHOICE_MESSAGE = "Choisissez l'une des options : ";


    public static void welcomeMessage() {
        printMessage(WELCOME_MESSAGE);
    }

    public static void exitMessage() {
        printMessage(EXIT_MESSAGE);
    }

    public static void backMessage() {
        printMessage(BACK_MESSAGE);
    }

    public static void authMessage() {
        printMessage("[1] S'enregistrer");
        printMessage("[2] Se connecter");
    }

    public static void residentMenuMessages() {
        for (int i = 0; i < RESIDENT_MENU_MESSAGES.length; i++) {
            printMessage("[" + ((i + 1) % RESIDENT_MENU_MESSAGES.length) + "] " + RESIDENT_MENU_MESSAGES[i]);
        }
        printMessage(USER_CHOICE_MESSAGE);
    }

    public static void intervenantMenuMessages() {
        for (int i = 0; i < INTERVENANT_MENU_MESSAGES.length; i++) {
            printMessage("[" + ((i + 1) % INTERVENANT_MENU_MESSAGES.length) + "] " + INTERVENANT_MENU_MESSAGES[i]);
        }
        printMessage(USER_CHOICE_MESSAGE);
    }

    public static int getIntervenantOptionSize() {
        return INTERVENANT_MENU_MESSAGES.length;
    }

    public static int getResidentOtionSize() {
        return RESIDENT_MENU_MESSAGES.length;
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
