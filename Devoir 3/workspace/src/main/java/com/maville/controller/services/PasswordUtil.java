package com.maville.controller.services;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Classe utilitaire pour la gestion des mots de passe.
 * Fournit des méthodes pour hacher les mots de passe et vérifier leur validité.
 */
public class PasswordUtil {

    /**
     * Hache un mot de passe en utilisant l'algorithme BCrypt.
     *
     * @param password Le mot de passe à hacher.
     * @return Une chaîne contenant le mot de passe haché.
     */
    public static String hashPassword(String password) {
        String strongSalt = BCrypt.gensalt(10); // Génère un sel avec une force de 10
        return BCrypt.hashpw(password, strongSalt);
    }

    /**
     * Vérifie si un mot de passe correspond à son hachage.
     *
     * @param password       Le mot de passe en clair.
     * @param hashedPassword Le mot de passe haché à vérifier.
     * @return {@code true} si le mot de passe correspond au hachage, {@code false} sinon.
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}