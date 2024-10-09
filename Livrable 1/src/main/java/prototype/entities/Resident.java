package main.java.prototype.entities;

public class Resident implements Utilisateur {
    protected final String nom;
    protected final String dateNaissance;
    protected final String email;
    protected final String mdp;
    protected final String telephone;
    protected final String adresse;
    protected final String type = "Resident"; // Set type explicitly

    /**
     * Construit une instance {@link Resident} avec un {@link Builder}.
     *
     * @param builder le builder contenant les valeurs pour l'initilisation
     */
    public Resident(Builder builder) {
        this.nom = builder.nom;
        this.dateNaissance = builder.dateNaissance;
        this.email = builder.email;
        this.mdp = builder.mdp;
        this.telephone = builder.telephone;
        this.adresse = builder.adresse;
    }

    public static class Builder {
        private String nom;
        private String dateNaissance;
        private String email;
        private String mdp;
        private String telephone;
        private String adresse;

        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public Builder dateNaissance(String naissance) {
            this.dateNaissance = naissance;
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

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        /**
         * Construit un objet de type {@link Resident} avec les paramètres reçus
         * 
         * @return l'instance d'un objet {@link Resident}
         * @throws Exception si les champs nécessaires sont vides
         */
        public Resident build() throws Exception {
            // Lancement d'erreurs
            return new Resident(this); // Retourne l'instanciation du résident
        }
    }

    // #####################
    // -----> Getters <-----
    // #####################
    @Override
    public String getNom() { return this.nom; }
    public String getDateNaissance() { return this.dateNaissance; }
    @Override
    public String getEmail() { return this.email; }
    @Override
    public String getMdp() { return this.mdp; }
    public String getTelephone() { return this.telephone; }
    public String getAdresse() { return this.adresse; }
    @Override
    public String getType() { return this.type; }
}
