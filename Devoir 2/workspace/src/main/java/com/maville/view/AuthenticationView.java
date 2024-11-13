package com.maville.view;

import com.maville.model.Intervenant;

public class AuthenticationView extends MenuView {
    private static final String AUTH_TYPE_MESSAGE = "En tant que résident ou intervenant ?";
    private static final String COMPANY_TYPE_MESSAGE = "Quel est le type de votre entreprise ?";
    private static final String INVALID_INPUT_MESSAGE = "Veuillez entrer un nombre valide.";
    private static final String[] USER_TYPES = {
            "Résident",
            "Intervenant"
    };

    public static void showAuthMessage() {
        printMessage(AUTH_TYPE_MESSAGE);
    }

    public static void showLogInMessage(String userType) {
        printMessage("Inscription en tant que " + userType);
    }

    public static void showSignUpMessage(String userType) {
        printMessage("Inscription en tant que " + userType);
    }

    public static void showAuthType() {
        for (int i = 0; i < USER_TYPES.length; i++) {
            printMessage("[" + (i + 1) + "] " + USER_TYPES[i]);
        }
    }

    public static void showCompanyTypeMessage() {
        printMessage(COMPANY_TYPE_MESSAGE);
    }

    public static void showCompanyTypes(Intervenant.CompanyType[] companyTypes) {
        for (Intervenant.CompanyType companyType : companyTypes) {
            printMessage("[" + companyType.getValue() + "] " + companyType.getDisplayType());
        }
    }

    public static void showInvalidChoiceMessage(int maxChoice) {
        printMessage("Choix invalide. Veuillez entrer un nombre entre 1 et " + maxChoice);
    }

    public static void showInvalidInputMessage() {
        printMessage(INVALID_INPUT_MESSAGE);
    }
}
