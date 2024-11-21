package com.maville;

import com.maville.controller.menu.DefaultMenu;
import com.maville.controller.services.DatabaseConnectionManager;

public class MaVille {

    public static void main(String[] args) {
        DatabaseConnectionManager.connect();

        DefaultMenu menu = new DefaultMenu();
        menu.welcome();

        exitApplication();
    }

    private static void exitApplication() {
        DatabaseConnectionManager.close();
        System.exit(0);
    }
}
