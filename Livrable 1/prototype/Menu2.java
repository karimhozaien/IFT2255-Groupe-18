import java.util.Scanner; // Importation de la classe Scanner

class Menu2 {
    private Scanner scanner;
    private String typeUtilisateur;

    /**
     * Constructeur qui initialise le scanner et le type d'utilisateur.
     * 
     * @param scanner Scanner pour capturer l'entrée utilisateur
     * @param typeUtilisateur Type d'utilisateur (Résident ou Intervenant)
     */
    public Menu2(Scanner scanner, String typeUtilisateur) {
        this.scanner = scanner;
        this.typeUtilisateur = typeUtilisateur;
    }

    /**
     * Affiche le menu spécifique en fonction du type d'utilisateur
     * et traite le choix de l'utilisateur.
     */
    public void afficherMenu() {
        while (true) {
            System.out.println("Menu " + typeUtilisateur + ":");
            afficherOptions();
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // Traiter le choix
            if (traiterChoix(choix)) {
                break; // Sortir si l'utilisateur a choisi de quitter
            }
        }
    }

    /**
     * Affiche les options disponibles dans le menu.
     */
    private void afficherOptions() {
        if (typeUtilisateur.equals("Résident")) {
            System.out.println("[1] Consultation des travaux");
            System.out.println("[2] Rechercher des travaux");
            System.out.println("[3] Signaler un problème");
            System.out.println("[4] Soumettre une requête de travaux");
            System.out.println("[5] Recevoir des notifications");
            System.out.println("[0] Quitter"); // Option pour quitter
        } else if (typeUtilisateur.equals("Intervenant")) {
            System.out.println("[1] Soumettre de nouveaux travaux");
            System.out.println("[2] Mettre à jour les travaux");
            System.out.println("[0] Quitter"); // Option pour quitter
        }
    }

/**
 * Traite le choix de l'utilisateur dans le menu en fonction du type d'utilisateur.
 * 
 * @param choix Choix de l'utilisateur
 * @return true si l'utilisateur souhaite quitter, false sinon
 */
private boolean traiterChoix(int choix) {
    if (typeUtilisateur.equals("Résident")) {
        switch (choix) {
            case 1:
                // Consultation des travaux
                System.out.println("Consultation des travaux...");
                // Ajoutez votre logique ici
                break;
            case 2:
                // Rechercher des travaux
                System.out.println("Recherche des travaux...");
                // Ajoutez votre logique ici
                break;
            case 3:
                // Signaler un problème
                System.out.println("Signalement d'un problème...");
                // Ajoutez votre logique ici
                break;
            case 4:
                // Soumettre une requête de travaux
                System.out.println("Soumission d'une requête de travaux...");
                // Ajoutez votre logique ici
                break;
            case 5:
                // Recevoir des notifications
                System.out.println("Réception des notifications...");
                // Ajoutez votre logique ici
                break;
            case 0: // Option pour quitter
                System.out.println("Merci d'avoir utilisé l'application MaVille. Au revoir!");
                return true; // Indique que l'utilisateur souhaite quitter
            default:
                System.out.println("Option invalide pour résident. Veuillez réessayer.");
                break;
        }
    } else if (typeUtilisateur.equals("Intervenant")) {
        switch (choix) {
            case 1:
                // Soumettre de nouveaux travaux
                System.out.println("Soumission de nouveaux travaux...");
                // Ajoutez votre logique ici
                break;
            case 2:
                // Mettre à jour les travaux
                System.out.println("Mise à jour des travaux...");
                // Ajoutez votre logique ici
                break;
            case 0: // Option pour quitter
                return true; // Indique que l'utilisateur souhaite quitter
            default:
                System.out.println("Option invalide pour intervenant. Veuillez réessayer.");
                break;
        }
    }

    return false; // Indique que l'utilisateur ne souhaite pas quitter
}

}
