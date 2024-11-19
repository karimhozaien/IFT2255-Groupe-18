package com.maville.controller.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTest {
    @Test
    // Dans ce test, on regarde si le ID de l'utilisateur stocké dans la base des données correspond à ce qu'on fetch.
    public void fetchUserTest() {
        List<String> userInfo = new ArrayList<>();
        userInfo.add("john.doe@gmail.com"); // Adresse courriel
        userInfo.add("johndoe123"); // Mot de passe

        UserRepository userRepo = UserRepository.getInstance();
        String userIdFetched = userRepo.fetchUser(userInfo);

        assertEquals("19d4e666-ebed-4a53-911a-a5a7efe69496", userIdFetched);
    }
}
