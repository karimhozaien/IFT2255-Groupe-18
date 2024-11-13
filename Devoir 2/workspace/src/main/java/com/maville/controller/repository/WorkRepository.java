package com.maville.controller.repository;

import com.maville.model.Project.TypeOfWork;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkRepository {
    public List<Result.Record> getRecords(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<ApiResponse> jsonAdapter = moshi.adapter(ApiResponse.class);

        ApiResponse apiResponse = jsonAdapter.fromJson(json);

        if (apiResponse == null || apiResponse.result == null || apiResponse.result.records == null) {
            throw new IOException("Invalid JSON: Missing or incorrect 'result' or 'records' field");
        }

        return apiResponse.result.records;
    }

    // Classe ApiResponse pour le Json au complet
    public static class ApiResponse {
        public Result result;
    }

    // Classe Result pour l'object "result"
    public static class Result {
        @Json(name = "records")
        public List<Record> records;

        // Classe Record pour l'object "records" qui contient tous les projets en cours
        public static class Record {
            @Json(name = "id")
            private String id;
            @Json(name = "title")
            private String title;
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

            public String getId() { return id; }
            public String getTitle() { return title; }
            public String getAffectedNeighbourhood() { return affectedNeighbourhood; }
            public String getAffectedStreets() { return affectedStreets; }
            public String getStartDate() { return startDate; }
            public String getEndDate() { return endDate; }
        }
    }
}
