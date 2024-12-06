package com.maville.view;

import com.maville.model.Intervenant;

public class AuthenticationView extends MenuView {
    public static void showAuthMessage() {
        printMessage("En tant que résident ou intervenant ?");
    }

    public static void showLogInMessage(String userType) {
        printMessage("Connexion en tant que " + userType);
    }

    public static void showSignUpMessage(String userType) {
        printMessage("Inscription en tant que " + userType);
    }

    public static void showAuthType() {
        printMessage("[0] Revenir en arrière");
        printMessage("[1] Résident");
        printMessage("[2] Intervenant");
    }

    public static void showCompanyTypeMessage() {
        printMessage("Quel est le type de votre entreprise ?");
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
        printMessage("Veuillez entrer un nombre valide.");
    }
}
