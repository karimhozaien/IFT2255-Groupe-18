package com.maville.controller.services;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {
    final OkHttpClient client = new OkHttpClient();
    private String jsonResponse;

    /**
     * Établit une connexion à l'API des travaux de la ville et récupère la réponse JSON.
     * La réponse est stockée dans la variable {@code jsonResponse} si la requête est réussie.
     *
     * @throws IOException Si une erreur survient lors de l'exécution de la requête HTTP.
     */
    public void connect(String myAPIUrl) throws IOException {
        Request request = new Request.Builder()
                .url(myAPIUrl)
                .build();

        // Effectue la connection et si ça marche pas, on catch l'erreur
        try(Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                jsonResponse = response.body().string();
            }
        }
    }

    public String getJsonResponse() {
        return jsonResponse;
    }
}
