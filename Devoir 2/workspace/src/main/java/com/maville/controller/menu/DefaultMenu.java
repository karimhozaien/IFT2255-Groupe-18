package com.maville.controller.menu;

public class DefaultMenu extends Menu {
    private final AuthenticationMenu authMenu = new AuthenticationMenu();

    @Override
    protected void selection(int option, String action) {
        switch (option) {
            case 1:
                authMenu.signUpManager();  // Navigue vers le sous-menu d'inscription
                break;
            case 2:
                authMenu.logInManager();   // Navigue vers le sous-menu de connexion
                break;
            default:
                System.out.println("Option invalide, veuillez réessayer.");
                break;
        }
    }
}
