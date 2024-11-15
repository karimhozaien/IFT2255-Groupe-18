package com.maville.controller.repository;

import com.maville.controller.services.ApiClient;
import com.maville.controller.services.Parser;
import com.maville.controller.services.TextUtil;
import com.maville.model.Project;
import com.maville.model.Project.TypeOfWork;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WorkRepository {
    String worksAPI = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    String roadObstructionsAPI = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    /**
     * Parse un objet JSON et extrait une liste de {@code Result.Record} à partir du champ `records` de la réponse.
     *
     * @param jsonResponse La chaîne JSON à analyser.
     * @return Une liste de {@code Result.Record} extraite du champ `records` de la réponse JSON.
     * @throws IOException Si le JSON est invalide ou si les champs attendus sont manquants ou incorrects.
     */
    private List<Result.Record> getRecords(String jsonResponse) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<ApiResponse> jsonAdapter = moshi.adapter(ApiResponse.class);
        ApiResponse apiResponse = jsonAdapter.fromJson(jsonResponse);

        if (apiResponse == null || apiResponse.result == null || apiResponse.result.records == null) {
            throw new IOException("JSON invalide : Les variables 'result' ou 'records' sont manquantes/inexistantes.");
        }
        return apiResponse.result.records;
    }


    private <T> List<T> fetchAndParseRecords(String apiUrl, String option, Class<T> type) throws IOException {
        ApiClient apiClient = new ApiClient();
        apiClient.connect(apiUrl); // Connection à l'API

        List<Result.Record> records = getRecords(apiClient.getJsonResponse());
        Parser<T> parser = new Parser<>(records);
        return parser.initializeParsing(option, type);
    }

    public List<Project> getProjects() throws IOException {
        return fetchAndParseRecords(worksAPI, "works", Project.class);
    }

    public List<String> getRoadObstructions() throws IOException {
        return fetchAndParseRecords(roadObstructionsAPI, "road_obstructions", String.class);
    }

    public List<String> getFilteredRoadObstructions(String criteria, String criteriaField) throws IOException {
        return parseItems(criteria, criteriaField, String.class);
    }

    public List<Project> getFilteredProjects(String criteria, String criteriaField) throws IOException {
        return parseItems(criteria, criteriaField, Project.class);
    }

    // Surcharge de la méthode au-dessus
    public List<Project> getFilteredProjects(String searchTerm) throws IOException {
        List<Project> allProjects = getProjects();
        List<Project> filteredProjects = new ArrayList<>();
        searchTerm = TextUtil.removeAccents(searchTerm).toLowerCase(); // Retirer les accents

        for (Project project : allProjects) {
            if (project.getTitle().toLowerCase().contains(searchTerm) ||
                    project.getAffectedNeighbourhood().toLowerCase().contains(searchTerm) ||
                    project.getTypeOfWork().toString().toLowerCase().contains(searchTerm)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    private <T> List<T> parseItems(String criteria, String criteriaField, Class<T> type) throws IOException {
        List<T> filteredItems = new ArrayList<>();

        if (type.equals(Project.class)) {
            for (Project project : getProjects()) {
                if (criteria.equals("quartier")) {
                    if (project.getAffectedNeighbourhood().toLowerCase().contains(criteriaField.toLowerCase())) {
                        filteredItems.add(type.cast(project));
                    }
                } else if (criteria.equals("travail")) {
                    if (project.getTitle().toLowerCase().contains(criteriaField.toLowerCase())) {
                        filteredItems.add(type.cast(project));
                    }
                }
            }
        } else if (type.equals(String.class)) {
            for (String roadObsutruction : getRoadObstructions()) {
                if (criteria.equals("rue")) {
                    if (roadObsutruction.toLowerCase().contains(criteriaField.toLowerCase())) {
                        filteredItems.add(type.cast(roadObsutruction));
                    }
                } else if (criteria.equals("travail")) {
                    // TODO : avec Postman et le id_request
                }
            }
        }

        return filteredItems;
    }


    // ######################## Classes utilisés pour le casting avec Mochi's Adapters ########################
    // Classe ApiResponse pour le Json au complet
    public static class ApiResponse {
        public Result result;
    }

    // Classe Result pour l'objet "result"
    public static class Result {
        @Json(name = "records")
        public List<Record> records;

        // Classe Record pour l'objet "records" qui contient tous les projets en cours
        public static class Record {
            // Travaux
            @Json(name = "_id")
            private String id;
            @Json(name = "reason_category")
            private String typeOfWork;
            @Json(name = "boroughid")
            private String affectedNeighbourhood;
            @Json(name = "occupancy_name")
            private String affectedStreets;
            @Json(name = "duration_start_date")
            private String startDate;
            @Json(name = "duration_end_date")
            private String endDate;
            @Json(name = "duration_days_mon_start_time")
            private String mondayStartTime;
            @Json(name = "duration_days_mon_end_time")
            private String mondayEndTime;
            @Json(name = "duration_days_tue_start_time")
            private String tuesdayStartTime;
            @Json(name = "duration_days_tue_end_time")
            private String tuesdayEndTime;
            @Json(name = "duration_days_wed_start_time")
            private String wednesdayStartTime;
            @Json(name = "duration_days_wed_end_time")
            private String wednesdayEndTime;
            @Json(name = "duration_days_thu_start_time")
            private String thursdayStartTime;
            @Json(name = "duration_days_thu_end_time")
            private String thursdayEndTime;
            @Json(name = "duration_days_fri_start_time")
            private String fridayStartTime;
            @Json(name = "duration_days_fri_end_time")
            private String fridayEndTime;
            @Json(name = "duration_days_sat_start_time")
            private String saturdayStartTime;
            @Json(name = "duration_days_sat_end_time")
            private String saturdayEndTime;
            @Json(name = "duration_days_sun_start_time")
            private String sundayStartTime;
            @Json(name = "duration_days_sun_end_time")
            private String sundayEndTime;

            // Entraves
            @Json(name = "id_request")
            private String idRequest;
            @Json(name = "streetid")
            private String streetId;
            @Json(name = "streetimpactwidth")
            private String streetImpactWidth;
            @Json(name = "streetimpacttype")
            private String streetImpactType;

            public TypeOfWork getTypeOfWork() {
                if (typeOfWork != null) {
                    // Matching partielle dans typeOfWork
                    if (typeOfWork.contains("souterraine")) {
                        return TypeOfWork.UNDERGROUND;
                    } else if (typeOfWork.contains("routier")) {
                        return TypeOfWork.ROAD;
                    } else if (typeOfWork.contains("gaz") || typeOfWork.contains("électricité")) {
                        return TypeOfWork.GAS_ELECTRICITY;
                    } else if (typeOfWork.contains("construction") || typeOfWork.contains("rénovation")) {
                        return TypeOfWork.CONSTRUCTION_RENOVATION;
                    } else if (typeOfWork.contains("paysagement")) {
                        return TypeOfWork.LANDSCAPE;
                    } else if (typeOfWork.contains("transports publics")) {
                        return TypeOfWork.PUBLIC_TRANSPORT;
                    } else if (typeOfWork.contains("signalisation") || typeOfWork.contains("éclairage")) {
                        return TypeOfWork.SIGNAGE_LIGHTING;
                    } else if (typeOfWork.contains("résidentiels")) {
                        return TypeOfWork.RESIDENTIAL;
                    } else if (typeOfWork.contains("Entretien")) {
                        return TypeOfWork.URBAN_MAINTENANCE;
                    } else if (typeOfWork.contains("télécommunication")) {
                        return TypeOfWork.TELECOMMUNICATION_NETWORKS;
                    }
                }

                return TypeOfWork.OTHER;
            }

            public List<String> buildScheduleList() {
                List<String> scheduleList = new ArrayList<>();
                scheduleList.add(formatTimeRange(saturdayStartTime, saturdayEndTime));  // Saturday
                scheduleList.add(formatTimeRange(sundayStartTime, sundayEndTime));      // Sunday
                scheduleList.add(formatTimeRange(mondayStartTime, mondayEndTime));      // Monday
                scheduleList.add(formatTimeRange(tuesdayStartTime, tuesdayEndTime));    // Tuesday
                scheduleList.add(formatTimeRange(wednesdayStartTime, wednesdayEndTime));// Wednesday
                scheduleList.add(formatTimeRange(thursdayStartTime, thursdayEndTime));  // Thursday
                scheduleList.add(formatTimeRange(fridayStartTime, fridayEndTime));      // Friday
                return scheduleList;
            }

            private String formatTimeRange(String startTime, String endTime) {
                String formattedStartTime = (startTime != null) ? startTime : "N/A";
                String formattedEndTime = (endTime != null) ? endTime : "N/A";
                return formattedStartTime + "-" + formattedEndTime;
            }

            // Getters pour travaux
            public String getId() { return id; }
            public String getTypeOfWorkRaw() { return this.typeOfWork; }
            public String getAffectedNeighbourhood() { return affectedNeighbourhood; }
            public String getAffectedStreets() { return affectedStreets; }
            public String getStartDate() { return startDate; }
            public String getEndDate() { return endDate; }

            // Getters pour entraves
            public String getIdRequest() { return idRequest; }
            public String getStreetId() { return streetId; }
            public String getStreetImpactWidth() { return streetImpactWidth; }
            public String getStreetImpactType() { return streetImpactType; }
        }
    }
}
