package main.java.prototype.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import main.java.prototype.utils.GenerateurCodeDeHachage;
import main.java.prototype.utils.PolymorphicTypeAdapterFactory;
import main.java.prototype.entities.Intervenant;
import main.java.prototype.entities.Resident;
import main.java.prototype.entities.Utilisateur;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

        ArrayList<Utilisateur> residentsExistants = new ArrayList<>();
        try (FileReader lecture = new FileReader(chemin)) {
            Gson gson = creationGson();
            Type typeListe = new TypeToken<ArrayList<Resident>>(){}.getType(); // Permet de garder le type de la liste
            residentsExistants = gson.fromJson(lecture, typeListe);
        } catch (IOException e) {
            System.out.println("Impossible de lire le fichier reçu.");
            e.printStackTrace();
        }

        residentsExistants = gererExistenceObjets(utilisateur, residentsExistants);

        Gson gson = creationGson();
        try (FileWriter ecriture = new FileWriter(chemin)) {
            String json = gson.toJson(residentsExistants);
            ecriture.write(json);
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier reçu.");
            e.printStackTrace();
        }
    }

    private void lectureJSON(String chemin) {
        ArrayList<Utilisateur> residentsExistants = new ArrayList<>();
        try (FileReader lecture = new FileReader(chemin)) {
            Gson gson = creationGson();
            Type typeListe = new TypeToken<ArrayList<Resident>>(){}.getType(); // Permet de garder le type de la liste
            residentsExistants = gson.fromJson(lecture, typeListe);
        } catch (IOException e) {
            System.out.println("Impossible de lire le fichier reçu.");
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un objet {@link Utilisateur} à une liste existante d'utilisateurs. Si la liste
     * n'existe pas, elle est créée et l'objet utilisateur est ajouté à cette liste.
     *
     * @param utilisateur l'objet utilisateur à ajouter à la liste
     * @param residentsExistants la liste existante d'utilisateurs
     * @return la liste mise à jour des utilisateurs
     */
    private static ArrayList<Utilisateur> gererExistenceObjets(Utilisateur utilisateur, ArrayList<Utilisateur> residentsExistants) {
        if (residentsExistants != null && utilisateur != null) {
            residentsExistants.add(utilisateur);
        } else if (utilisateur != null) {
            residentsExistants = new ArrayList<>();
            residentsExistants.add(utilisateur);
        }

        return residentsExistants;
    }

    /**
     * Crée et configure une instance Gson avec des paramètres spécifiques pour la désérialisation polymorphique.
     * Cette méthode enregistre un TypeAdapterFactory pour gérer la sérialisation et la désérialisation
     * des différents sous-types de la classe Utilisateur, tels que Resident et Intervenant.
     *
     * @return une instance Gson configurée avec la désérialisation polymorphique pour {@link Utilisateur} et ses sous-types,
     * i.e. {@link Resident} et {@link Intervenant}
     */
    private static Gson creationGson() {
        TypeAdapterFactory runtimeTypeAdapterFactory = PolymorphicTypeAdapterFactory.of(Utilisateur.class, "type")
                .registerSubtype(Resident.class, "Resident")
                .registerSubtype(Intervenant.class, "Intervenant");

        return new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .setPrettyPrinting()
                .create();
    }

    public static String recupererTypeUtilisateur(String nom, String mdp, String chemin) {
        ArrayList<Utilisateur> residentsExistants;

        try (FileReader lecture = new FileReader(chemin)) {
            Gson gson = creationGson();
            Type typeListe = new TypeToken<ArrayList<Resident>>(){}.getType(); // Permet de garder le type de la liste
            residentsExistants = gson.fromJson(lecture, typeListe);

            for (Utilisateur utilisateur : residentsExistants) {
                if (utilisateur.getNom().equals(nom) && utilisateur.getMdp().equals(mdp)) {
                    return utilisateur.getType();
                }
            }
        } catch (Exception exception) {
            System.out.println("Impossible de lire le fichier - " + exception.getMessage());
        }

        return null;
    }

    public boolean rechercheUtilisateurDansJSON(int codeDeHachage, String chemin) {
        ArrayList<Utilisateur> residentsExistants;

        try (FileReader lecture = new FileReader(chemin)) {
            Gson gson = creationGson();
            Type typeListe = new TypeToken<ArrayList<Resident>>(){}.getType(); // Permet de garder le type de la liste
            residentsExistants = gson.fromJson(lecture, typeListe);

            for (Utilisateur utilisateur : residentsExistants) {
                int codeDeHachageUtilisateurCourant = GenerateurCodeDeHachage
                        .generationCodeDeHachage(utilisateur.getNom(), utilisateur.getMdp());

                if (codeDeHachageUtilisateurCourant == codeDeHachage) {
                    return true;
                }
            }
        } catch (Exception exception) {
            System.out.println("Impossible de vous connecter - " + exception.getMessage());
        }

        return false;
    }
}