package com.maville;

import com.maville.controller.menu.DefaultMenu;
import com.maville.controller.services.DatabaseConnectionManager;
import com.maville.view.MenuView;

/**
 * Classe principale de l'application MaVille.
 * Cette classe initialise la connexion à la base de données, affiche le menu principal
 * et gère la fermeture de l'application.
 */
public class MaVille {

    /**
     * Point d'entrée de l'application. <br>
     * Elle initialise la connexion à la base de données, affiche le menu principal,
     * puis termine l'application.
     *
     * @param args Arguments de ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        // Initialisation à DB
        DatabaseConnectionManager.connect();

        // Affichage du menu principal
        DefaultMenu menu = new DefaultMenu();
        menu.welcome();

        // Fermeture de l'app
        exitApplication();
    }

    /**
     * Termine proprement l'application.
     * Cette méthode affiche un message de sortie, ferme la connexion à la base de données
     * et arrête l'exécution du programme.
     */
    public static void exitApplication() {
        MenuView.exitMessage();
        DatabaseConnectionManager.close(); // Ferme connexion a DB
        System.exit(0);
    }
}