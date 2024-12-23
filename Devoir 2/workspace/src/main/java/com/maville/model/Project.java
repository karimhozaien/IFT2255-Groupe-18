package com.maville.model;

import java.util.List;

public class Project {
    private String id;
    private String title;
    private TypeOfWork typeOfWork;
    private String affectedNeighbourhood;
    private String affectedStreets;
    private String startDate;
    private String endDate;
    private List<String> workSchedule;
    private WorkStatus workStatus;

    // Full constructor
    public Project(String id, String title, TypeOfWork typeOfWork, String affectedNeighbourhood, String affectedStreets,
                   String startDate, String endDate, List<String> workSchedule, WorkStatus workStatus) {
        this.id = id;
        this.title = title;
        this.typeOfWork = typeOfWork;
        this.affectedNeighbourhood = affectedNeighbourhood;
        this.affectedStreets = affectedStreets;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workSchedule = workSchedule;
        this.workStatus = workStatus;
    }

    // Overloaded constructor (default WorkStatus = PLANNED)
    public Project(String id, String title, TypeOfWork typeOfWork, String affectedNeighbourhood, String affectedStreets,
                   String startDate, String endDate, List<String> workSchedule) {
        this(id, title, typeOfWork, affectedNeighbourhood, affectedStreets, startDate, endDate, workSchedule, WorkStatus.PLANNED);
    }

    // Enum for WorkStatus
    public enum WorkStatus {
        ONGOING,
        PLANNED;
    }

    // Enum for TypeOfWork
    public enum TypeOfWork {
        ROAD,
        GAS_ELECTRICITY,
        CONSTRUCTION_RENOVATION,
        LANDSCAPE,
        PUBLIC_TRANSPORT,
        SIGNAGE_LIGHTING,
        UNDERGROUND,
        RESIDENTIAL,
        URBAN_MAINTENANCE,
        TELECOMMUNICATION_NETWORKS,
        OTHER;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public TypeOfWork getTypeOfWork() { return typeOfWork; }
    public String getAffectedNeighbourhood() { return affectedNeighbourhood; }
    public String getAffectedStreets() { return affectedStreets; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public List<String> getWorkSchedule() { return workSchedule; }
    public WorkStatus getWorkStatus() { return workStatus; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setTypeOfWork(TypeOfWork typeOfWork) { this.typeOfWork = typeOfWork; }
    public void setAffectedNeighbourhood(String affectedNeighbourhood) { this.affectedNeighbourhood = affectedNeighbourhood; }
    public void setAffectedStreets(String affectedStreets) { this.affectedStreets = affectedStreets; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setWorkSchedule(List<String> workSchedule) { this.workSchedule = workSchedule; }
    public void setWorkStatus(WorkStatus workStatus) { this.workStatus = workStatus; }

    @Override
    public String toString() {
        return title + ", " +
                typeOfWork + ", " +
                affectedNeighbourhood + ", " +
                affectedStreets + ", " +
                startDate + ", " +
                endDate + ", " +
                workSchedule + ", " +
                workStatus;
    }

    public static TypeOfWork getTypeOfWork(String typeOfWork) {
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
}