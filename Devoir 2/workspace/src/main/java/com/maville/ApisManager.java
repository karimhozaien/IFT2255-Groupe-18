package com.maville;

import com.maville.controller.repository.WorkRepository;
import com.maville.controller.services.ApiClient;
import com.maville.controller.services.Parser;
import com.maville.model.Project;
import com.maville.view.MenuView;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.maville.model.Project.TypeOfWork.*;

// CETTE CLASSE EST SEULEMENT UTILISÉE POUR CRÉER DES FAUSSES DONNÉES ET VOIR COMMENT S'AFFICHE
// LES TRAVAUX A VENIR MIS PAR LES INTERVENANTS (car le CU : Soumettre un projet, n'est pas demandé dans le devoir 2)
public class ApisManager {
    /*public static void main(String[] args) {
        WorkRepository workRepository = new WorkRepository();

        // Fausse donnée
        List<String> workSchedule = new ArrayList<>();
        Collections.addAll(workSchedule, "8:00-16:00", "N/A-N/A", "N/A-N/A", "N/A-N/A", "N/A-N/A", "N/A-N/A", "N/A-N/A");
        Project project = new Project(
                "1",
                "Changement égout sur Édouard-Montpetit",
                UNDERGROUND,
                "Côtes-des-neiges",
                "Édouard-Montpetit",
                "10-11-2024",
                "07-04-2024",
                workSchedule
        );

        workRepository.savePlannedProject(project);
    }*/

    // ################# TESTING #################
    public static List<List<String>> testParallelComputing(Project.TypeOfWork criteriaField) throws Exception {
        int threadPoolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        ApiClient apiClient = new ApiClient();

        List<String> recordsList = getListOfRecordsRoadObstructions();
        CompletableFuture<?>[] futures = new CompletableFuture[recordsList.size()];
        for (int i = 0; i < recordsList.size(); i++) {
            final int index = i; // Capturer i dans une variable locale finale
            futures[i] = CompletableFuture.supplyAsync(() -> {
                String filter = "{\"id\":\"" + recordsList.get(index) + "\"}";
                String apiRequest = WorkRepository.worksAPI + "&filters=" + filter;
                try {
                    apiClient.connect(apiRequest);
                    return apiClient.getJsonResponse(); // Retourne la réponse JSON de l'API
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, executorService);
        }

        Set<String> tests = new HashSet<>();
        WorkRepository workRepository = new WorkRepository();
        for (CompletableFuture<?> future : futures) {
            try {
                Object result = future.join();
                List<WorkRepository.Result.Record> records = workRepository.getRecords((String) result);
                for (WorkRepository.Result.Record record : records) {
                    if (record.getTypeOfWork().equals(criteriaField)) {
                        tests.add(record.getId()); // ON A LES ID DES TRAVAUX RELIES AU ENTRAVES QUI SONT DU TYPE DE TRAVAIL RECHERCHÉ
                        //System.out.println("Result: " + record.getId());
                    }
                }


            } catch (CompletionException e) {
                System.err.println("Task failed: " + e.getCause());
            }
        }
        return test(tests, workRepository);
    }

    private static List<List<String>> test(Set<String> tests, WorkRepository workRepository) throws IOException {
        ApiClient apiClient = new ApiClient();

        List<List<String>> recordsParsed = new ArrayList<>();
        for (String test : tests) {
            //System.out.println(test);
            String filter = "{\"id_request\":\"" + test + "\"}";
            String apiRequest = WorkRepository.roadObstructionsAPI + "&filters=" + filter;
            apiClient.connect(apiRequest);
            String response = apiClient.getJsonResponse();
            //System.out.println(response); //
            List<WorkRepository.Result.Record> records = workRepository.getRecords(apiClient.getJsonResponse());
            Parser<String> parser = new Parser<>(records);
            recordsParsed.add(parser.initializeParsing("road_obstructions", String.class));
            //MenuView.printMessage(recordsParsed.getFirst().split("\\.")[1]);
        }
        return recordsParsed;
    }

    public static List<String> getListOfRecordsRoadObstructions() throws IOException {
        ApiClient apiClient = new ApiClient();
        apiClient.connect(WorkRepository.roadObstructionsAPI);

        WorkRepository workRepository = new WorkRepository();
        List<WorkRepository.Result.Record> roadObstructions = workRepository.getRecords(apiClient.getJsonResponse());

        List<String> ids = new ArrayList<>();
        for (WorkRepository.Result.Record record : roadObstructions) {
            ids.add(record.getIdRequest());
        }

        return ids;
    }

    public static void main(String[] args) throws Exception {
        List<List<String>> t = testParallelComputing(UNDERGROUND);
        for (List<String> s : t) {
            for (String x : s) {
                System.out.println(x.split("\\. ")[1]);
            }

        }
    }
}