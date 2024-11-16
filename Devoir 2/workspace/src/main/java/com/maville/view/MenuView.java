package com.maville.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    static final Scanner scanner = new Scanner(System.in);

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
        printMessage("[1] Consulter les travaux");
        printMessage("[2] Consulter les entraves routières");
        printMessage("[3] Rechercher des travaux");
        printMessage("[4] Participer à une planification");
        printMessage("[5] Soumettre une requête de travaux");
        printMessage("[6] Recevoir des notifications");
        printMessage("[0] Quitter");
        printMessage("Choisissez l'une des options : ");
    }

    public static void intervenantMenuMessages() {
        printMessage("[1] Soumettre de nouveaux travaux");
        printMessage("[2] Mettre à jour les travaux");
        printMessage("[3] Consulter les requêtes de travaux");
        printMessage("[0] Quitter");
        printMessage("Choisissez l'une des options : ");
    }

    public static void askFilter(String type1, String type2, String type3) {
        printMessage("Désirez-vous filtrer par quartier ou par type de travaux ?");
        printMessage("[1] " + type1);
        printMessage("[2] " + type2);
        printMessage("[3] " + type3);
    }

    public static List<String> askFormInfo() {
        List<String> infos = new ArrayList<>();
        printMessage("Entrez les informations suivantes pour la requête :");

        printMessageInline("Titre : ");
        infos.add(getStringInput());

        printMessageInline("Description : ");
        infos.add(getStringInput());

        printMessageInline("Type de travaux : ");
        infos.add(getStringInput());

        printMessageInline("Date de fin espéré : ");
        infos.add(getStringInput());

        return infos;
    }

    private static String getStringInput() {
        return scanner.nextLine();
    }

    private static int getIntInput() {
        return scanner.nextInt();
    }

    public static <T> void showResults(List<T> items) {
        for (T item : items) {
            printMessage(item.toString());
        }
    }

    private static void printMessageInline(String message) {
        System.out.print(message);
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
