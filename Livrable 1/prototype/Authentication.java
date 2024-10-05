import java.util.Scanner;

class Authentication {
    public static boolean inscrireResident(Scanner scanner) {
        System.out.println("Inscription en tant que résident :");

        try {
            Resident nouveauResident = new Resident.ResidentBuilder()
            .nom(demanderInfo("Nom complet", scanner))
            .dateNaissance(demanderInfo("Date de naissance (DD/MM/YYYY)", scanner))
            .email(demanderInfo("Adresse courriel", scanner))
            .mdp(demanderInfo("Mot de passe", scanner))
            .telephone(demanderInfo("Numéro de téléphone (optionnel)", scanner))
            .adresse(demanderInfo("Adresse résidentielle", scanner))
            .build();
        } catch (Exception exception) {
            System.out.println("L'information entrée est invalide. Veuillez réessayer.");
            return false;
        }
        
        return true;
    }

    public static boolean inscrireIntervenant(Scanner scanner) {
        System.out.println("Inscription en tant qu'intervenant :");

        try {
            Intervenant nouveauIntervenant = new Intervenant.IntervenantBuilder()
                .nom(demanderInfo("Nom complet", scanner))
                .email(demanderInfo("Adresse courriel", scanner))
                .mdp(demanderInfo("Mot de passe", scanner))
                .typeEntreprise(demanderTypeEntreprise(scanner))
                .identifiant(demanderInfo("Identifiant de la ville", scanner))
                .build();
            
            System.out.println("Inscription réussie !");
            return true;
        } catch (IllegalArgumentException iae) {
            System.out.println("Une erreur inattendue s'est produite : " + iae.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur inattendue s'est produite : " + e.getMessage());
        }

        return true;
    }

    private static String demanderInfo(String etiquette, Scanner scanner) {
        System.out.print(etiquette + " : ");
        return scanner.nextLine();
    }

    private static int demanderTypeEntreprise(Scanner scanner) {
        while (true) {
            System.out.println("Choisissez votre type d'entreprise : ");
            afficherTypesEntreprises();
            
            try {
                int choix = Integer.parseInt(scanner.nextLine().trim());
                int tailleOptions = Intervenant.TypeEntreprise.values().length;
                if (choix >= 1 && choix <= tailleOptions) {
                    return choix;
                } else {
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et " + tailleOptions);
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    private static void afficherTypesEntreprises() {
        for (Intervenant.TypeEntreprise typeEntreprise : Intervenant.TypeEntreprise.values()) {
            System.out.println("[" + typeEntreprise.getValeur() + "] " + typeEntreprise.getAffichageType());
        }
    }
}
