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
                        while (!Authentication.inscrireResident(scanner)) {
                            System.out.println("Voulez-vous réessayer l'inscription en tant que résident ? (O/N)");
                            if (!scanner.nextLine().trim().toLowerCase().equals("o")) {
                                return false;
                            }
                        }
                        return true;
                    case INTERVENANT:
                        while (!Authentication.inscrireIntervenant(scanner)) {
                            System.out.println("Voulez-vous réessayer l'inscription en tant qu'intervenant ? (O/N)");
                            if (!scanner.nextLine().trim().toLowerCase().equals("o")) {
                                return false;
                            }
                        }
                        return true;
                }
            }
        }
        System.out.println("Option invalide. Veuillez réessayer.");
        return false;
    }
}