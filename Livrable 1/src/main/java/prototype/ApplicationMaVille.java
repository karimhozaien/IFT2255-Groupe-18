package main.java.prototype;

import main.java.prototype.auth.Authentification;
import main.java.prototype.ui.MenuPrincipal;

import java.util.Scanner;

enum OptionInscription {
    RESIDENT(1, "Résident"),
    INTERVENANT(2, "Intervenant");

    private final int valeur;
    private final String description;

    OptionInscription(int valeur, String description) {
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

public class ApplicationMaVille {
    private final Scanner scanner = new Scanner(System.in);
    private final Authentification auth = new Authentification(this);

    public void demarrer() {
        while (true) {
            afficherOptionsAuthentification();
            System.out.print("Votre choix : ");
            try {
                int choix = scanner.nextInt();
                scanner.nextLine();
                if (auth.traiterChoixAuthentification(choix)) {
                    quitter();
                }
            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine();
                break;
            }
        }
    }

    public void quitter() {
        System.out.println("Merci d'avoir utilisé l'application MaVille. Au revoir!");
        System.exit(0); // Termine le programme
    }

    private void afficherOptionsAuthentification() {
        System.out.println("Bienvenue sur l'application MaVille de la ville de Montréal !");
        System.out.println("Que souhaitez-vous faire ?");
        for (Authentification.OptionsEnregistrement option : Authentification.OptionsEnregistrement.values()) {
            System.out.println("[" + option.getValeur() + "] " + option.getDescription());
        }
    }

    public boolean gererEnregistrement() {
        return gererChoix("Voulez-vous vous inscrire en tant que résident ou intervenant ?",
                OptionInscription.values());
    }

    public boolean gererConnexion() {
        System.out.println("Entrez vos informations de connexion :");
        return traiterConnexion();
    }

    private boolean gererChoix(String message, OptionInscription[] options) {
        System.out.println(message);
        for (OptionInscription option : options) {
            System.out.println("[" + option.getValeur() + "] " + option.getDescription());
        }
        System.out.print("Votre choix : ");
        return traiterChoix(scanner.nextInt());
    }

    private boolean traiterConnexion() {
        while (true) {
            System.out.print("Nom d'utilisateur : ");
            String nomUtilisateur = scanner.nextLine();
            System.out.print("Mot de passe : ");
            String motDePasse = scanner.nextLine();

            // Vérifie la connexion
            if (Authentification.itererTantQueConnexionUtilisateur(nomUtilisateur, motDePasse)) {
                System.out.println("Connexion réussie !");
                new MenuPrincipal(scanner, Authentification.connexionUtilisateur(nomUtilisateur, motDePasse)).afficherMenu();
                return true; // Connexion réussie
            }

            // Si la connexion échoue
            System.out.println("Échec de la connexion. Voulez-vous réessayer ? (O/N)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
                quitter(); // Sortie si l'utilisateur ne veut pas réessayer
            }
        }
    }


    public boolean traiterChoix(int choix) {
        for (OptionInscription option : OptionInscription.values()) {
            if (option.getValeur() == choix) {
                return inscrireUtilisateur(option);
            }
        }
        System.out.println("Option invalide. Veuillez réessayer.");
        return false;
    }

    private boolean inscrireUtilisateur(OptionInscription option) {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            boolean success;
            if (option == OptionInscription.RESIDENT) {
                success = Authentification.inscrireResident(inputScanner);
            } else {
                success = Authentification.inscrireIntervenant(inputScanner);
            }

            if (success) {
                System.out.println("Inscription réussie !");
                new MenuPrincipal(scanner, option.getDescription()).afficherMenu();
                return true;
            }

            System.out.printf("Voulez-vous réessayer l'inscription en tant que %s ? (O/N)%n", option.getDescription());
            if (!inputScanner.nextLine().trim().equalsIgnoreCase("o")) {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        new ApplicationMaVille().demarrer();
    }
}
