package com.maville.view;

public class MenuView {

    public static void welcomeMessage() {
        printMessage("Bienvenue dans l'application Maville!\n" + "Voulez-vous vous inscrire ou vous connecter ?");
    }

    public static void exitMessage() {
        printMessage("Vous avez quitté l'application, au revoir!");
    }

    public static void backMessage() {
        printMessage("Vous êtes revenu en arrière!");
    }

    public static void authMessage() {
        printMessage("[1] S'enregistrer");
        printMessage("[2] Se connecter");
    }

    public static void residentMenuMessages() {
        printMessage("[1] Consultation des travaux");
        printMessage("[2] Rechercher des travaux");
        printMessage("[3] Participer à une planification");
        printMessage("[4] Soumettre une requête de travaux");
        printMessage("[5] Recevoir des notifications");
        printMessage("[0] Quitter");
        printMessage("Choisissez l'une des options : ");
    }

    public static void intervenantMenuMessages() {
        printMessage("[1] Soumettre de nouveaux travaux");
        printMessage("[2] Mettre à jour les travaux");
        printMessage("[0] Quitter");
        printMessage("Choisissez l'une des options : ");
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
