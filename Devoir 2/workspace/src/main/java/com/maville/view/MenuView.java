package com.maville.view;

public class MenuView {
    private static final String WELCOME_MESSAGE = "Bienvenue dans l'application Maville!\n" +
                                                  "Voulez-vous vous inscrire ou vous connecter ?";
    private static final String EXIT_MESSAGE = "Vous avez quitté l'application, au revoir!";
    private static final String BACK_MESSAGE = "Vous êtes revenu en arrière!";

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

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
