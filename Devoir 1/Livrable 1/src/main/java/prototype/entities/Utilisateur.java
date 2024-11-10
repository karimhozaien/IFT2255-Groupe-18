package main.java.prototype.entities;

public class Utilisateur {
    protected String nom;
    protected String email;
    protected String mdp;
    protected String type;

    // Getter methods
    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public String getType() {
        return type;
    }

    // Builder class for building Utilisateur instances
    public static class Builder {
        protected String nom;
        protected String email;
        protected String mdp;
        protected String type;

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

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        // Build method to create a Utilisateur instance
        public Utilisateur build() {
            return new Utilisateur();
        }
    }
}