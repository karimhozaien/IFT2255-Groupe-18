package main.java.prototype.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.java.prototype.entities.Intervenant;
import main.java.prototype.entities.Resident;
import main.java.prototype.entities.Utilisateur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalyseFichiersJson {
    /**
     * Analyse les fichiers JSON pour trouver un type d'utilisateur pour un code de hachage donné.
     *
     * @param codeDeHachage le code de hachage à rechercher dans les fichiers JSON
     * @return le type d'utilisateur si un code de hachage correspondant est trouvé, sinon null
     */
    public static String analyserFichiersJson(int codeDeHachage) {
        File repertoire = new File("/Users/mathiaslarochelle/Documents/School/A24/IFT2255/Projets/Projet 1/Livrable 1/ressources/data/");

        File[] files = repertoire.listFiles();

        String type = null;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    type = lecture(file, codeDeHachage);

                    if (type != null) {
                        break;
                    }
                }
            }
        }

        return type;
    }

    /**
     * Lit un fichier JSON contenant des données utilisateur et trouve un type d'utilisateur basé sur le code de hachage donné.
     *
     * @param file le fichier à lire
     * @param codeDeHachage le code de hachage à comparer avec les données utilisateur
     * @return le type d'utilisateur si un code de hachage correspondant est trouvé, sinon null
     */
    private static String lecture(File file, int codeDeHachage) {
        List<? extends Utilisateur> utilisateurList = null;

        try (BufferedReader lecture = new BufferedReader(new FileReader(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (file.getName().equals("resident_data.json")) {
                utilisateurList = gson.fromJson(lecture, new TypeToken<ArrayList<Resident>>(){}.getType());
            } else if (file.getName().equals("intervenant_data.json")) {
                utilisateurList = gson.fromJson(lecture, new TypeToken<ArrayList<Intervenant>>(){}.getType());
            }


            if (utilisateurList != null) {
                for (Utilisateur utilisateur : utilisateurList) {
                    int codeDeHachageUtilisateurCourant = GenerateurCodeDeHachage
                            .generationCodeDeHachage(utilisateur.getNom(), utilisateur.getMdp());

                    if (codeDeHachageUtilisateurCourant == codeDeHachage) {
                        return utilisateur.getType();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
