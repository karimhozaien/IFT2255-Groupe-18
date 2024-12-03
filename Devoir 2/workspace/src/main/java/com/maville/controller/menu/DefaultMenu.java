package com.maville.controller.menu;

import com.maville.controller.activity.IntervenantActivityController;
import com.maville.controller.activity.ResidentActivityController;
import com.maville.MaVille;

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
            case 0:
                MaVille.exitApplication();
                return;
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
                    //System.out.println("Consultation des travaux...");
                    residentAC.consultWorks();
                    break;
                case 2:
                    // Consultation des entraves routières
                    //System.out.println("Consultation des entraves...");
                    residentAC.consultRoadObstructions();
                    break;
                case 3:
                    // Rechercher des travaux
                    //System.out.println("Recherche des travaux...");
                    residentAC.searchWorks();
                    break;
                case 4:
                    // Permettre une planification participative
                    //System.out.println("Participer à une planification...");
                    residentAC.participateToSchedule();
                    break;
                case 5:
                    // Soumettre une requête de travaux
                    //System.out.println("Soumission d'une requête de travaux...");
                    residentAC.submitWorkRequest();
                    break;
                case 6:
                    // Recevoir des notifications personalisées
                    //System.out.println("Réception des notifications...");
                    residentAC.receivePersonalizedNotifications();
                    break;
                case 0: // Option pour quitter
                    return true;
                default:
                    //System.out.println("Option invalide pour résident. Veuillez réessayer.");
                    break;
            }
        } else if (userType.equals("intervenant")) {
            System.out.println("karim");
            IntervenantActivityController intervenantAC = new IntervenantActivityController();

            switch (option) {
                case 1:
                    // Soumettre de nouveaux travaux
                    intervenantAC.submitProject();
                    //System.out.println("Soumission de nouveaux travaux...");
                    break;
                case 2:
                    // Mettre à jour les travaux
                    intervenantAC.updateProject();
                    //System.out.println("Mise à jour des travaux...");
                    break;
                case 3:
                    intervenantAC.consultWorkRequests();
                    //System.out.println("Consultation des requêtes...");
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
