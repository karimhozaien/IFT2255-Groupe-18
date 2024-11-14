package com.maville.controller.activity;

import com.maville.controller.repository.WorkRepository;
import com.maville.view.SearchResultsView;
import java.io.IOException;
import java.util.Scanner;

public class ResidentActivityController {
    final Scanner scanner = new Scanner(System.in);

    public void consultWorks() {
        // TODO
        // Afficher tous les travaux en cours et planné
        // Demander à l'utilisateur s'il veut filter par 1. Quartier, 2. Type de travaux
        try {
            WorkRepository workRepo = new WorkRepository();

            SearchResultsView.askFilter();

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    SearchResultsView.printMessage("Entrez le nom du quartier : ");
                    String name = scanner.next();
                    SearchResultsView.showResults(workRepo.getFilteredProjects("quartier", name));
                    break;
                case 2:
                    SearchResultsView.printMessage("Entrez le type du travail : ");
                    String type = scanner.next();
                    SearchResultsView.showResults(workRepo.getFilteredProjects("travail", type));
                    break;
                case 0:
                    SearchResultsView.showResults(workRepo.getProjects());
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchWorks() {
        // TODO
        // Demander à l'utilisateur s'il veut chercher par 1. Titre, 2. Quartier, 3. Type de travaux
    }

    public void participateToSchedule() {
        // TODO
    }

    public void submitWorkRequest() {
        // TODO
    }

    public void receivePersonalizedNotifications() {
        // TODO
    }
}
