package com.maville.controller.menu;

import com.maville.view.MenuView;
import java.util.Scanner;

public abstract class Menu {
    protected static final Scanner SCANNER = new Scanner(System.in);

    public void welcome() {
        MenuView.welcomeMessage();
        MenuView.authMessage();

        int option = SCANNER.nextInt();
        selection(option, "");
    }

    public static void showUserMenu(String userType) {
        while (true) {
            switch (userType) {
                case "resident":
                    MenuView.residentMenuMessages();
                    break;
                case "intervenant":
                    MenuView.intervenantMenuMessages();
                    break;
            }

            int option = SCANNER.nextInt();
            if (DefaultMenu.handleMainMenuOption(option, userType)) {
                break;
            }
        }
    }

    protected abstract void selection(int option, String action);
}
