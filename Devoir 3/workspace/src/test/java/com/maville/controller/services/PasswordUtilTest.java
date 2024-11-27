package com.maville.controller.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordUtilTest {
    // Dans ce test, nous vérifions que le mot de passe est bel et bien hashed
    @Test
    public void hashPasswordTest() {
        String plainPassword = "iloveu10";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        assertNotNull("Le mot de passe encrypté ne devrait pas être null", hashedPassword);
        assertNotEquals("Le mot de passe encrypté ne devrait pas être égal à sa correspondance originale", plainPassword, hashedPassword);
    }
}