package com.maville.controller.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordUtilTest {
    // Dans ce test, nous vérifions que le mot de passe est bel et bien hashed
    @Test
    public void testHashPassword() {
        String plainPassword = "iloveu10";

        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        assertNotNull("The hashed password should not be null", hashedPassword);
        assertNotEquals("The hashed password should not equal the plain password", plainPassword, hashedPassword);
    }
}