package com.maville.view;

import com.maville.model.Intervenant;

import java.util.Map;
import java.util.TreeMap;

public class AuthenticationView extends MenuView {
    public static final String[] LOGIN_INFO_MESSAGES = {
            "Adresse courriel",
            "Mot de passe"
    };
    public static final String[] SIGNUP_RESIDENT_INFO_MESSAGES = {
            "Nom complet",
            "Date de naissance (DD/MM/YYYY)",
            "Adresse courriel",
            "Mot de passe",
            "Numéro de téléphone (optionnel)",
            "Adresse résidentielle"
    };
    public static final String[] SIGNUP_INTERVENANT_INFO_MESSAGES = {
            "Nom complet",
            "Adresse courriel",
            "Mot de passe",
            "Identifiant de la ville"
    };

    public static void showAuthMessage() {
        printMessage("En tant que résident ou intervenant ?");
    }

    public static void showLogInMessage(String userType) {
        printMessage("Connexion en tant que " + userType);
    }

    public static void showSignUpMessage(String userType) {
        printMessage("Inscription en tant que " + userType);
    }

    public static void displayAuthOptions(String header, TreeMap<Integer, String> options) {
        printMessage("========================================");
        printMessage(header);
        printMessage("----------------------------------------");
        options.forEach((key, value) -> printMessage("[" + key + "] " + value));
        printMessage("========================================");
    }

    public static void showAuthType() {
        // Utilisation de la méthode `displayAuthOptions`
        TreeMap<Integer, String> authOptions = new TreeMap<>(Map.of(
                0, "Revenir en arrière",
                1, "Résident",
                2, "Intervenant"
        ));
        displayAuthOptions("Veuillez choisir votre type :", authOptions);
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
