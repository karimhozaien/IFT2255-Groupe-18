package com.maville.controller.menu;

import com.maville.view.MenuView;
import java.util.Scanner;

public abstract class Menu {
    protected static final Scanner scanner = new Scanner(System.in);

    public void welcome() {
        MenuView.welcomeMessage();
        MenuView.authMessage();

        int option = scanner.nextInt();
        selection(option, "");
    }

    // Méthode abstraite que les sous-classes implémenteront
    protected abstract void selection(int option, String action);
}
