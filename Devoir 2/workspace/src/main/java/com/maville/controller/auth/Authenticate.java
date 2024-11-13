package com.maville.controller.auth;

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

    public boolean logIn() {
        UserRepository instanceUserRepo = UserRepository.getInstance();
        currentLogInUserId = instanceUserRepo.fetchUser(userInfo); // Le UUID est suffisant

        return currentLogInUserId != null;
    }

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
            currentLogInUserId = currentSignUpUser.getId();
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
}