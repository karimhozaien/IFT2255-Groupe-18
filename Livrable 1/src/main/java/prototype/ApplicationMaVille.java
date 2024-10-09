package main.java.prototype;

import main.java.prototype.auth.Authentification;
import main.java.prototype.managers.UtilisateurManager;
import main.java.prototype.ui.Menu2;

import java.util.Scanner;

/**
 * Crée deux constantes qui représentent le titre de l'utilisateur :
 * {@link #RESIDENT} et {@link #INTERVENANT}.
 */
enum OptionInscription {
    RESIDENT(1, "Résident"),
    INTERVENANT(2, "Intervenant");

    private final int VALEUR;
    private final String DESCRIPTION;

    OptionInscription(int valeur, String description) {
        this.VALEUR = valeur;
        this.DESCRIPTION = description;
    }

    public int getValeur() {
        return VALEUR;
    }
    public String getDescription() {
        return DESCRIPTION;
    }
}

public class ApplicationMaVille {
    private final Scanner scanner;
    private final Authentification auth;
    private static final String CHEMIN_ABSOLU = "/Users/mathiaslarochelle/Documents/School/A24/IFT2255/Projets/Projet 1/Livrable 1/ressources/data/user_data.json";
    /**
     * Crée un objet Scanner lors de l'appel du constructeur
     * pour capturer l'entrée utilisateur.
     * 
     * @see java.util.Scanner
     */
    public ApplicationMaVille() {
        this.scanner = new Scanner(System.in);
        this.auth = new Authentification(this);
    }

    /**
     * Affiche le menu principal. 
     * <p>
     * Ensuite, demande à l'utilisateur de sélectionner un mode 
     * d'enregistrement et traite l'entrée de l'utilisateur. 
     * <p>
     * En cas d'entrée invalide, un message d'erreur est affiché et l'utilisateur 
     * est invité à réessayer.
     */
    public void demarrer() {
        while (true) {
            afficherOptionsAuthentification();
            try {
                System.out.print("Votre choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (auth.traiterChoixAuthentification(choix)) {
                    System.out.println("Merci d'avoir utilisé l'application MaVille. Au revoir!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    private void afficherOptionsAuthentification() {
        System.out.println("Bienvenue sur l'application MaVille de la ville de Montréal !");
        System.out.println("Que souhaitez-vous faire ?");
        for (Authentification.OptionsEnregistrement option : Authentification.OptionsEnregistrement.values()) {
            System.out.println("[" + option.getValeur() + "] " + option.getDescription());
        }
    }

    /**
     * Gère le processus d'enregistrement en affichant le menu approprié
     * et en traitant le choix de l'utilisateur.
     *
     * @return true si le traitement du choix d'enregistrement a réussi, false sinon
     */
    public boolean gererEnregistrement() {
        afficherMenuEnregistrement();
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();
        scanner.nextLine(); // consume newline

        return traiterChoix(choix);
    }
    
    /**
     * Gère le processus de connexion en affichant le menu approprié
     * et implémente la logique nécessaire pour traiter la connexion.
     *
     * @return false, la fonctionnalité de connexion n'étant pas implémentée
     */
    public boolean gererConnexion() {
        // Implémentez ici la logique de connexion

        return traiterConnexion();
    }

    /**
     * Affiche les messages du menu principal. 
     * <p>
     * Fait de même pour les différentes options qui s'offrent à l'utilisateur.
     */
    private void afficherMenuEnregistrement() {
        System.out.println("Voulez-vous vous inscrire en tant que résident ou en tant qu'intervenant ?");
        for (OptionInscription option : OptionInscription.values()) {
            System.out.println("[" + option.getValeur() + "] " + option.getDescription());
        }
    }

    private boolean traiterConnexion() {
        System.out.println("Entrez vos informations de connexion :");
        System.out.println("Nom d'utilisateur : ");
        String nomUtilisateur = scanner.nextLine();
        System.out.println("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        while (!Authentification.connexionUtilisateur(nomUtilisateur, motDePasse)) {
            System.out.println("Voulez-vous réessayer votre connexion ? (O/N)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
                return false; // Sortir si l'utilisateur ne veut pas réessayer
            }
        }
        System.out.println("Connexion réussie !");
        // Passer du type récupérer au Menu2
        String typeDeUtilisateur = UtilisateurManager.recupererTypeUtilisateur(nomUtilisateur, motDePasse, CHEMIN_ABSOLU);
        new Menu2(scanner, typeDeUtilisateur).afficherMenu();
        return true;
    }

    /**
     * Traite l'option choisie dans la méthode {@link #demarrer()}. 
     * <p>
     * Cette option correspond au type de compte que l'utilisateur souhaite créer. 
     * <p>
     * À des fins de clarification, le bloc de code suivant est utilisé pour vérifier si l'utilisateur souhaite 
     * réessayer l'inscription et retourne false si la réponse est différente de "o" :
     * 
     * @param choix entier représentant le choix de l'utilisateur
     * <p>
     */
    public boolean traiterChoix(int choix) {
        for (OptionInscription option : OptionInscription.values()) {
            if (option.getValeur() == choix) {
                switch (option) {
                    case RESIDENT:
                        // Essayer d'inscrire le résident
                        while (!Authentification.inscrireResident(scanner)) {
                            System.out.println("Voulez-vous réessayer l'inscription en tant que résident ? (O/N)");
                            if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
                                return false; // Sortir si l'utilisateur ne veut pas réessayer
                            }
                        }
                        System.out.println("Inscription réussie !");
                        // Passer "Résident" à Menu2
                        new Menu2(scanner, "Résident").afficherMenu(); // Afficher le menu spécifique aux Résidents
                        return true;
                    case INTERVENANT:
                        // Essayer d'inscrire l'intervenant
                        while (!Authentification.inscrireIntervenant(scanner)) {
                            System.out.println("Voulez-vous réessayer l'inscription en tant qu'intervenant ? (O/N)");
                            if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
                                return false; // Sortir si l'utilisateur ne veut pas réessayer
                            }
                        }
                        System.out.println("Inscription réussie !");
                        // Passer "Intervenant" à Menu2
                        new Menu2(scanner, "Intervenant").afficherMenu(); // Afficher le menu spécifique aux Intervenants
                        return true;
                }
            }
        }
        // Si l'option n'est pas valide
        System.out.println("Option invalide. Veuillez réessayer.");
        return false;
    }
    
    public static void main(String[] args) {
        ApplicationMaVille app = new ApplicationMaVille();
        app.demarrer();
    }
}
