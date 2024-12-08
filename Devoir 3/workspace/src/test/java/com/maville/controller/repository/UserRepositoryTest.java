package com.maville.controller.repository;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTest {

    @Test
    public void testFetchUser() {
        // Prépare une liste contenant les informations utilisateur valides
        List<String> userInfo = new ArrayList<>();
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("johndoe123"); // Mot de passe correct

        // Récupère les informations utilisateur via UserRepository
        UserRepository userRepo = UserRepository.getInstance();
        String[] importantInfo = userRepo.fetchUser(userInfo);
        String userIdFetched = importantInfo[0]; // Récupère l'identifiant utilisateur

        // Vérifie que l'identifiant correspond à celui attendu
        assertEquals("755bf798-da16-4116-95e3-9fae9a220037", userIdFetched);
    }

    @Test
    public void testFetchUserInvalidPassword() {
        // Prépare une liste contenant les informations utilisateur avec un mot de passe incorrect
        List<String> userInfo = new ArrayList<>();
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("mauvaismdp"); // Mot de passe incorrect

        // Tente de récupérer les informations utilisateur via UserRepository
        UserRepository userRepo = UserRepository.getInstance();
        String[] importantInfo = userRepo.fetchUser(userInfo);

        // Vérifie que la méthode retourne null pour un mot de passe incorrect
        assertNull("Devrais être vide", importantInfo);
    }
}