package com.maville.controller.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PasswordUtilTest {

    @Test
    public void hashPasswordTest() {
        // Mot de passe en clair à tester
        String plainPassword = "iloveu10";

        // Hachage du mot de passe
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        // Vérifie que le mot de passe haché est différent du mot de passe en clair
        assertNotEquals("Le mot de passe encrypté ne devrait pas être égal à sa correspondance originale", plainPassword, hashedPassword);
    }
}