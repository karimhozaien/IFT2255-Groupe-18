package com.maville.controller.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTest {

    @Test
    public void testFetchUser() {
        // Prépare une liste contenant les informations utilisateur valides
        List<String> userInfo = new ArrayList<>();
        userInfo.add("camille.dupont@gmail.com"); // Adresse courriel
        userInfo.add("Dupont@1985"); // Mot de passe correct

        // Récupère les informations utilisateur via UserRepository
        UserRepository userRepo = UserRepository.getInstance();
        String[] importantInfo = userRepo.fetchUser(userInfo);
        String userIdFetched = importantInfo[0]; // Récupère l'identifiant utilisateur

        // Vérifie que l'identifiant correspond à celui attendu
        assertEquals("46c1b676-1094-4def-8a4d-8fd73437550f", userIdFetched);
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
        assertNull(importantInfo, "Devrais être vide");
    }
}