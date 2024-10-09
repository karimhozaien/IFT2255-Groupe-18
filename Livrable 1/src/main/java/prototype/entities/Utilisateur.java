package main.java.prototype.entities;

public interface Utilisateur {
    public String getNom();
    public String getMdp();
    public String getEmail();
    public String getType();

    public static class Builder {
        protected String nom;
        protected String email;
        protected String mdp;

    };
}
