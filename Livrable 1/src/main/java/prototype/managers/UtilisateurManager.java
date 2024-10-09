package main.java.prototype.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.java.prototype.entities.*;
import main.java.prototype.utils.AnalyseFichiersJson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurManager {

    /**
     * Sauvegarde les informations d'un utilisateur au format JSON dans le fichier spécifié par le chemin donné.
     *
     * @param utilisateur l'objet utilisateur à sauvegarder
     * @param chemin le chemin du fichier dans lequel les données d'utilisateur seront sauvegardées
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture ou de l'écriture du fichier
     */
    public void sauvegardeUtilisateurAJSON(Utilisateur utilisateur, String chemin) throws IOException {
        System.out.println("Enregistrement des informations de l'utilisateur...");

        if (utilisateur instanceof Resident) {
            System.out.println("Utilisateur est une instance de Resident");
            sauvegarderUtilisateur((Resident) utilisateur, chemin, new TypeToken<ArrayList<Resident>>(){}.getType());
        } else if (utilisateur instanceof Intervenant) {
            System.out.println("Utilisateur est une instance de Intervenant");
            sauvegarderUtilisateur((Intervenant) utilisateur, chemin, new TypeToken<ArrayList<Intervenant>>(){}.getType());
        }
    }

    /**
     * Méthode générique pour lire et sauvegarder un utilisateur (Resident ou Intervenant) dans un fichier JSON.
     *
     * @param utilisateur l'utilisateur à sauvegarder
     * @param chemin le chemin du fichier JSON
     * @param type le type de la liste d'utilisateurs (Resident ou Intervenant)
     * @param <T> le type d'utilisateur (Resident ou Intervenant)
     */
    private <T> void sauvegarderUtilisateur(T utilisateur, String chemin, Type type) {
        List<T> utilisateurList;

        // Lecture du JSON
        try (BufferedReader lecture = new BufferedReader(new FileReader(chemin))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            utilisateurList = gson.fromJson(lecture, type);

            if (utilisateurList == null) {
                utilisateurList = new ArrayList<>();
            }

            utilisateurList.add(utilisateur);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Écriture dans le JSON
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chemin))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(utilisateurList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String rechercheUtilisateurDansJSON(int codeDeHachage) {
        return AnalyseFichiersJson.analyserFichiersJson(codeDeHachage);
    }
}
