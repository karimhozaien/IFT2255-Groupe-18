package main.java.prototype.utils;

public class GenerateurCodeDeHachage {
    final static int MULTIPLICATEUR_HASH = 31;

    public static int generationCodeDeHachage(String nom, String mdp) {
        int hash = 7;
        for (int i = 0; i < nom.length(); i++) {
            hash = hash * MULTIPLICATEUR_HASH + nom.charAt(i);
        }

        for (int i = (mdp.length() - 1); i >= 0; i--) { // à l'envers
            hash = hash * MULTIPLICATEUR_HASH + mdp.charAt(i);
        }

        return hash;
    }
}
