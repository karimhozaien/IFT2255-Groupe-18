package main.java.prototype.auth;

import main.java.prototype.ApplicationMaVille;
import main.java.prototype.managers.UtilisateurManager;
import main.java.prototype.utils.GenerateurCodeDeHachage;
import main.java.prototype.entities.Intervenant;
import main.java.prototype.entities.Resident;

import java.util.Scanner;

public class Authentification {

    private final ApplicationMaVille app;
    private static final String CHEMIN_RELATIF_RESIDENT = "data/resident_data.json";
    private static final String CHEMIN_RELATIF_INTERVENANT = "data/intervenant_data.json";

    public enum OptionsEnregistrement {
        ENREGISTRER(1, "S'enregistrer"),
        CONNECTER(2, "Se connecter");

        private final int valeur;
        private final String description;

        OptionsEnregistrement(int valeur, String description) {
            this.valeur = valeur;
            this.description = description;
        }

        public int getValeur() {
            return valeur;
        }

        public String getDescription() {
            return description;
        }
    }

    public Authentification(ApplicationMaVille app) {
        this.app = app;
    }

    public boolean traiterChoixAuthentification(int choix) {
        for (OptionsEnregistrement option : OptionsEnregistrement.values()) {
            if (option.getValeur() == choix) {
                return switch (option) {
                    case ENREGISTRER -> app.gererEnregistrement();
                    case CONNECTER -> app.gererConnexion();
                };
            }
        }
        System.out.println("Option invalide. Veuillez réessayer.");
        return false;
    }

    public static boolean inscrireResident(Scanner scanner) {
        System.out.println("Inscription en tant que résident :");
        try {
            Resident nouveauResident = new Resident.Builder()
                    .nom(demanderInfo("Nom complet", scanner))
                    .dateNaissance(demanderInfo("Date de naissance (DD/MM/YYYY)", scanner))
                    .email(demanderInfo("Adresse courriel", scanner))
                    .mdp(demanderInfo("Mot de passe", scanner))
                    .telephone(demanderInfo("Numéro de téléphone (optionnel)", scanner))
                    .adresse(demanderInfo("Adresse résidentielle", scanner))
                    .type("Résident")
                    .build();

            try {
                UtilisateurManager manager = new UtilisateurManager();
                manager.sauvegardeUtilisateurAJSON(nouveauResident, CHEMIN_RELATIF_RESIDENT);
            } catch (Exception exception) {
                System.out.println("Impossible d'enregistrer les informations dans la base de données.");
                exception.printStackTrace();
                return false;
            }
        } catch (Exception exception) {
            System.out.println("L'information entrée est invalide. Veuillez réessayer.");
            return false;
        }
        return true;
    }

    public static boolean inscrireIntervenant(Scanner scanner) {
        System.out.println("Inscription en tant qu'intervenant :");
        try {
            Intervenant nouvelIntervenant = new Intervenant.Builder()
                    .nom(demanderInfo("Nom complet", scanner))
                    .email(demanderInfo("Adresse courriel", scanner))
                    .mdp(demanderInfo("Mot de passe", scanner))
                    .identifiant(demanderInfo("Identifiant de la ville", scanner))
                    .typeEntreprise(Intervenant.demanderTypeEntreprise(scanner))
                    .type("Intervenant")
                    .build();

            try {
                UtilisateurManager manager = new UtilisateurManager();
                manager.sauvegardeUtilisateurAJSON(nouvelIntervenant, CHEMIN_RELATIF_INTERVENANT);
            } catch (Exception exception) {
                System.out.println("Impossible d'enregistrer les informations dans la base de données.");
                exception.printStackTrace();
                return false;
            }
        } catch (Exception exception) {
            System.out.println("L'information entrée est invalide. Veuillez réessayer.");
            return false;
        }
        return true;
    }

    private static String demanderInfo(String etiquette, Scanner scanner) {
        System.out.print(etiquette + " : ");
        return scanner.nextLine();
    }


    public static String connexionUtilisateur(String nom, String mdp) {
        int codeDeHachage = GenerateurCodeDeHachage.generationCodeDeHachage(nom, mdp);

        return UtilisateurManager.rechercheUtilisateurDansJSON(codeDeHachage);
    }

    public static boolean itererTantQueConnexionUtilisateur(String nom, String mdp) {
        return connexionUtilisateur(nom, mdp) != null;
    }
}