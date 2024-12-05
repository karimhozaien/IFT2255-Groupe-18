package com.maville.controller.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PostalCodeFinder {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";

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
            if (jsonArray.size() == 0) {
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

    public static void main(String[] args) {
        PostalCodeFinder finder = new PostalCodeFinder();
        try {
            String postalCode = finder.getPostalCode("450 Rue de la Montagne, Montréal, QC");
            if (postalCode != null) {
                System.out.println("Postal Code: " + postalCode);
            } else {
                System.out.println("No postal code found for the given address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}