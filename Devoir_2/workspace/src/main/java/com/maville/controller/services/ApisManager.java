package com.maville.controller.services;

import com.maville.controller.repository.WorkRepository;
import com.maville.model.Project;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApisManager {
    private final ApiClient apiClient;
    private final WorkRepository workRepository;

    public ApisManager() {
        this.apiClient = new ApiClient();
        this.workRepository = new WorkRepository();
    }

    public List<List<String>> parallelComputingForRequests(Project.TypeOfWork criteriaField) throws Exception {
        int threadPoolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        List<String> recordsList = getListOfRecordsRoadObstructions();
        CompletableFuture<?>[] futures = new CompletableFuture[recordsList.size()];

        for (int i = 0; i < recordsList.size(); i++) {
            final int index = i; // Capture index in a final variable
            futures[i] = CompletableFuture.supplyAsync(() -> {
                String filter = "{\"id\":\"" + recordsList.get(index) + "\"}";
                String apiRequest = WorkRepository.worksAPI + "&filters=" + filter;
                try {
                    apiClient.connect(apiRequest);
                    return apiClient.getJsonResponse(); // Return JSON response from API
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, executorService);
        }

        Set<String> idRequests = new HashSet<>();

        for (CompletableFuture<?> future : futures) {
            try {
                Object result = future.join();
                List<WorkRepository.Result.Record> records = workRepository.getRecords((String) result);
                for (WorkRepository.Result.Record record : records) {
                    if (record.getTypeOfWork().equals(criteriaField)) {
                        idRequests.add(record.getId()); // Add IDs of matching works
                    }
                }
            } catch (CompletionException e) {
                System.err.println("Task failed: " + e.getCause());
            }
        }

        executorService.shutdown();
        return retrieveFilteredRoadObstructions(idRequests);
    }

    private List<List<String>> retrieveFilteredRoadObstructions(Set<String> idRequests) throws IOException {
        List<List<String>> recordsParsed = new ArrayList<>();

        for (String idRequest : idRequests) {
            String filter = "{\"id_request\":\"" + idRequest + "\"}";
            String apiRequest = WorkRepository.roadObstructionsAPI + "&filters=" + filter;
            apiClient.connect(apiRequest); // Reusing the same ApiClient instance

            List<WorkRepository.Result.Record> records = workRepository.getRecords(apiClient.getJsonResponse());
            Parser<String> parser = new Parser<>(records);
            recordsParsed.add(parser.initializeParsing("road_obstructions", String.class));
        }

        return recordsParsed;
    }

    public List<String> getListOfRecordsRoadObstructions() throws IOException {
        apiClient.connect(WorkRepository.roadObstructionsAPI); // Reusing the same ApiClient instance

        List<WorkRepository.Result.Record> roadObstructions = workRepository.getRecords(apiClient.getJsonResponse());

        List<String> ids = new ArrayList<>();
        for (WorkRepository.Result.Record record : roadObstructions) {
            ids.add(record.getIdRequest());
        }

        return ids;
    }
}