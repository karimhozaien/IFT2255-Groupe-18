package main.java.prototype.entities;

import java.util.Scanner;

public class Intervenant implements Utilisateur {
    protected final String nom;
    protected final String email;
    protected final String mdp;
    protected final int identifiant;
    protected final TypeEntreprise typeEntreprise;
    protected final String type = "Intervenant"; // Set type explicitly

    /**
     * Crée trois constantes qui représentent le type d'entreprise :
     * {@link #PRIVEE}, {@link #PUBLIQUE} et {@link #PARTICULIER}.
     */
    public enum TypeEntreprise {
        PRIVEE(1, "Privée"),
        PUBLIQUE(2, "Publique"),
        PARTICULIER(3, "Particulier");

        private final int valeur;
        private final String affichageType;

        TypeEntreprise(int valeur, String affichageType) {
            this.valeur = valeur;
            this.affichageType = affichageType;
        }

        public String getAffichageType() {
            return affichageType;
        }

        public int getValeur() {
            return valeur;
        }

        /**
         * Sélectionne parmie les constantes de {@link #TypeEntreprise} le type d'entreprise sélectionné par l'utilisateur
         * 
         * @param choix entier représentant le choix de l'utilisateur
         */
        public static TypeEntreprise stringAType(int choix) {
            for (TypeEntreprise typeEntreprise : TypeEntreprise.values()) {
                if (typeEntreprise.getValeur() == choix) {
                    return typeEntreprise;
                }
            }
            throw new IllegalArgumentException("Ce type d'entreprise d'existe pas.");
        }
    }
    
    /**
     * Construit une instance {@link Intervenant} avec un {@link Builder}.
     *
     * @param builder le builder contenant les valeurs pour l'initilisation
     */
    public Intervenant(Builder builder) {
        this.nom = builder.nom;
        this.email = builder.email;
        this.mdp = builder.mdp;
        this.identifiant = builder.identifiant;
        this.typeEntreprise = builder.typeEntreprise;
    }

    public static class Builder {
        private String nom;
        private String email;
        private String mdp;
        private int identifiant;
        private TypeEntreprise typeEntreprise;

        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder mdp(String mdp) {
            this.mdp = mdp;
            return this;
        }

        public Builder identifiant(String identifiant) {
            this.identifiant = Integer.parseInt(identifiant);
            return this;
        }

        public Builder typeEntreprise(int choix) {
            this.typeEntreprise = TypeEntreprise.stringAType(choix);
            return this;
        }

        /**
         * Construit un objet de type {@link Intervenant} avec les paramètres reçus
         *
         * @return l'instance d'un objet {@link Intervenant}
         * @throws Exception si les champs nécessaires sont vides
         */
        public Intervenant build() throws Exception {
            if (this.nom == null || this.nom.trim().isEmpty()) {
                throw new Exception("Le nom ne peut pas être vide. Veuillez réessayer.");
            }
            if (this.email == null || this.email.trim().isEmpty()) {
                throw new Exception("L'email ne peut pas être vide. Veuillez réessayer.");
            }
            // Lancement d'erreurs
            return new Intervenant(this); // Retourne l'instanciation de l'intervenant
        }
    }

    // #####################
    // -----> Getters <-----
    // #####################
    @Override
    public String getNom() { return this.nom; }
    @Override
    public String getEmail() { return this.email; }
    @Override
    public String getMdp() { return this.mdp; }
    public int getIdentifiant() { return this.identifiant; }
    public TypeEntreprise getTypeEntreprise() { return this.typeEntreprise; }
    @Override
    public String getType() { return this.type; }

    // ##################################
    // -----> Méthodes auxiliaires <-----
    // ##################################
    /**
     * Demande à l'utilisateur de choisir un type d'entreprise en affichant les options et en lisant l'entrée de l'utilisateur. <p>
     * L'entrée est validée pour s'assurer qu'elle est un nombre entier valide et qu'elle correspond à l'une des options disponibles.
     *
     * @param scanner le scanner utilisé pour lire l'entrée de l'utilisateur
     * @return le choix de type d'entreprise de l'utilisateur sous forme d'entier
     */
    public static int demanderTypeEntreprise(Scanner scanner) {
        while (true) {
            System.out.println("Choisissez votre type d'entreprise : ");
            afficherTypesEntreprises();

            try {
                int choix = Integer.parseInt(scanner.nextLine().trim());
                int tailleOptions = TypeEntreprise.values().length;
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

    /**
     * Affiche les différents types d'entreprises disponibles.
     * Cette méthode parcourt toutes les valeurs de l'énumération {@link TypeEntreprise}.
     */
    private static void afficherTypesEntreprises() {
        for (TypeEntreprise typeEntreprise : TypeEntreprise.values()) {
            System.out.println("[" + typeEntreprise.getValeur() + "] " + typeEntreprise.getAffichageType());
        }
    }
}