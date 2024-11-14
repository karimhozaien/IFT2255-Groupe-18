package com.maville.controller.menu;

import com.maville.controller.auth.Authenticate;
import com.maville.model.Intervenant;
import com.maville.view.AuthenticationView;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationMenu extends Menu {
    private Authenticate authenticate;
    private Menu menu;
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
        AuthenticationView.showAuthMessage();
        AuthenticationView.showAuthType();

        int option = SCANNER.nextInt();
        selection(option, "login"); // Passe "login" en tant qu'argument pour indiquer la connexion
    }

    // Logique d'inscription
    public void signUpManager() {
        AuthenticationView.showAuthMessage();
        AuthenticationView.showAuthType();

        int option = SCANNER.nextInt();
        selection(option, "signup"); // Passe "signup" en tant qu'argument pour indiquer l'inscription
    }

    // Implémentation de la logique de sélection selon l'action (connexion ou inscription) et le type d'utilisateur
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
                System.out.println("Action inconnue.");
                break;
        }
    }

    private void handleLogIn(int option) {
        String userType;
        switch (option) {
            case 1:
                userType = "resident";
                break;
            case 2:
                userType = "intervenant";
                break;
            default:
                System.out.println("XXXXXX");
                return;
        }
        AuthenticationView.showLogInMessage(userType);
        SCANNER.nextLine();

        authenticate = new Authenticate(collectUserInfo(LOGIN_INFO_MESSAGES));

        if (authenticate.logIn()) { // Construction du User
            menu.showUserMenu(userType);
        }
    }

    private void handleSignUp(int option) {
        String userType;
        String[] infoMessages;

        switch (option) {
            case 1:
                userType = "resident";
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

        AuthenticationView.showSignUpMessage(userType);
        SCANNER.nextLine();

        authenticate = new Authenticate(collectUserInfo(infoMessages));
        if (authenticate.signUp(userType)) { // Construction du User
            menu.showUserMenu(userType);
        }
    }

    private List<String> collectUserInfo(String[] infoMessages) {
        List<String> userInfo = new ArrayList<>();
        for (String message : infoMessages) {
            AuthenticationView.printMessage(message);
            String input = SCANNER.nextLine();
            userInfo.add(input);
            System.out.println("Vous avez entré : " + input); // Pour vérification
        }

        return userInfo;
    }

    private static int getInput() {
        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                AuthenticationView.showInvalidInputMessage();
            }
        }
    }

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
}
