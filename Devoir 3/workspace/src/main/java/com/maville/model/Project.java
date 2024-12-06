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
    private String workSchedule;
    private WorkStatus workStatus;

    String[] postalCodes = {
            "H1A", "H2A", "H3A", "H4A", "H5A",
            "H2B", "H3B", "H4B", "H5B", "H1C",
            "H2C", "H3C", "H4C", "H9C", "H1E",
            "H2E", "H3E", "H4E", "H9E", "H1G",
            "H2G", "H3G", "H4G", "H1H", "H2H",
            "H3H", "H4H", "H9H", "H1J", "H2J",
            "H3J", "H4J", "H1K", "H2K", "H3K",
            "H4K", "H9K", "H1L", "H2L", "H3L",
            "H4L", "H1M", "H2M", "H3M", "H4M",
            "H1N", "H2N", "H3N", "H4N", "H8N",
            "H1P", "H2P", "H8P", "H1R", "H2R",
            "H4R", "H8R", "H1S", "H2S", "H3S",
            "H4S", "H8S", "H1T", "H2T", "H3T",
            "H4T", "H8T", "H1V", "H2V", "H3V",
            "H1W", "H2W", "H3W", "H1X", "H2X",
            "H1Y", "H2Y", "H8Y", "H1Z", "H2Z",
            "H4Z", "H8Z"
    };

    // Full constructor
    public Project(String id, String title, TypeOfWork typeOfWork, String affectedNeighbourhood, String affectedStreets,
                   String startDate, String endDate, String workSchedule, WorkStatus workStatus) {
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
    public Project(String id, String title, String typeOfWork, String affectedNeighbourhood, String affectedStreets,
                   String startDate, String endDate, String workSchedule) {
        this(id, title, TypeOfWork.valueOf(typeOfWork), affectedNeighbourhood, affectedStreets, startDate, endDate, workSchedule, WorkStatus.PLANNED);
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
    public String getWorkSchedule() { return workSchedule; }
    public WorkStatus getWorkStatus() { return workStatus; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setTypeOfWork(TypeOfWork typeOfWork) { this.typeOfWork = typeOfWork; }
    public void setAffectedNeighbourhood(String affectedNeighbourhood) { this.affectedNeighbourhood = affectedNeighbourhood; }
    public void setAffectedStreets(String affectedStreets) { this.affectedStreets = affectedStreets; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setWorkSchedule(String workSchedule) { this.workSchedule = workSchedule; }
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
            System.out.println(typeOfWork); // helper
            if ("souterraine".contains(typeOfWork)) {
                return TypeOfWork.UNDERGROUND;
            } else if ("routier".contains(typeOfWork)) {
                return TypeOfWork.ROAD;
            } else if ("gaz".contains(typeOfWork) || "électricité".contains(typeOfWork)) {
                return TypeOfWork.GAS_ELECTRICITY;
            } else if ("construction".contains(typeOfWork) || "rénovation".contains(typeOfWork)) {
                return TypeOfWork.CONSTRUCTION_RENOVATION;
            } else if ("paysagement".contains(typeOfWork)) {
                return TypeOfWork.LANDSCAPE;
            } else if ("transports publics".contains(typeOfWork)) {
                return TypeOfWork.PUBLIC_TRANSPORT;
            } else if ("signalisation".contains(typeOfWork) || "éclairage".contains(typeOfWork)) {
                return TypeOfWork.SIGNAGE_LIGHTING;
            } else if ("résidentiels".contains(typeOfWork)) {
                return TypeOfWork.RESIDENTIAL;
            } else if ("entretien".contains(typeOfWork)) {
                return TypeOfWork.URBAN_MAINTENANCE;
            } else if ("télécommunication".contains(typeOfWork)) {
                return TypeOfWork.TELECOMMUNICATION_NETWORKS;
            }
        }

        return TypeOfWork.OTHER;
    }
}