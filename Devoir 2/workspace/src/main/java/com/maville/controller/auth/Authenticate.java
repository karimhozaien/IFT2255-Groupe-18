package com.maville.controller.auth;

import com.maville.controller.menu.AuthenticationMenu;
import com.maville.controller.repository.UserRepository;
import com.maville.model.Resident;
import com.maville.model.Intervenant;
import com.maville.model.User;

import java.util.List;

public class Authenticate {
    List<String> userInfo;
    User currentSignUpUser;
    String currentLogInUserId;

    public Authenticate(List<String> userInfo) {
        this.userInfo = userInfo;
    }

    public void logIn() {
        UserRepository instanceUserRepo = UserRepository.getInstance();

        currentLogInUserId = instanceUserRepo.fetchUser(userInfo); // Le UUID est suffisant
    }

    public void signUp(String userType) {
        UserRepository instanceUserRepo = UserRepository.getInstance();

        switch (userType) {
            case "résident":
                currentSignUpUser = signUpResident();
                break;
            case "intervenant":
                currentSignUpUser = signUpIntervenant();
                break;
        }
        if (currentSignUpUser != null) {
            currentSignUpUser.print();
            instanceUserRepo.saveUser(currentSignUpUser);
        }
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
}