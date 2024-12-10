package com.maville.controller.repository;

import com.maville.controller.services.ApisManager;
import com.maville.controller.services.*;
import com.maville.model.Project;
import com.maville.model.Project.TypeOfWork;
import com.maville.model.WorkRequestForm;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dépôt pour gérer les données liées aux projets et aux entraves.
 * Fournit des fonctionnalités pour récupérer, filtrer et stocker des informations provenant
 * d'APIs externes ou de la base de données locale.
 */
public class WorkRepository {
    public static String worksAPI = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    public static String roadObstructionsAPI = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    /**
     * Récupère les enregistrements à partir d'une réponse JSON.
     *
     * @param jsonResponse La chaîne JSON à analyser.
     * @return Une liste de {@code Result.Record} contenant les données des enregistrements.
     * @throws IOException Si le JSON est invalide ou incomplet.
     */
    public List<Result.Record> getRecords(String jsonResponse) throws IOException {
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

    /**
     * Récupère la liste des projets en cours depuis l'API des travaux.
     *
     * @return Une liste de {@code Project} représentant les projets en cours.
     * @throws IOException Si une erreur se produit lors de la récupération ou du traitement des données.
     */
    public List<Project> getOngoingProjects() throws IOException {
        return fetchAndParseRecords(worksAPI, "works", Project.class);
    }

    /**
     * Récupère la liste des entraves routières depuis l'API des entraves.
     *
     * @return Une liste de chaînes représentant les entraves routières.
     * @throws IOException Si une erreur se produit lors de la récupération ou du traitement des données.
     */
    public List<String> getRoadObstructions() throws IOException {
        return fetchAndParseRecords(roadObstructionsAPI, "road_obstructions", String.class);
    }

    /**
     * Récupère une liste filtrée d'entraves routières selon un critère donné.
     *
     * @param criteria Le type de critère (ex. "rue").
     * @param criteriaField La valeur du critère (ex. nom de rue).
     * @return Une liste de chaînes représentant les entraves filtrées.
     * @throws IOException Si une erreur se produit lors du traitement des données.
     */
    public List<String> getFilteredRoadObstructions(String criteria, String criteriaField) throws IOException {
        return parseItems(criteria, criteriaField, String.class);
    }

    /**
     * Récupère une liste filtrée de projets selon un critère donné.
     *
     * @param criteria Le type de critère (ex. "quartier", "travail").
     * @param criteriaField La valeur du critère (ex. nom de quartier).
     * @return Une liste de {@code Project} représentant les projets filtrés.
     * @throws IOException Si une erreur se produit lors du traitement des données.
     */
    public List<Project> getFilteredProjects(String criteria, String criteriaField) throws IOException {
        return parseItems(criteria, criteriaField, Project.class);
    }

    /**
     * Récupère une liste filtrée de projets selon un terme de recherche donné.
     *
     * @param searchTerm Le terme de recherche (titre, quartier, type de travaux).
     * @return Une liste de {@code Project} correspondant au terme recherché.
     * @throws IOException Si une erreur se produit lors du traitement des données.
     */
    public List<Project> getFilteredProjects(String searchTerm) throws IOException {
        List<Project> filteredProjects = new ArrayList<>();
        searchTerm = TextUtil.removeAccents(searchTerm).toLowerCase(); // Retirer les accents

        for (Project project : getOngoingProjects()) {
            if (project.getTitle().toLowerCase().contains(searchTerm) ||
                    project.getAffectedNeighbourhood().toLowerCase().contains(searchTerm) ||
                    project.getTypeOfWork().toString().toLowerCase().contains(searchTerm)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }

    /**
     * Récupère tous les projets, y compris ceux en cours et planifiés.
     *
     * @return Une liste de {@code Project} représentant tous les projets.
     * @throws IOException Si une erreur se produit lors de la récupération ou du traitement des données.
     */
    public List<Project> getAllProjects() throws IOException {
        List<Project> allProjects = new ArrayList<>();
        allProjects.addAll(getOngoingProjects());
        allProjects.addAll(getPlannedProjects());
        return allProjects;
    }

    /**
     * Récupère les projets planifiés depuis la base de données locale.
     *
     * @return Une liste de {@code Project} représentant les projets planifiés.
     */
    public List<Project> getPlannedProjects() {
        return fetchPlannedProjects();
    }

    private <T> List<T> parseItems(String criteria, String criteriaField, Class<T> type) throws IOException {
        List<T> filteredItems = new ArrayList<>();

        if (type.equals(Project.class)) {
            for (Project project : getAllProjects()) {
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
            if (criteria.equals("rue")) {
                for (String roadObsutruction : getRoadObstructions()) {
                    if (roadObsutruction.toLowerCase().contains(criteriaField.toLowerCase())) {
                        filteredItems.add(type.cast(roadObsutruction.split("\\. ")[1]));
                    }
                }
            } else if (criteria.equals("travail")) {
                try {
                    ApisManager apisManager = new ApisManager();
                    List<List<String>> filteredRoadObstructions = apisManager.parallelComputingForRequests(Project.getTypeOfWork(criteriaField));
                    for (List<String> filteredRoadObstruction : filteredRoadObstructions) {
                        for (String s : filteredRoadObstruction) {
                            filteredItems.add(type.cast(s.split("\\. ")[1]));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return filteredItems;
    }

    /**
     * Récupère tous les projets planifiés depuis la base de données locale.
     *
     * @return Une liste de {@code Project} représentant les projets planifiés.
     *         Retourne {@code null} si aucune donnée n'est trouvée ou en cas d'erreur.
     */
    public List<Project> fetchPlannedProjects() {
        List<Project> plannedProjects = new ArrayList<>();
        String selectSQL = "SELECT * FROM Projects";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String idFromDB = rs.getString("id");
                    String titleFromDB = rs.getString("title");
                    String descriptionFromDB = rs.getString("description");
                    String typeOfWorkRawFromDB = rs.getString("type_of_work");
                    String affectedNeighbourhoodFromDB = rs.getString("affected_neighbourhood");
                    String affectedStreetsFromDB = rs.getString("affected_streets");
                    String startDateFromDB = rs.getString("start_date");
                    String endDateFromDB = rs.getString("end_date");
                    String workScheduleFromDB = rs.getString("work_schedule");
                    String workStatusFromDB = rs.getString("work_status");

                    Project project = new Project(
                            idFromDB,
                            titleFromDB,
                            descriptionFromDB,
                            Project.TypeOfWork.valueOf(typeOfWorkRawFromDB),
                            affectedNeighbourhoodFromDB,
                            affectedStreetsFromDB,
                            startDateFromDB,
                            endDateFromDB,
                            workScheduleFromDB,
                            Project.WorkStatus.valueOf(workStatusFromDB)
                    );
                    plannedProjects.add(project);
                }

                if (plannedProjects.isEmpty()) {
                    System.out.println("Erreur lors du fetching.");
                    return null;
                }

                return plannedProjects;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la DB : " + e.getMessage());
            return null;
        }
    }

    /**
     * Enregistre un projet planifié dans la base de données locale.
     *
     * @param project Le projet à enregistrer.
     */
    public void savePlannedProject(Project project) {
        String insertSQL = "INSERT INTO Projects(id, title, description, type_of_work, affected_neighbourhood, " +
                "affected_streets, start_date, end_date, work_schedule, work_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, project.getId());
            pstmt.setString(2, project.getTitle());
            pstmt.setString(3, project.getDescription());
            pstmt.setString(4, project.getTypeOfWork().toString());
            pstmt.setString(5, project.getAffectedNeighbourhood());
            pstmt.setString(6, project.getAffectedStreets());
            pstmt.setString(7, project.getStartDate());
            pstmt.setString(8, project.getEndDate());
            pstmt.setString(9, project.getWorkSchedule());
            pstmt.setString(10, project.getWorkStatus().toString());

            pstmt.executeUpdate();
            //System.out.println("Le projet a été sauvegardée."); // Message helper
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du projet : " + e.getMessage());
        }
    }

    /**
     * Met à jour un projet planifié existant dans la base de données locale.
     *
     * @param project Le projet à mettre à jour.
     */
    public void updatePlannedProject(Project project) {
        String updateSQL = "UPDATE Projects SET title = ?, description = ?, type_of_work = ?, " +
                "affected_neighbourhood = ?, affected_streets = ?, start_date = ?, end_date = ?, " +
                "work_schedule = ?, work_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, project.getTitle());
            pstmt.setString(2, project.getDescription());
            pstmt.setString(3, project.getTypeOfWork().toString());
            pstmt.setString(4, project.getAffectedNeighbourhood());
            pstmt.setString(5, project.getAffectedStreets());
            pstmt.setString(6, project.getStartDate());
            pstmt.setString(7, project.getEndDate());
            pstmt.setString(8, project.getWorkSchedule());
            pstmt.setString(9, project.getWorkStatus().toString());
            pstmt.setString(10, project.getId()); // Ensure the correct project is updated

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun projet mis à jour. ID invalide ou inexistant.");
            } else {
                System.out.println("Projet mis à jour avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du projet : " + e.getMessage());
        }
    }

    /**
     * Enregistre une requête de travaux dans la base de données locale.
     *
     * @param workRequestForm La requête à enregistrer.
     */
    public void saveWorkRequest(WorkRequestForm workRequestForm) {
        String insertSQL = "INSERT INTO WorkRequests(id, submitter_id, title, description, project_type, expected_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, workRequestForm.getId());
            pstmt.setString(2, workRequestForm.getSubmitterId());
            pstmt.setString(3, workRequestForm.getTitle());
            pstmt.setString(4, workRequestForm.getDescription());
            pstmt.setString(5, workRequestForm.getProjectType().toString());
            pstmt.setString(6, workRequestForm.getExpectedDate());

            pstmt.executeUpdate();
            //System.out.println("La requête a été sauvegardée."); // Message helper
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de la requête : " + e.getMessage());
        }
    }

    /**
     * Met à jour une requête de travaux existante dans la base de données locale.
     *
     * @param workRequestForm La requête à mettre à jour.
     */
    public void updatingCandidacySubmission(WorkRequestForm workRequestForm) {
        String updateSQL = "UPDATE WorkRequests SET submitter_id = ?, title = ?, description = ?, project_type = ?, " +
                "expected_date = ?, submissions = ?, chosen_intervenant = ?, closing_message = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, workRequestForm.getSubmitterId());
            pstmt.setString(2, workRequestForm.getTitle());
            pstmt.setString(3, workRequestForm.getDescription());
            pstmt.setString(4, workRequestForm.getProjectType().toString());
            pstmt.setString(5, workRequestForm.getExpectedDate());
            pstmt.setString(6, String.join(",", workRequestForm.getSubmissions()));
            pstmt.setString(7, workRequestForm.getChosenIntervenant());
            pstmt.setString(8, workRequestForm.getClosingMessage());
            pstmt.setString(9, workRequestForm.getId()); // Ensure the correct project is updated

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucune requête mise à jour. ID invalide ou inexistant.");
            } else {
                System.out.println("Requête mise à jour avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du projet : " + e.getMessage());
        }
    }

    /**
     * Récupère toutes les requêtes soumises par un utilisateur spécifique.
     *
     * @param userId L'identifiant de l'utilisateur.
     * @return Une liste de {@code WorkRequestForm} représentant les requêtes de l'utilisateur.
     */
    public List<WorkRequestForm> fetchWorkRequestsByUserId(String userId) {
        List<WorkRequestForm> userWorkRequests = new ArrayList<>();
        String selectSQL = "SELECT * FROM WorkRequests WHERE submitter_id = ?";

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String submitterId = rs.getString("submitter_id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String projectType = rs.getString("project_type");
                    String expectedDate = rs.getString("expected_date");
                    String submissions = rs.getString("submissions");
                    String chosenIntervenant = rs.getString("chosen_intervenant");
                    String closingMessage = rs.getString("closing_message");

                    WorkRequestForm workRequestForm = new WorkRequestForm(
                            id,
                            submitterId,
                            title,
                            description,
                            projectType,
                            expectedDate,
                            submissions != null ? Arrays.asList(submissions.split(",(?! )")) : new ArrayList<>(),
                            chosenIntervenant,
                            closingMessage
                    );
                    userWorkRequests.add(workRequestForm);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des requêtes utilisateur : " + e.getMessage());
        }
        return userWorkRequests;
    }

    /**
     * Récupère toutes les requêtes de travaux depuis la base de données locale.
     *
     * @return Une liste de {@code WorkRequestForm} représentant les requêtes de travaux.
     */
    public List<WorkRequestForm> fetchWorkRequests() {
        List<WorkRequestForm> workRequestForms = new ArrayList<>();
        String selectSQL = "SELECT * FROM WorkRequests";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String idFromDb = rs.getString("id");
                    String submitterIdFromDB = rs.getString("submitter_id");
                    String titleFromDB = rs.getString("title");
                    String descriptionFromDB = rs.getString("description");
                    String projectTypeFromDB = rs.getString("project_type");
                    String expectedDateFromDB = rs.getString("expected_date");
                    String submissionsFromDB = rs.getString("submissions");
                    String chosenIntervenantFromDB = rs.getString("chosen_intervenant");
                    String closingMessageFromDB = rs.getString("closing_message");

                    WorkRequestForm workRequestForm = new WorkRequestForm(
                            idFromDb,
                            submitterIdFromDB,
                            titleFromDB,
                            descriptionFromDB,
                            projectTypeFromDB,
                            expectedDateFromDB,
                            submissionsFromDB != null ? Arrays.asList(submissionsFromDB.split(",(?! )")) : new ArrayList<>(),
                            chosenIntervenantFromDB,
                            closingMessageFromDB

                    );
                    workRequestForms.add(workRequestForm);
                }

                if (workRequestForms.isEmpty()) {
                    System.out.println("Erreur lors du fetching.");
                    return null;
                }

                return workRequestForms;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la DB : " + e.getMessage());
            return null;
        }
    }

    /**
     * Supprime une requête de travaux de la base de données locale.
     *
     * @param workRequestId L'identifiant unique de la requête à supprimer.
     */
    public void deleteWorkRequest(String workRequestId) {
        String deleteSQL = "DELETE FROM WorkRequests WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, workRequestId); // Bind the work request ID to the query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Requête supprimée avec succès.");
            } else {
                System.out.println("Aucune requête trouvée avec cet identifiant.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la requête : " + e.getMessage());
        }
    }


    // ##################### Classes utilisées pour le casting avec Mochi's Adapters #####################
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
            @Json(name = "id")
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
