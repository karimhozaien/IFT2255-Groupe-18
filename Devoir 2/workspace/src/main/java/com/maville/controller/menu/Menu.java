package com.maville.controller.menu;

import com.maville.view.MenuView;
import java.util.Scanner;

public abstract class Menu {
    protected static final Scanner scanner = new Scanner(System.in);

    public void welcome() {
        MenuView.welcomeMessage();
        MenuView.authMessage();

        int option = scanner.nextInt();
        selection(option, "");
    }

    public static void showUserMenu(String userType) {
        while (true) {
            switch (userType) {
                case "resident":
                    MenuView.residentMenuMessages();
                    break;
                case "intervenant":
                    MenuView.intervenantMenuMessages();
                    break;
            }

            int option = scanner.nextInt();

            if (handleMainUserOption(option, userType)) {
                break;
            }
        }
    }

    private static boolean handleMainUserOption(int option, String userType) {
        if (userType.equals("resident")) {
            switch (option) {
                case 1:
                    // Consultation des travaux
                    System.out.println("Consultation des travaux...");
                    // Ajoutez votre logique ici
                    break;
                case 2:
                    // Rechercher des travaux
                    System.out.println("Recherche des travaux...");
                    // Ajoutez votre logique ici
                    break;
                case 3:
                    // Signaler un problème
                    System.out.println("Signalement d'un problème...");
                    // Ajoutez votre logique ici
                    break;
                case 4:
                    // Soumettre une requête de travaux
                    System.out.println("Soumission d'une requête de travaux...");
                    // Ajoutez votre logique ici
                    break;
                case 5:
                    // Recevoir des notifications
                    System.out.println("Réception des notifications...");
                    // Ajoutez votre logique ici
                    break;
                case 0: // Option pour quitter
                    return true; // Indique que l'utilisateur souhaite quitter
                default:
                    System.out.println("Option invalide pour résident. Veuillez réessayer.");
                    break;
            }
        } else if (userType.equals("intervenant")) {
            switch (option) {
                case 1:
                    // Soumettre de nouveaux travaux
                    System.out.println("Soumission de nouveaux travaux...");
                    // Ajoutez votre logique ici
                    break;
                case 2:
                    // Mettre à jour les travaux
                    System.out.println("Mise à jour des travaux...");
                    // Ajoutez votre logique ici
                    break;
                case 0: // Option pour quitter
                    return true; // Indique que l'utilisateur souhaite quitter
                default:
                    System.out.println("Option invalide pour intervenant. Veuillez réessayer.");
                    break;
            }
        }

        return false; // Indique que l'utilisateur ne souhaite pas quitter
    }

    // Méthode abstraite que les sous-classes implémenteront
    protected abstract void selection(int option, String action);
}
