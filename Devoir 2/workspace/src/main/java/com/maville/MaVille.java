package com.maville;

import com.maville.controller.menu.DefaultMenu;
import com.maville.model.DatabaseConnectionManager;

public class MaVille {

    public static void main(String[] args) {
        DefaultMenu menu = new DefaultMenu();
        DatabaseConnectionManager.initialize();
        menu.welcome();
    }
}
