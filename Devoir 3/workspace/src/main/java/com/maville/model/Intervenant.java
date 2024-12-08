package com.maville.model;

import com.maville.view.MenuView;

/**
 * Classe représentant un intervenant dans le système Maville.
 * Un intervenant est un utilisateur spécialisé associé à un type d'entreprise et disposant d'un identifiant unique.
 */
public class Intervenant extends User {
    private final int identifier;
    private final CompanyType companyType;

    /**
     * Enumération pour les types d'entreprises auxquels un intervenant peut appartenir.
     */
    public enum CompanyType {
        PRIVATE(1, "Privée"),
        PUBLIC(2, "Publique"),
        INDIVIDUAL(3, "Individuel");

        private final int value;
        private final String displayType;

        /**
         * Constructeur pour l'énumération CompanyType.
         *
         * @param value       La valeur numérique associée au type.
         * @param displayType La représentation textuelle du type.
         */
        CompanyType(int value, String displayType) {
            this.value = value;
            this.displayType = displayType;
        }

        /**
         * Obtient la représentation textuelle du type.
         *
         * @return Une chaîne représentant le type.
         */
        public String getDisplayType() {
            return displayType;
        }

        /**
         * Obtient la valeur numérique associée au type.
         *
         * @return La valeur numérique.
         */
        public int getValue() {
            return value;
        }

        /**
         * Convertit une valeur numérique en type d'entreprise.
         *
         * @param choice La valeur numérique représentant un type d'entreprise.
         * @return Le type d'entreprise correspondant.
         * @throws IllegalArgumentException Si la valeur ne correspond à aucun type.
         */
        public static CompanyType stringToType(int choice) {
            for (CompanyType companyType : CompanyType.values()) {
                if (companyType.getValue() == choice) {
                    return companyType;
                }
            }
            throw new IllegalArgumentException("Ce type d'entreprise n'existe pas.");
        }
    }

    /**
     * Constructeur privé pour la classe Intervenant, utilisé par le Builder.
     *
     * @param builder L'objet Builder contenant les données nécessaires pour créer un Intervenant.
     */
    private Intervenant(IntervenantBuilder builder) {
        super(builder);
        this.identifier = builder.identifier;
        this.companyType = builder.companyType;
    }

    /**
     * Classe interne pour construire un objet Intervenant de manière fluide.
     */
    public static class IntervenantBuilder extends User.Builder<IntervenantBuilder> {
        private int identifier;
        private CompanyType companyType;

        /**
         * Définit l'identifiant unique de l'intervenant.
         *
         * @param identifier L'identifiant sous forme de chaîne.
         * @return L'instance courante de IntervenantBuilder.
         */
        public IntervenantBuilder identifier(String identifier) {
            this.identifier = Integer.parseInt(identifier);
            return this;
        }

        /**
         * Définit le type d'entreprise de l'intervenant.
         *
         * @param choice La valeur numérique représentant le type d'entreprise.
         * @return L'instance courante de IntervenantBuilder.
         */
        public IntervenantBuilder companyType(int choice) {
            this.companyType = CompanyType.stringToType(choice);
            return this;
        }

        /**
         * Retourne l'instance courante du Builder.
         *
         * @return L'instance courante de IntervenantBuilder.
         */
        @Override
        protected IntervenantBuilder self() {
            return this;
        }

        /**
         * Construit un objet Intervenant avec les données fournies.
         *
         * @return Une instance de Intervenant.
         */
        @Override
        public Intervenant build() {
            return new Intervenant(this);
        }
    }

    /**
     * Obtient l'identifiant unique de l'intervenant.
     *
     * @return L'identifiant unique de l'intervenant.
     */
    public int getIdentifier() {
        return this.identifier;
    }

    /**
     * Obtient le type d'entreprise auquel l'intervenant est associé.
     *
     * @return Le type d'entreprise de l'intervenant.
     */
    public CompanyType getCompanyType() {
        return this.companyType;
    }

    /**
     * Affiche les informations de l'intervenant.
     * Utilise la classe MenuView pour afficher les données dans la console.
     */
    @Override
    public void print() {
        super.print();
        MenuView.printMessage("Identifiant : " + this.identifier);
        MenuView.printMessage("Type d'entreprise : " + this.companyType.getDisplayType());
    }
}