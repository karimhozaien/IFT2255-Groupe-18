package com.maville.controller.menu;

import com.maville.controller.activity.ResidentActivityController;

public class DefaultMenu extends Menu {
    private final AuthenticationMenu AUTHMENU = new AuthenticationMenu();

    @Override
    protected void selection(int option, String action) {
        switch (option) {
            case 1:
                AUTHMENU.signUpManager();  // Navigue vers le sous-menu d'inscription
                break;
            case 2:
                AUTHMENU.logInManager();   // Navigue vers le sous-menu de connexion
                break;
            default:
                System.out.println("Option invalide, veuillez réessayer.");
                break;
        }
    }

    protected static boolean handleMainMenuOption(int option, String userType) {
        if (userType.equals("resident")) {
            ResidentActivityController residentAC = new ResidentActivityController();

            switch (option) {
                case 1:
                    // Consultation des travaux
                    residentAC.consultWorks();
                    System.out.println("Consultation des travaux...");
                    break;
                case 2:
                    // Rechercher des travaux
                    residentAC.searchWorks();
                    System.out.println("Recherche des travaux...");
                    break;
                case 3:
                    // Permettre une planification participative
                    residentAC.participateToSchedule();
                    System.out.println("Participer à une planification...");
                    break;
                case 4:
                    // Soumettre une requête de travaux
                    residentAC.submitRequest();
                    System.out.println("Soumission d'une requête de travaux...");
                    break;
                case 5:
                    // Recevoir des notifications personalisées
                    residentAC.receivePersonalizedNotifications();
                    System.out.println("Réception des notifications...");
                    break;
                case 0: // Option pour quitter
                    return true;
                default:
                    System.out.println("Option invalide pour résident. Veuillez réessayer.");
                    break;
            }
        } else if (userType.equals("intervenant")) {
            switch (option) {
                case 1:
                    // Soumettre de nouveaux travaux
                    System.out.println("Soumission de nouveaux travaux...");
                    break;
                case 2:
                    // Mettre à jour les travaux
                    System.out.println("Mise à jour des travaux...");
                    break;
                case 0: // Option pour quitter
                    return true;
                default:
                    System.out.println("Option invalide pour intervenant. Veuillez réessayer.");
                    break;
            }
        }

        return false; // Indique que l'utilisateur ne souhaite pas quitter
    }
}
