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

class ApplicationMaVille {
    private Scanner scanner;

    public ApplicationMaVille() {
        this.scanner = new Scanner(System.in);
    }

    public void demarrer() {
        afficherMenuPrincipal();
        
        while (true) {
            try {
                System.out.print("Votre choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine();
                
                if (traiterChoix(choix)) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }
    }

    private void afficherMenuPrincipal() {
        System.out.println("Bienvenue sur l'application MaVille de la ville de Montréal !");
        System.out.println("Voulez-vous vous inscrire en tant que résident ou en tant qu'intervenant ?");
        for (OptionInscription option : OptionInscription.values()) {
            System.out.println("[" + option.getValeur() + "] " + option.getDescription());
        }
    }

    private boolean traiterChoix(int choix) {
        for (OptionInscription option : OptionInscription.values()) {
            if (option.getValeur() == choix) {
                switch (option) {
                    case RESIDENT:
                        inscrireResident();
                        return true;
                    case INTERVENANT:
                        inscrireIntervenant();
                        return true;
                }
            }
        }
        System.out.println("Option invalide. Veuillez réessayer.");
        return false;
    }

    private void inscrireResident() {
        System.out.println("Inscription en tant que résident :");
        String nom = demanderInfo("Nom complet");
        String naissance = demanderInfo("Date de naissance (DD/MM/YYYY)");
        String email = demanderInfo("Adresse courriel");
        String mdp = demanderInfo("Mot de passe");
        String telephone = demanderInfo("Numéro de téléphone (optionnel)");
        String adresse = demanderInfo("Adresse résidentielle");
    }

    private void inscrireIntervenant() {
        System.out.println("Inscription en tant qu'intervenant :");
        String nom = demanderInfo("Nom complet");
        String email = demanderInfo("Adresse courriel");
        String mdp = demanderInfo("Mot de passe");
        String entreprise = demanderInfo("Type d'entreprise");
        String identifiant = demanderInfo("Identifiant de la ville");
    }

    private String demanderInfo(String label) {
        System.out.print(label + " : ");
        return scanner.nextLine();
    }
}