package com.maville.controller.menu;

import com.maville.view.MenuView;
import java.util.Scanner;

public abstract class Menu {
    protected static final Scanner SCANNER = new Scanner(System.in);

    public void welcome() {
        while (true) {
            MenuView.welcomeMessage();
            MenuView.authMessage();

            if (SCANNER.hasNextInt()) { // Vérifie si l'entrée est un entier
                int option = SCANNER.nextInt();
                selection(option, "");
            } else {
                System.out.println("Entrée invalide. Veuillez entrer un numéro valide.");
                SCANNER.next(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
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

            if (SCANNER.hasNextInt()) { // Vérifie si l'entrée est un entier
                int option = SCANNER.nextInt();
                if (DefaultMenu.handleMainMenuOption(option, userType)) {
                    return;
                }
            } else {
                System.out.println("Entrée invalide. Veuillez entrer un numéro valide.");
                SCANNER.next(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
    }

    protected abstract void selection(int option, String action);
}