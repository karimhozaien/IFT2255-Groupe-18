package com.maville.model;

import java.util.UUID;

/**
 * Classe représentant un projet dans le système Maville.
 * Un projet est associé à un type de travaux, un quartier affecté, des rues spécifiques,
 * une période de réalisation, et un statut de travaux.
 */
public class Project {
    private String id;
    private String title;
    private String description;
    private TypeOfWork typeOfWork;
    private String affectedNeighbourhood;
    private String affectedStreets;
    private String startDate;
    private String endDate;
    private String workSchedule;
    private WorkStatus workStatus;

    // Full constructeur (les noms sont self-explanatory)
    public Project(String id, String title, String description,
                   TypeOfWork typeOfWork, String affectedNeighbourhood, String affectedStreets,
                   String startDate, String endDate, String workSchedule, WorkStatus workStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.typeOfWork = typeOfWork;
        this.affectedNeighbourhood = affectedNeighbourhood;
        this.affectedStreets = affectedStreets;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workSchedule = workSchedule;
        this.workStatus = workStatus;
    }

    // Constructeur overloader (WorkStatus = PLANNED)
    public Project(String title, String description, String typeOfWork, String affectedNeighbourhood,
                   String affectedStreets, String startDate, String endDate, String workSchedule) {
        this(UUID.randomUUID().toString(), title, description, TypeOfWork.valueOf(typeOfWork), affectedNeighbourhood, affectedStreets, startDate,
                endDate, workSchedule, WorkStatus.PLANNED);
    }

    // Enum for WorkStatus
    public enum WorkStatus {
        ONGOING,
        PLANNED,
        SUSPENDED
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

    // Getters et setters
    /**
     * Retourne l'identifiant unique du projet.
     *
     * @return L'identifiant unique du projet.
     */
    public String getId() { return id; }

    /**
     * Retourne le titre du projet.
     *
     * @return Le titre du projet.
     */
    public String getTitle() { return title; }

    /**
     * Retourne la description du projet.
     *
     * @return La description du projet.
     */
    public String getDescription() { return description; }

    /**
     * Retourne le type de travaux du projet.
     *
     * @return Le type de travaux.
     */
    public TypeOfWork getTypeOfWork() { return typeOfWork; }

    /**
     * Retourne le quartier affecté par le projet.
     *
     * @return Le quartier affecté.
     */
    public String getAffectedNeighbourhood() { return affectedNeighbourhood; }

    /**
     * Retourne les rues affectées par le projet.
     *
     * @return Les rues affectées.
     */
    public String getAffectedStreets() { return affectedStreets; }

    /**
     * Retourne la date de début du projet.
     *
     * @return La date de début.
     */
    public String getStartDate() { return startDate; }

    /**
     * Retourne la date de fin du projet.
     *
     * @return La date de fin.
     */
    public String getEndDate() { return endDate; }

    /**
     * Retourne l'horaire des travaux du projet.
     *
     * @return L'horaire des travaux.
     */
    public String getWorkSchedule() { return workSchedule; }

    /**
     * Retourne le statut actuel du projet.
     *
     * @return Le statut du projet.
     */
    public WorkStatus getWorkStatus() { return workStatus; }

    /**
     * Définit l'identifiant unique du projet.
     *
     * @param id L'identifiant unique à définir.
     */
    public void setId(String id) { this.id = id; }

    /**
     * Définit la description du projet.
     *
     * @param description La description à définir.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Définit la date de fin du projet.
     *
     * @param endDate La date de fin à définir.
     */
    public void setEndDate(String endDate) { this.endDate = endDate; }

    /**
     * Définit le statut actuel du projet.
     *
     * @param workStatus Le statut à définir.
     */
    public void setWorkStatus(WorkStatus workStatus) { this.workStatus = workStatus; }

    /**
     * Retourne une chaîne de caractères représentant les informations principales du projet.
     *
     * @return Une représentation textuelle du projet.
     */
    @Override
    public String toString() {
        return title + ", " +
                description + ", " +
                typeOfWork + ", " +
                affectedNeighbourhood + ", " +
                affectedStreets + ", " +
                startDate + ", " +
                endDate + ", " +
                workSchedule + ", " +
                workStatus;
    }

    /**
     * Déduit le type de travaux basé sur une chaîne de caractères.
     *
     * @param typeOfWork Une chaîne décrivant le type de travaux.
     * @return Le type de travaux correspondant ou OTHER si aucun type ne correspond.
     */
    public static TypeOfWork getTypeOfWork(String typeOfWork) {
        if (typeOfWork != null) {
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