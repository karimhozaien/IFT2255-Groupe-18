package com.maville.controller.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticateTest {

    // Liste utilisée pour stocker les informations utilisateur pendant les tests
    List<String> userInfo;

    // Supprime un utilisateur de test de la base de données à partir de son email
    private void deleteTestUser(String email) {
        String deleteSQL = "DELETE FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, email); // Définit l'email à supprimer
            pstmt.executeUpdate(); // Exécute la requête de suppression
            System.out.println("Utilisateur supprimé : " + email); // Affiche un message de confirmation
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage()); // Gestion des erreurs SQL
        }
    }

    @Test
    public void signUpResidentTest() {
        // Prépare les informations utilisateur pour un résident
        userInfo = new ArrayList<>();
        userInfo.add("John Doe"); // Nom complet
        userInfo.add("29/02/2000"); // Date de naissance
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("johndoe123"); // Mot de passe
        userInfo.add("478-817-8657"); // Numéro de téléphone
        userInfo.add("575 Claude Center, Atlanta, GA, 30303"); // Adresse résidentielle

        // Inscrit le résident
        signUpResident(userInfo);

        // Nettoie la base de données après le test
        deleteTestUser("john.doe@gmail.com");
    }

    // Effectue l'inscription d'un résident et vérifie que l'inscription est réussie
    private void signUpResident(List<String> userInfo) {
        Authenticate auth = new Authenticate(userInfo); // Initialise le processus d'authentification
        boolean isValid = auth.signUp("resident"); // Effectue l'inscription en tant que résident
        assertTrue(isValid); // Vérifie que l'inscription a réussi
    }

    @Test
    public void logInTest() {
        // Prépare les informations utilisateur pour une connexion valide
        userInfo = new ArrayList<>();
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("johndoe123"); // Mot de passe

        // Crée une instance d'authentification et effectue la connexion
        Authenticate auth = new Authenticate(userInfo);
        boolean isValid = auth.logIn(); // Vérifie que la connexion est réussie

        assertTrue(isValid); // Valide le succès de la connexion
    }

    @Test
    public void signUpDuplicateEmailTest() {
        try {
            // Prépare les informations pour le premier utilisateur
            userInfo = new ArrayList<>();
            userInfo.add("John Doe");
            userInfo.add("29/02/2000");
            userInfo.add("john.doe@gmail.com");
            userInfo.add("johndoe123");
            userInfo.add("478-817-8657");
            userInfo.add("575 Claude Center, Atlanta, GA, 30303");

            // Inscrit le premier utilisateur
            Authenticate auth = new Authenticate(userInfo);
            auth.signUp("resident");

            // Prépare les informations pour un utilisateur avec le même email
            userInfo = new ArrayList<>();
            userInfo.add("Jane Doe");
            userInfo.add("01/01/1995");
            userInfo.add("john.doe@gmail.com"); // Même email
            userInfo.add("janedoe123");
            userInfo.add("123-456-7890");
            userInfo.add("123 Maple Drive, Springfield, IL, 62701");

            // Tente d'inscrire un utilisateur avec un email déjà utilisé
            Authenticate duplicateAuth = new Authenticate(userInfo);
            boolean isValid = duplicateAuth.signUp("resident");

            // Vérifie que l'inscription échoue à cause de l'email en double
            assertFalse(isValid, "L'inscription devrait échouer pour un courriel déjà utilisé");
        } finally {
            // Nettoie la base de données après le test
            deleteTestUser("john.doe@gmail.com");
        }
    }
}