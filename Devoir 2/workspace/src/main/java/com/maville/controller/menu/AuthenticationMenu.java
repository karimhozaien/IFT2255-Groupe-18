package com.maville.controller.menu;

import com.maville.controller.auth.Authenticate;
import com.maville.view.AuthenticationView;

public class AuthenticationMenu extends Menu {
    private static final String[] LOGIN_INFO_MESSAGES = {
            "Adresse courriel",
            "Mot de passe"
    };
    private static final String[] SIGNUP_RESIDENT_INFO_MESSAGES = {
            "Nom complet",
            "Date de naissance (DD/MM/YYYY)",
            "Adresse courriel",
            "Mot de passe",
            "Numéro de téléphone (optionnel)",
            "Adresse résidentielle"
    };
    private static final String[] SIGNUP_INTERVENANT_INFO_MESSAGES = {
            "Nom complet",
            "Adresse courriel",
            "Mot de passe",
            "Identifiant de la ville"
    };

    // Logique de connexion
    public void logInManager() {
        AuthenticationView.authMessage();
        AuthenticationView.authType();

        int option = scanner.nextInt();
        selection(option, "login"); // Passe "login" en tant qu'argument pour indiquer la connexion
    }

    // Logique d'inscription
    public void signUpManager() {
        AuthenticationView.authMessage();
        AuthenticationView.authType();

        int option = scanner.nextInt();
        selection(option, "signup"); // Passe "signup" en tant qu'argument pour indiquer l'inscription
    }

    // Implémentation de la logique de sélection selon l'action (connexion ou inscription) et le type d'utilisateur
    @Override
    protected void selection(int option, String action) {
        switch (action) {
            case "login":
                AuthenticationView.logInMessage();
                scanner.nextLine();

                collectUserInfo(LOGIN_INFO_MESSAGES);
                break;
            case "signup":
                // Cas d'inscription
                handleSignUp(option);
                break;
            default:
                System.out.println("Action inconnue.");
                break;
        }
    }

    private void handleSignUp(int option) {
        String userType;
        String[] infoMessages;

        switch (option) {
            case 1:
                userType = "résident";
                infoMessages = SIGNUP_RESIDENT_INFO_MESSAGES;
                break;
            case 2:
                userType = "intervenant";
                infoMessages = SIGNUP_INTERVENANT_INFO_MESSAGES;
                break;
            default:
                System.out.println("Option invalide pour l'inscription.");
                return;
        }

        AuthenticationView.signUpMessage(userType);
        scanner.nextLine(); // Consomme la nouvelle ligne restante
        collectUserInfo(infoMessages);
    }

    private void collectUserInfo(String[] infoMessages) {
        for (String message : infoMessages) {
            AuthenticationView.printMessage(message);
            String input = scanner.nextLine();
            System.out.println("Vous avez entré : " + input); // Pour vérification
        }
    }
}
