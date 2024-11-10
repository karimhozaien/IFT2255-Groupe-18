package main.java.prototype.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Scanner;

public class Intervenant extends Utilisateur {
    @SerializedName("identifiant")
    protected int identifiant;

    @SerializedName("typeEntreprise")
    protected TypeEntreprise typeEntreprise;

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

        public static TypeEntreprise stringAType(int choix) {
            for (TypeEntreprise typeEntreprise : TypeEntreprise.values()) {
                if (typeEntreprise.getValeur() == choix) {
                    return typeEntreprise;
                }
            }
            throw new IllegalArgumentException("Ce type d'entreprise d'existe pas.");
        }
    }

    private Intervenant(Builder builder) {
        super.nom = builder.nom;
        super.email = builder.email;
        super.mdp = builder.mdp;
        this.identifiant = builder.identifiant;
        this.typeEntreprise = builder.typeEntreprise;
        this.type = builder.type;
    }

    public static class Builder extends Utilisateur.Builder {
        private int identifiant;
        private TypeEntreprise typeEntreprise;
        private String type;

        public Builder() {
            this.identifiant = build().getIdentifiant();
            this.typeEntreprise = build().getTypeEntreprise();
        }

        @Override
        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        @Override
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        @Override
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

        @Override
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public Intervenant build() {
            return new Intervenant(this);
        }
    }

    // #####################
    // -----> Getters <-----
    // #####################
    public int getIdentifiant() { return this.identifiant; }
    public TypeEntreprise getTypeEntreprise() { return this.typeEntreprise; }

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