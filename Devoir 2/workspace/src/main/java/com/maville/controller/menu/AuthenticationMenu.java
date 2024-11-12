package com.maville.controller.menu;

import com.maville.controller.auth.Authenticate;
import com.maville.model.Intervenant;
import com.maville.view.AuthenticationView;
import com.maville.view.MenuView;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationMenu extends Menu {
    private Authenticate authenticate;
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
        AuthenticationView.showAuthType();

        int option = scanner.nextInt();
        selection(option, "login"); // Passe "login" en tant qu'argument pour indiquer la connexion
    }

    // Logique d'inscription
    public void signUpManager() {
        AuthenticationView.authMessage();
        AuthenticationView.showAuthType();

        int option = scanner.nextInt();
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
        scanner.nextLine();

        authenticate = new Authenticate(collectUserInfo(LOGIN_INFO_MESSAGES));

        if (authenticate.logIn()) { // Construction du User
            Menu.showUserMenu(userType);
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
        scanner.nextLine();

        authenticate = new Authenticate(collectUserInfo(infoMessages));
        if (authenticate.signUp(userType)) { // Construction du User
            Menu.showUserMenu(userType);
        }
    }

    private List<String> collectUserInfo(String[] infoMessages) {
        List<String> userInfo = new ArrayList<>();
        for (String message : infoMessages) {
            AuthenticationView.printMessage(message);
            String input = scanner.nextLine();
            userInfo.add(input);
            System.out.println("Vous avez entré : " + input); // Pour vérification
        }

        return userInfo;
    }

    public static int getInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                AuthenticationView.showInvalidInputMessage();
            }
        }
    }

    public static int askForCompanyType() {
        Intervenant.CompanyType[] companyTypes = Intervenant.CompanyType.values();

        while (true) {
            AuthenticationView.showCompanyTypePrompt();
            AuthenticationView.showCompanyTypes(companyTypes);

            int choice = getInput();
            if (choice >= 1 && choice <= companyTypes.length) {
                return choice;
            } else {
                AuthenticationView.showInvalidChoiceMessage(companyTypes.length);
            }
        }
    }


    // Méthode pour demander un nouvel identifiant
    public static String demanderNouvelIdentifiant() {
        System.out.print("Entrez un identifiant valide (8 chiffres) : ");
        return scanner.nextLine();
    }
}