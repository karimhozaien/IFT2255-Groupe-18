package com.maville.controller.api;

import java.io.IOException;
import java.util.List;

import com.maville.controller.repository.Parser;
import com.maville.controller.repository.WorkRepository;
import com.maville.controller.repository.WorkRepository.Result.Record;
import com.maville.model.Project;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ApiClient {
    final OkHttpClient client = new OkHttpClient();
    String myAPIUrl = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    public void connect() throws IOException {
        Request request = new Request.Builder()
                .url(myAPIUrl)
                .build();

        // Effectue la connection et si ça marche pas, on catch l'erreur
        try(Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();

                WorkRepository workRepo = new WorkRepository();
                List<Record> records = workRepo.getRecords(json);

                Parser parser = new Parser(records);
                List<Project> projects = parser.initializeParsing();

            }
        }
    }

    public static void main(String[] args) throws IOException {
        ApiClient apiClient = new ApiClient();
        apiClient.connect();
    }
}
