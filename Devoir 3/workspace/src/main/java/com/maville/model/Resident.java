package com.maville.model;

import com.maville.view.MenuView;

/**
 * Classe représentant un résident dans le système Maville.
 * Un résident est un utilisateur avec des informations supplémentaires telles que sa date de naissance,
 * son numéro de téléphone et son adresse résidentielle.
 */
public class Resident extends User {

    private String birthday;
    private String phoneNumber;
    private String address;

    /**
     * Constructeur privé utilisé par le Builder pour créer une instance de Resident.
     *
     * @param builder Le Builder contenant les données nécessaires à la création de l'objet.
     */
    private Resident(ResidentBuilder builder) {
        super(builder);
        this.birthday = builder.birthday;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    /**
     * Classe interne utilisée pour construire des objets Resident de manière fluide.
     */
    public static class ResidentBuilder extends User.Builder<ResidentBuilder> {
        private String birthday;
        private String phoneNumber;
        private String address;

        /**
         * Définit la date de naissance du résident.
         *
         * @param birthday La date de naissance du résident (format attendu : DD/MM/YYYY).
         * @return L'instance courante de ResidentBuilder.
         */
        public ResidentBuilder birthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        /**
         * Définit le numéro de téléphone du résident.
         *
         * @param phoneNumber Le numéro de téléphone du résident.
         * @return L'instance courante de ResidentBuilder.
         */
        public ResidentBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * Définit l'adresse résidentielle du résident.
         *
         * @param address L'adresse résidentielle du résident.
         * @return L'instance courante de ResidentBuilder.
         */
        public ResidentBuilder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * Retourne l'instance courante du Builder.
         *
         * @return L'instance courante de ResidentBuilder.
         */
        @Override
        protected ResidentBuilder self() {
            return this;
        }

        /**
         * Construit et retourne un objet Resident.
         *
         * @return Une nouvelle instance de Resident.
         */
        @Override
        public Resident build() {
            return new Resident(this);
        }
    }

    /**
     * Retourne la date de naissance du résident.
     *
     * @return La date de naissance du résident.
     */
    public String getBirthday() {
        return this.birthday;
    }

    /**
     * Retourne le numéro de téléphone du résident.
     *
     * @return Le numéro de téléphone du résident.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Retourne l'adresse résidentielle du résident.
     *
     * @return L'adresse résidentielle du résident.
     */
    public String getResidentialAddress() {
        return this.address;
    }

    /**
     * Affiche les informations du résident dans la console.
     * Utilise la classe MenuView pour formater et afficher les données.
     */
    @Override
    public void print() {
        super.print();
        MenuView.printMessage("Date de naissance : " + this.birthday);
        MenuView.printMessage("Numéro de téléphone : " + this.phoneNumber);
        MenuView.printMessage("Adresse : " + this.address);
    }
}