package com.maville.controller.menu;

import com.maville.view.MenuView;
import java.util.Scanner;

/**
 * Classe abstraite représentant le menu principal de l'application.
 * Fournit les structures de base pour afficher les menus et gérer les interactions utilisateur.
 */
public abstract class Menu {
    /**
     * Scanner partagé pour lire les entrées de l'utilisateur.
     */
    protected static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Méthode d'accueil qui affiche le menu principal et gère les options.
     *
     * <p>L'utilisateur est invité à choisir une option parmi celles affichées.
     * Une boucle permet de gérer les saisies invalides jusqu'à ce qu'une entrée correcte soit fournie.</p>
     */
    public void welcome() {
        MenuView.welcomeMessage();
        while (true) {
            MenuView.authMessage();

            if (SCANNER.hasNextInt()) { // Vérifie si l'entrée est un entier
                int option = SCANNER.nextInt();
                selection(option, ""); // Appelle la méthode abstraite pour gérer l'option
            } else {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                SCANNER.next(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
    }

    /**
     * Affiche le menu principal correspondant au type d'utilisateur connecté.
     *
     * @param userType Le type d'utilisateur, soit "resident" ou "intervenant".
     */
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
                    return; // Quitte le menu si l'utilisateur choisit de sortir
                }
            } else {
                MenuView.printMessage("Entrée invalide. Veuillez entrer un numéro valide.");
                SCANNER.next(); // Consomme l'entrée incorrecte pour éviter une boucle infinie
            }
        }
    }

    /**
     * Méthode abstraite pour gérer les options du menu.
     *
     * @param option L'option sélectionnée par l'utilisateur.
     * @param action L'action associée au menu (par exemple "login" ou "signup").
     */
    protected abstract void selection(int option, String action);
}