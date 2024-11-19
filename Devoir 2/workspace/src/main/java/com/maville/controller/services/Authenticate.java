package com.maville.controller.services;

import com.maville.controller.menu.AuthenticationMenu;
import com.maville.controller.repository.UserRepository;
import com.maville.model.Resident;
import com.maville.model.Intervenant;
import com.maville.model.User;

import java.util.List;

public class Authenticate {
    private List<String> userInfo;
    private String currentLogInUserId;

    public Authenticate(List<String> userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Tente de connecter l'utilisateur avec les informations fournies.
     *
     * @return {@code true} si la connexion est réussie, sinon {@code false}.
     */
    public boolean logIn() {
        UserRepository instanceUserRepo = UserRepository.getInstance();
        currentLogInUserId = instanceUserRepo.fetchUser(userInfo); // Le UUID est suffisant

        return currentLogInUserId != null;
    }

    /**
     * Inscrit un nouvel utilisateur du type spécifié.
     *
     * @param userType Le type de l'utilisateur à inscrire (ex. "resident" ou "intervenant").
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
            currentSignUpUser.print();
            instanceUserRepo.saveUser(currentSignUpUser);
            currentLogInUserId = currentSignUpUser.getID();
        }
        return currentLogInUserId != null;
    }

    /**
     * Crée un nouvel utilisateur de type {@code Resident} avec les informations d'inscription fournies.
     *
     * @return Un objet {@code Resident} si l'inscription réussit, sinon {@code null}.
     */
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

    /**
     * Crée un nouvel utilisateur de type {@code Intervenant} avec les informations d'inscription fournies.
     *
     * @return Un objet {@code Intervenant} si l'inscription réussit, sinon {@code null}.
     */
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

    public void setUserInfo(List<String> userInfo) {
        this.userInfo = userInfo;
    }
}