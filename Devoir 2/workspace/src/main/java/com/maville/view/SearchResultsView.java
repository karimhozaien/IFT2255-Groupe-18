package com.maville.view;

import com.maville.model.Project;
import java.util.List;

public class SearchResultsView extends MenuView {
    public static void showResults(List<Project> projects) {
        for (Project project : projects) {
            printMessage(project.toString());
        }
    }

    public static void askFilter() {
        printMessage("Désirez-vous filtrer par quartier ou par type de travaux ?");
        printMessage("[1] Quartier");
        printMessage("[2] Type de travaux");
        printMessage("[0] Aucun");
    }
}
