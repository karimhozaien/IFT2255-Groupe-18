package com.maville.controller.services;

import com.maville.controller.menu.AuthenticationMenu;
import com.maville.controller.repository.UserRepository;
import com.maville.model.Resident;
import com.maville.model.Intervenant;
import com.maville.model.User;
import com.maville.view.MenuView;

import java.sql.SQLException;
import java.util.List;

/**
 * Service permettant de gérer l'authentification et l'inscription des utilisateurs.
 * Fournit des fonctionnalités pour connecter, enregistrer et récupérer les informations des utilisateurs.
 */
public class Authenticate {
    private List<String> userInfo;
    private static String[] fetchedUserInfo;
    private static String currentLogInUserId;
    private static String userType;

    public Authenticate(List<String> userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Tente de connecter l'utilisateur avec les informations fournies.
     * Les informations récupérées sont stockées pour un accès ultérieur.
     *
     * @return {@code true} si la connexion est réussie, sinon {@code false}.
     */
    public boolean logIn() {
        UserRepository instanceUserRepo = UserRepository.getInstance();
        String[] importantInfo = instanceUserRepo.fetchUser(userInfo); // Le UUID est suffisant

        if (importantInfo != null) {
            currentLogInUserId = importantInfo[0];
            userType = importantInfo[1];
            fetchedUserInfo = importantInfo;
        }

        return currentLogInUserId != null;
    }

    /**
     * Inscrit un nouvel utilisateur du type spécifié dans le système.
     *
     * @param userType Le type de l'utilisateur à inscrire (par exemple, "resident" ou "intervenant").
     * @return {@code true} si l'inscription est réussie, sinon {@code false}.
     */
    public boolean signUp(String userType) {
        UserRepository instanceUserRepo = UserRepository.getInstance();
        User currentSignUpUser = switch (userType) {
            case "resident" -> signUpResident();
            case "intervenant" -> signUpIntervenant();
            default -> null;
        };

        if (currentSignUpUser != null) {
            try {
                instanceUserRepo.saveUser(currentSignUpUser);
                currentLogInUserId = currentSignUpUser.getID();
            } catch (SQLException e) {
                MenuView.printMessage("Erreur : Cette adresse courriel est déjà utilisée.");
                return false;
            }
        }
        return currentLogInUserId != null;
    }

    private User signUpResident() {
        try {
            return new Resident.ResidentBuilder()
                    .name(userInfo.get(0))
                    .birthday(userInfo.get(1))
                    .email(userInfo.get(2))
                    .password(userInfo.get(3))
                    .phoneNumber(userInfo.get(4))
                    .address(userInfo.get(5))
                    .id()
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur durant l'enregistrement : " + e.getMessage());
            return null;
        }
    }

    private User signUpIntervenant() {
        try {
            return new Intervenant.IntervenantBuilder()
                    .name(userInfo.get(0))
                    .email(userInfo.get(1))
                    .password(userInfo.get(2))
                    .identifier(userInfo.get(3))
                    .companyType(AuthenticationMenu.askForCompanyType())
                    .id()
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur durant l'enregistrement : " + e.getMessage());
            return null;
        }
    }

    /**
     * Récupère l'identifiant de l'utilisateur actuellement connecté.
     *
     * @return L'identifiant unique de l'utilisateur connecté.
     */
    public static String getUserId() { return currentLogInUserId; }

    /**
     * Récupère le type de l'utilisateur actuellement connecté.
     *
     * @return Le type de l'utilisateur connecté (par exemple, "resident" ou "intervenant").
     */
    public static String getUserType() {
        return userType;
    }

    /**
     * Récupère les informations complètes de l'utilisateur connecté depuis la base de données.
     *
     * @return Un tableau contenant les informations récupérées.
     */
    public static String[] getFetchedUserInfo() { return fetchedUserInfo; }
}