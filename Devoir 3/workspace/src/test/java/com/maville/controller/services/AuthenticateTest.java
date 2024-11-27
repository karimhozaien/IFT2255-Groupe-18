package com.maville.controller.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class AuthenticateTest {
    List<String> userInfo;

    @Test
    // Dans ce test, on crée des fausses données. On fait un enregistrement en tant que résident en raison de la logique
    // non-pratique pour l'intervenant.
    public void signUpResidentTest() {
        userInfo = new ArrayList<>();
        userInfo.add("John Doe"); // Nom complet
        userInfo.add("29/02/2000"); // Date de naissance
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("johndoe123"); // Mot de passe
        userInfo.add("478-817-8657"); // Numéro de téléphone
        userInfo.add("575 Claude Center, Atlanta, GA, 30303"); // Adresse résidentielle
        signUpResident(userInfo);

        userInfo = new ArrayList<>();
        userInfo.add("Jane Smith"); // Nom complet
        userInfo.add("15/07/1995"); // Date de naissance
        userInfo.add("jane.smith@outlook.com"); // Adresse courriel
        userInfo.add("janesmith95"); // Mot de passe
        userInfo.add("402-555-9248"); // Numéro de téléphone
        userInfo.add("123 Maple Drive, Springfield, IL, 62701"); // Adresse résidentielle
        signUpResident(userInfo);

        userInfo = new ArrayList<>();
        userInfo.add("Alex Dupont"); // Nom complet
        userInfo.add("10/03/1988"); // Date de naissance
        userInfo.add("alex.dupont@gmail.com"); // Adresse courriel
        userInfo.add("alexdu88"); // Mot de passe
        userInfo.add("514-555-7890"); // Numéro de téléphone
        userInfo.add("450 Rue de la Montagne, Montréal, QC, H3C 2V8"); // Adresse résidentielle
        signUpResident(userInfo);
    }

    private void signUpResident(List<String> userInfo) {
        Authenticate auth = new Authenticate(userInfo);
        boolean isValid = auth.signUp("resident");
        assertTrue(isValid);
    }

    @Test
    // Dans ce test, on utilise les données créées dans le test au-dessus et on vérifie que l'utilisateur peut se
    // connecter.
    public void logInTest() {
        List<String> userInfo = new ArrayList<>();
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("johndoe123"); // Mot de passe

        Authenticate auth = new Authenticate(userInfo);
        boolean isValid = auth.logIn();

        assertTrue(isValid);
    }
}
