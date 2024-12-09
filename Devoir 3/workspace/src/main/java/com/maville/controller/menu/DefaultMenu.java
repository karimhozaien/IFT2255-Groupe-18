package com.maville.controller.menu;

import com.maville.controller.activity.IntervenantActivityController;
import com.maville.controller.activity.ResidentActivityController;
import com.maville.MaVille;
import com.maville.view.MenuView;

/**
 * Menu par défaut gérant les interactions principales avec les utilisateurs.
 * Ce menu inclut la gestion des sous-menus pour l'inscription, la connexion,
 * ainsi que les actions spécifiques aux résidents et aux intervenants.
 */
public class DefaultMenu extends Menu {
    private final AuthenticationMenu AUTHMENU = new AuthenticationMenu();

    /**
     * Implémente la logique de sélection d'une option dans le menu principal.
     *
     * @param option L'option sélectionnée par l'utilisateur.
     * @param action L'action associée (non utilisée ici).
     */
    @Override
    protected void selection(int option, String action) {
        switch (option) {
            case 1:
                AUTHMENU.signUpManager(); // Navigue vers le sous-menu d'inscription
                break;
            case 2:
                AUTHMENU.logInManager();  // Navigue vers le sous-menu de connexion
                break;
            case 0:
                MaVille.exitApplication(); // Quitte l'application
                return;
            default:
                MenuView.printMessage("Option invalide, veuillez réessayer.");
                break;
        }
    }

    /**
     * Gère les options principales du menu utilisateur.
     *
     * @param option   L'option choisie par l'utilisateur.
     * @param userType Le type d'utilisateur connecté, soit "resident" ou "intervenant".
     * @return {@code true} si l'utilisateur souhaite quitter le menu, sinon {@code false}.
     */
    protected static boolean handleMainMenuOption(int option, String userType) {
        if (userType.equals("resident")) {
            ResidentActivityController residentAC = new ResidentActivityController();

            switch (option) {
                case 1:
                    residentAC.consultWorks(); // Consultation des travaux
                    break;
                case 2:
                    residentAC.consultRoadObstructions(); // Consultation des entraves routières
                    break;
                case 3:
                    residentAC.searchWorks(); // Recherche des travaux
                    break;
                case 4:
                    residentAC.participateToSchedule(); // Planification participative
                    break;
                case 5:
                    residentAC.submitWorkRequest(); // Soumission de requête de travaux
                    break;
                case 6:
                    residentAC.consultNotifications(); // Consultation des notifications
                    break;
                case 0:
                    return true; // Quitte le menu
                default:
                    MenuView.printMessage("Option invalide pour résident. Veuillez réessayer.");
                    break;
            }
        } else if (userType.equals("intervenant")) {
            IntervenantActivityController intervenantAC = new IntervenantActivityController();

            switch (option) {
                case 1:
                    intervenantAC.submitProject(); // Soumission de nouveaux travaux
                    break;
                case 2:
                    intervenantAC.updateProject(); // Mise à jour des travaux
                    break;
                case 3:
                    intervenantAC.consultWorkRequests(); // Consultation des requêtes de travaux
                    break;
                case 0:
                    return true; // Quitte le menu
                default:
                    MenuView.printMessage("Option invalide pour intervenant. Veuillez réessayer.");
                    break;
            }
        }

        return false; // Reste dans le menu
    }
}