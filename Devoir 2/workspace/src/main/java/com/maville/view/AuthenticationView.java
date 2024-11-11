package com.maville.view;

public class AuthenticationView extends MenuView {
    private static final String AUTH_TYPE_MESSAGE = "En tant que résident ou intervenant ?";

    public static void authMessage() {
        printMessage(AUTH_TYPE_MESSAGE);
    }

    public static void logInMessage() {

    }

    public static void signUpMessage(String userType) {
        printMessage("Inscription en tant que " + userType);
        switch (userType) {
            case "résident":

                break;
            case "intervenant":
                break;
            default:
                break;
        }
    }

    public static void authType() {
        printMessage("[1] Résident");
        printMessage("[2] Intervenant");
    }
}
