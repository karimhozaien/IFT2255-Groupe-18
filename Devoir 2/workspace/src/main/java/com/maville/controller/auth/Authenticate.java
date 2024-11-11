package com.maville.controller.auth;

import com.maville.controller.menu.AuthenticationMenu;
import com.maville.model.Resident;
import com.maville.model.Intervenant;
import com.maville.model.User;

import java.util.List;

public class Authenticate {
    List<String> userInfo;
    User user;

    public Authenticate(List<String> userInfo) {
        this.userInfo = userInfo;
    }

    public void logIn(String userType) {
        // TODO
    }

    public void signUp(String userType) {
        switch (userType) {
            case "résident":
                user = signUpResident();
                break;
            case "intervenant":
                user = signUpIntervenant();
                break;
        }
    }

    private User signUpResident() {
        return new Resident.Builder()
                .name(userInfo.get(0))
                .birthday(userInfo.get(1))
                .email(userInfo.get(2))
                .password(userInfo.get(3))
                .phoneNumber(userInfo.get(4))
                .address(userInfo.get(5))
                .build();
    }

    private User signUpIntervenant() {
        return new Intervenant.Builder()
                .name(userInfo.get(0))
                .email(userInfo.get(1))
                .password(userInfo.get(2))
                .identifier(userInfo.get(3))
                .companyType(AuthenticationMenu.askForCompanyType())
                .build();
    }
}
