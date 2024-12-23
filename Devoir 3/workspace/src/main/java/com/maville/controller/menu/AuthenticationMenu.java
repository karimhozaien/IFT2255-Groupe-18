package com.maville.controller.menu;

import com.maville.controller.services.Authenticate;
import com.maville.model.Intervenant;
import com.maville.view.AuthenticationView;
import com.maville.view.MenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu d'authentification permettant de gérer les connexions et inscriptions
 * des résidents et des intervenants. Fournit des options interactives pour
 * faciliter l'authentification des utilisateurs dans l'application.
 */
public class AuthenticationMenu extends Menu {
    private Authenticate authenticate;

    /**
     * Gère le processus de connexion pour les utilisateurs.
     * Permet à l'utilisateur de choisir son type (résident ou intervenant),
     * de saisir ses informations de connexion, et de continuer vers le menu principal.
     */
    public void logInManager() {
        while (true) {
            AuthenticationView.showAuthMessage();
            AuthenticationView.showAuthType();

            if (SCANNER.hasNextInt()) { // Vérifie si l'entrée est un entier
                int option = SCANNER.nextInt();
                selection(option, "login"); // Passe "login" en tant qu'argument pour indiquer la connexion
                break;
            } else {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                SCANNER.next(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
    }

    /**
     * Gère le processus d'inscription pour les utilisateurs.
     * Permet à l'utilisateur de choisir son type (résident ou intervenant),
     * de saisir ses informations de profil, et de compléter l'inscription.
     */
    public void signUpManager() {
        while (true) {
            AuthenticationView.showAuthMessage();
            AuthenticationView.showAuthType();

            if (SCANNER.hasNextInt()) { // Vérifie si l'entrée est un entier
                int option = SCANNER.nextInt();
                selection(option, "signup"); // Passe "signup" en tant qu'argument pour indiquer l'inscription
                break;
            } else {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                SCANNER.next(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
    }

    /**
     * Implémente la logique de sélection selon l'action (connexion ou inscription)
     * et le type d'utilisateur choisi.
     *
     * @param option L'option choisie par l'utilisateur.
     * @param action L'action à effectuer ("login" ou "signup").
     */
    @Override
    protected void selection(int option, String action) {
        switch (action) {
            case "login":
                handleLogIn(option);
                break;
            case "signup":
                handleSignUp(option);
                break;
            default:
                MenuView.printMessage("Action inconnue.");
                break;
        }
    }

    /**
     * Gère la logique de connexion en fonction du type d'utilisateur sélectionné.
     *
     * @param option L'option choisie par l'utilisateur (1 pour résident, 2 pour intervenant).
     */
    private void handleLogIn(int option) {
        String userType;
        switch (option) {
            case 1:
                userType = "resident";
                continueProcess(userType);
                break;
            case 2:
                userType = "intervenant";
                continueProcess(userType);
                break;
            case 0:
                MenuView.backMessage();
                return;
            default:
                MenuView.printMessage("Option invalide pour la connexion.");
        }
    }

    /**
     * Poursuit le processus de connexion en collectant les informations de l'utilisateur.
     *
     * @param userType Le type d'utilisateur (résident ou intervenant).
     */
    private void continueProcess(String userType) {
        AuthenticationView.showLogInMessage(userType);
        SCANNER.nextLine(); // Nettoie le buffer

        while (true) {
            authenticate = new Authenticate(collectUserInfo(AuthenticationView.LOGIN_INFO_MESSAGES));

            if (authenticate.logIn()) { // Authentification réussie
                String userTypeFromDB = Authenticate.getUserType();
                if (!userType.equals(userTypeFromDB)) {
                    MenuView.printMessage("Vous n'êtes pas un " + userType + ".");
                    return;
                }
                DefaultMenu.showUserMenu(userTypeFromDB); // Affiche le menu utilisateur
                break;
            } else {
                MenuView.printMessage("Connexion échouée. Veuillez réessayer.");
                return;
            }
        }
    }

    /**
     * Gère la logique d'inscription en fonction du type d'utilisateur sélectionné.
     *
     * @param option L'option choisie par l'utilisateur (1 pour résident, 2 pour intervenant).
     */
    private void handleSignUp(int option) {
        String userType;
        String[] infoMessages;

        switch (option) {
            case 1:
                userType = "resident";
                infoMessages = AuthenticationView.SIGNUP_RESIDENT_INFO_MESSAGES;
                break;
            case 2:
                userType = "intervenant";
                infoMessages = AuthenticationView.SIGNUP_INTERVENANT_INFO_MESSAGES;
                break;
            case 0:
                MenuView.backMessage();
                return;
            default:
                MenuView.printMessage("Option invalide pour l'inscription.");
                return;
        }

        AuthenticationView.showSignUpMessage(userType);
        SCANNER.nextLine(); // Nettoie le buffer

        authenticate = new Authenticate(collectUserInfo(infoMessages));
        if (authenticate.signUp(userType)) { // Inscription réussie
            MenuView.printMessage("Inscription réussie. Veuillez-vous connecter.");
        } else {
            MenuView.printMessage("Inscription échouée. Veuillez réessayer.");
        }
    }

    /**
     * Collecte les informations nécessaires pour la connexion ou l'inscription.
     *
     * @param infoMessages Les messages d'invite pour chaque champ d'information.
     * @return Une liste contenant les informations saisies par l'utilisateur.
     */
    private List<String> collectUserInfo(String[] infoMessages) {
        List<String> userInfo = new ArrayList<>();
        for (String message : infoMessages) {
            String input = "";
            boolean isValid = false;

            while (!isValid) {
                AuthenticationView.printMessage(message);
                input = SCANNER.nextLine();

                // Special validation for "Identifiant de la ville"
                if (message.equals("Identifiant de la ville")) {
                    if (input.matches("\\d{8}")) { // Matches exactly 8 digits
                        isValid = true;
                    } else {
                        AuthenticationView.printMessage("L'identifiant doit être un nombre composé de 8 chiffres. " +
                                "Veuillez réessayer.");
                    }
                } else {
                    // For all other messages, accept any input
                    isValid = true;
                }
            }

            userInfo.add(input);
        }
        return userInfo;
    }


    /**
     * Demande à l'utilisateur de sélectionner le type d'intervenant
     * parmi les types d'entreprises disponibles.
     *
     * @return Le numéro correspondant au type d'entreprise choisi.
     */
    public static int askForCompanyType() {
        Intervenant.CompanyType[] companyTypes = Intervenant.CompanyType.values();

        while (true) {
            AuthenticationView.showCompanyTypeMessage();
            AuthenticationView.showCompanyTypes(companyTypes);

            int choice = getInput();
            if (choice >= 1 && choice <= companyTypes.length) {
                return choice;
            } else {
                AuthenticationView.showInvalidChoiceMessage(companyTypes.length);
            }
        }
    }

    /**
     * Gère la saisie sécurisée d'un entier par l'utilisateur.
     *
     * @return L'entier saisi.
     */
    private static int getInput() {
        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                AuthenticationView.showInvalidInputMessage();
            }
        }
    }
}