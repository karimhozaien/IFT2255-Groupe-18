package com.maville.controller.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Service pour récupérer les codes postaux à partir d'adresses en utilisant l'API Nominatim d'OpenStreetMap.
 * Fournit également une méthode pour valider la correspondance entre un quartier et une adresse donnée.
 */
public class PostalCodeFinder {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";

    /**
     * Récupère le code postal associé à une adresse en utilisant l'API Nominatim.
     *
     * @param address L'adresse pour laquelle récupérer le code postal.
     * @return Le code postal correspondant à l'adresse, ou {@code null} si aucun résultat n'est trouvé ou en cas d'erreur.
     */
    public String getPostalCode(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String requestUrl = NOMINATIM_API_URL + "?q=" + encodedAddress + "&format=json&addressdetails=1&limit=1";

            // Créer l'URL et ouvrir la connexion
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Ajouter un User-Agent conformément aux politiques de Nominatim
            conn.setRequestProperty("User-Agent", "MaVille/1.0 (mathias.la.rochelle@umontreal.ca)");

            // Vérifier le code de réponse
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("HTTP GET Request Failed with Error code : " + responseCode);
                return null;
            }

            // Lire la réponse
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parser la réponse JSON
            JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();
            if (jsonArray.isEmpty()) {
                return null; // Aucun résultat trouvé
            }

            JsonObject firstResult = jsonArray.get(0).getAsJsonObject();
            JsonObject addressDetails = firstResult.getAsJsonObject("address");

            if (addressDetails.has("postcode")) {
                return addressDetails.get("postcode").getAsString();
            } else {
                return null; // Code postal non trouvé dans l'adresse
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // En cas d'erreur, renvoyer null
        }
    }

    /**
     * Vérifie si un quartier correspond à une adresse en comparant leurs codes postaux.
     *
     * @param currentNeighbourhood Le quartier à vérifier (basé sur la partie principale du code postal).
     * @param address L'adresse à valider.
     * @return {@code true} si le code postal de l'adresse correspond au quartier, sinon {@code false}.
     */
    public boolean isValidCorrespondance(String currentNeighbourhood, String address) {
        try {
            String postalCode = getPostalCode(address);
            if (postalCode != null) {
                return postalCode.split(" ")[0].equals(currentNeighbourhood);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Retourner false si aucun code postal n'a été trouvé ou en cas d'erreur
    }
}