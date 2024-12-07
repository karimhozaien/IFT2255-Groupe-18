package com.maville;

import com.maville.controller.menu.DefaultMenu;
import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.view.MenuView;

public class MaVille {

    public static void main(String[] args) {
        DatabaseConnectionManager.connect();

        DefaultMenu menu = new DefaultMenu();
        menu.welcome();

        exitApplication();
    }

    public static void exitApplication() {
        MenuView.exitMessage();
        DatabaseConnectionManager.close();
        System.exit(0);
    }
}
