package com.maville.view;

import com.maville.model.Intervenant;

import java.util.Map;
import java.util.TreeMap;

/**
 * Vue responsable de l'affichage des messages d'authentification pour les utilisateurs.
 * Cette classe fournit des méthodes statiques pour afficher des messages relatifs à la
 * connexion et l'inscription des résidents ou des intervenants.
 */
public class AuthenticationView extends MenuView {

    /**
     * Messages d'invite pour la connexion des utilisateurs.
     */
    public static final String[] LOGIN_INFO_MESSAGES = {
            "Adresse courriel",
            "Mot de passe"
    };

    /**
     * Messages d'invite pour l'inscription des résidents.
     */
    public static final String[] SIGNUP_RESIDENT_INFO_MESSAGES = {
            "Nom complet",
            "Date de naissance (DD/MM/YYYY)",
            "Adresse courriel",
            "Mot de passe",
            "Numéro de téléphone (optionnel)",
            "Adresse résidentielle"
    };

    /**
     * Messages d'invite pour l'inscription des intervenants.
     */
    public static final String[] SIGNUP_INTERVENANT_INFO_MESSAGES = {
            "Nom complet",
            "Adresse courriel",
            "Mot de passe",
            "Identifiant de la ville"
    };

    /**
     * Affiche un message demandant si l'utilisateur est un résident ou un intervenant.
     */
    public static void showAuthMessage() {
        printMessage("En tant que résident ou intervenant ?");
    }

    /**
     * Affiche un message indiquant que l'utilisateur est en mode connexion.
     *
     * @param userType Le type d'utilisateur (ex. : Résident, Intervenant).
     */
    public static void showLogInMessage(String userType) {
        printMessage("Connexion en tant que " + userType);
    }

    /**
     * Affiche un message indiquant que l'utilisateur est en mode inscription.
     *
     * @param userType Le type d'utilisateur (ex. : Résident, Intervenant).
     */
    public static void showSignUpMessage(String userType) {
        printMessage("Inscription en tant que " + userType);
    }

    /**
     * Affiche les options disponibles pour l'authentification.
     *
     * @param header  Le titre de la section.
     * @param options Une carte des options (clé : numéro, valeur : description).
     */
    public static void displayAuthOptions(String header, TreeMap<Integer, String> options) {
        printMessage("========================================");
        printMessage(header);
        printMessage("----------------------------------------");
        options.forEach((key, value) -> printMessage("[" + key + "] " + value));
        printMessage("========================================");
    }

    /**
     * Affiche les types d'authentification disponibles (résident ou intervenant).
     */
    public static void showAuthType() {
        TreeMap<Integer, String> authOptions = new TreeMap<>(Map.of(
                0, "Revenir en arrière",
                1, "Résident",
                2, "Intervenant"
        ));
        displayAuthOptions("Veuillez choisir votre type :", authOptions);
    }

    /**
     * Affiche un message demandant le type de l'entreprise.
     */
    public static void showCompanyTypeMessage() {
        printMessage("Quel est le type de votre entreprise ?");
    }

    /**
     * Affiche les types d'entreprises disponibles pour un intervenant.
     *
     * @param companyTypes Un tableau des types d'entreprises.
     */
    public static void showCompanyTypes(Intervenant.CompanyType[] companyTypes) {
        for (Intervenant.CompanyType companyType : companyTypes) {
            printMessage("[" + companyType.getValue() + "] " + companyType.getDisplayType());
        }
    }

    /**
     * Affiche un message d'erreur pour un choix invalide.
     *
     * @param maxChoice Le numéro maximum acceptable.
     */
    public static void showInvalidChoiceMessage(int maxChoice) {
        printMessage("Choix invalide. Veuillez entrer un nombre entre 1 et " + maxChoice);
    }

    /**
     * Affiche un message d'erreur pour une entrée invalide.
     */
    public static void showInvalidInputMessage() {
        printMessage("Veuillez entrer un nombre valide.");
    }
}