import java.util.Scanner;

/**
 * Crée deux constantes qui représentent le titre de l'utilisateur :
 * {@link #RESIDENT} et {@link #INTERVENANT}.
 */
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

class ApplicationMaVille {
    private Scanner scanner;

    /**
     * Crée un objet Scanner lors de l'appel du constructeur
     * pour capturer l'entrée utilisateur.
     * 
     * @see java.util.Scanner
     */
    public ApplicationMaVille() {
        this.scanner = new Scanner(System.in);
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
            afficherMenuPrincipal();
            try {
                System.out.print("Votre choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (traiterChoix(choix)) {
                    System.out.println("Merci d'avoir utilisé l'application MaVille. Au revoir!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    /**
     * Affiche les messages du menu principal. 
     * <p>
     * Fait de même pour les différentes options qui s'offrent à l'utilisateur.
     */
    private void afficherMenuPrincipal() {
        System.out.println("Bienvenue sur l'application MaVille de la ville de Montréal !");
        System.out.println("Voulez-vous vous inscrire en tant que résident ou en tant qu'intervenant ?");
        for (OptionInscription option : OptionInscription.values()) {
            System.out.println("[" + option.getValeur() + "] " + option.getDescription());
        }
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
    private boolean traiterChoix(int choix) {
        for (OptionInscription option : OptionInscription.values()) {
            if (option.getValeur() == choix) {
                switch (option) {
                    case RESIDENT:
                        // Essayer d'inscrire le résident
                        while (!Authentication.inscrireResident(scanner)) {
                            System.out.println("Voulez-vous réessayer l'inscription en tant que résident ? (O/N)");
                            if (!scanner.nextLine().trim().toLowerCase().equals("o")) {
                                return false; // Sortir si l'utilisateur ne veut pas réessayer
                            }
                        }
                        // Passer "Résident" à Menu2
                        new Menu2(scanner, "Résident").afficherMenu(); // Afficher le menu spécifique aux Résidents
                        return true;
                    case INTERVENANT:
                        // Essayer d'inscrire l'intervenant
                        while (!Authentication.inscrireIntervenant(scanner)) {
                            System.out.println("Voulez-vous réessayer l'inscription en tant qu'intervenant ? (O/N)");
                            if (!scanner.nextLine().trim().toLowerCase().equals("o")) {
                                return false; // Sortir si l'utilisateur ne veut pas réessayer
                            }
                        }
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
