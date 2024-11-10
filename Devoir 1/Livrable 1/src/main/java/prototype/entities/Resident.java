package main.java.prototype.entities;

import com.google.gson.annotations.SerializedName;

public class Resident extends Utilisateur {
    @SerializedName("dateNaissance")
    protected String dateNaissance;

    @SerializedName("telephone")
    protected String telephone;

    @SerializedName("adresse")
    protected String adresse;

    private Resident(Builder builder) {
        super.nom = builder.nom;
        super.email = builder.email;
        super.mdp = builder.mdp;
        this.dateNaissance = builder.dateNaissance;
        this.telephone = builder.telephone;
        this.adresse = builder.adresse;
        this.type = builder.type;
    }

    public static class Builder extends Utilisateur.Builder {
        private String dateNaissance;
        private String telephone;
        private String adresse;

        public Builder() {
            this.dateNaissance = build().getDateNaissance();
            this.telephone = build().getTelephone();
            this.adresse = build().getAdresse();
        }

        @Override
        public Builder nom(String nom) {
            super.nom(nom);
            return this;  // Return the current Builder instance
        }

        @Override
        public Builder email(String email) {
            super.email(email);
            return this;  // Return the current Builder instance
        }

        @Override
        public Builder mdp(String mdp) {
            super.mdp(mdp);
            return this;  // Return the current Builder instance
        }

        public Builder dateNaissance(String dateNaissance) {
            this.dateNaissance = dateNaissance;
            return this;
        } // Added missing closing bracket

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        } // Added missing closing bracket

        public Builder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        } // Added missing closing bracket

        @Override
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        @Override
        public Resident build() {
            return new Resident(this);
        }
    }

    // Getters
    public String getDateNaissance() { return this.dateNaissance; }

    public String getTelephone() { return this.telephone; }

    public String getAdresse() { return this.adresse; }
}