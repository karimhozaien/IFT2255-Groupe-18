package com.maville.model;

import com.maville.controller.services.TextUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un formulaire de requête de travaux dans le système Maville.
 */
public class WorkRequestForm {
    private String id;
    private String submitterId;
    private String title;
    private String description;
    private Project.TypeOfWork projectType;
    private String expectedDate;
    private List<String> submissions;
    private String chosenIntervenant;
    private String closingMessage;

    /**
     * Constructeur pour créer un formulaire de requête de travaux.
     *
     * @param title        Le titre de la requête.
     * @param description  La description des travaux demandés.
     * @param projectType  Le type de travaux (chaîne de caractères à analyser).
     * @param expectedDate La date de fin espérée pour les travaux.
     */
    public WorkRequestForm(String submitterId, String title, String description, String projectType, String expectedDate) {
        this.id = java.util.UUID.randomUUID().toString();
        this.submitterId = submitterId;
        this.title = title;
        this.description = description;
        this.projectType = parseProjectType(projectType);
        this.expectedDate = expectedDate;
        this.submissions = new ArrayList<>();
    }

    public WorkRequestForm(String id, String submitterId, String title, String description, String projectType,
                           String expectedDate, List<String> submissions, String chosenIntervenant, String closingMessage) {
        this.id = id;
        this.submitterId = submitterId;
        this.title = title;
        this.description = description;
        this.projectType = parseProjectType(projectType);
        this.expectedDate = expectedDate;
        this.submissions = new ArrayList<>(submissions);
        this.chosenIntervenant = chosenIntervenant;
        this.closingMessage = closingMessage;
    }

    /**
     * Ajoute une soumission à la liste des soumissions.
     *
     * @param submission La soumission à ajouter.
     */
    public void addSubmission(String submission) {
        this.submissions.add(submission.trim());
    }

    /**
     * Analyse une chaîne de caractères pour déterminer le type de travaux correspondant.
     *
     * @param projectType La chaîne représentant le type de travaux.
     * @return Le type de travaux correspondant ou null si aucun ne correspond.
     */
    private Project.TypeOfWork parseProjectType(String projectType) {
        for (Project.TypeOfWork typeOfWork : Project.TypeOfWork.values()) {
            String normalizedProjectType = TextUtil.removeAccents(projectType.toLowerCase());
            String[] words = normalizedProjectType.split("\\s+"); // Split on spaces or tabs

            for (String word : words) {
                // Check if the current word matches or is contained in the enum value
                if (typeOfWork.toString().toLowerCase().contains(word)) {
                    return typeOfWork;
                }
            }
        }
        return null;
    }

    /**
     * Retourne une chaîne représentant les informations principales du formulaire.
     *
     * @return Une représentation textuelle du formulaire.
     */
    @Override
    public String toString() {
        return title + ", " + description + ", " + projectType + ", " + expectedDate + ", " + submissions
                + ", " + closingMessage;
    }

    // Getters
    /**
     * Retourne l'identifiant unique du formulaire.
     *
     * @return L'identifiant unique.
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne l'identifiant du résident qui a soumis.
     *
     * @return L'identifiant du déposant.
     */
    public String getSubmitterId() {
        return submitterId;
    }

    /**
     * Retourne le titre de la requête.
     *
     * @return Le titre.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retourne la description de la requête.
     *
     * @return La description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retourne le type de travaux demandé.
     *
     * @return Le type de travaux.
     */
    public Project.TypeOfWork getProjectType() {
        return projectType;
    }

    /**
     * Retourne la date de fin espérée pour les travaux.
     *
     * @return La date de fin espérée.
     */
    public String getExpectedDate() {
        return expectedDate;
    }

    /**
     * Retourne la liste des soumissions associées à la requête.
     *
     * @return La liste des soumissions.
     */
    public List<String> getSubmissions() {
        return submissions;
    }

    /**
     * Définit la liste des soumissions associées à la requête.
     *
     * @param submissions La nouvelle liste de soumissions à associer au formulaire.
     */
    public void setSubmissions(List<String> submissions) {
        this.submissions = submissions;
    }

    public String getChosenIntervenant() {
        return chosenIntervenant;
    }

    public void setChosenIntervenant(String chosenIntervenant) {
        this.chosenIntervenant = chosenIntervenant;
    }

    /**
     * Retourne le message de fermeture (optionnel) écrit par le déposant.
     *
     * @return Le message de fermeture.
     */
    public String getClosingMessage() {
        return closingMessage;
    }

    /**
     * Définit le message de fermeture de la requête (qui est envoyé à l'intervenant en charge).
     *
     * @param closingMessage Le nouveau message de fermeture.
     */
    public void setClosingMessage(String closingMessage) {
        this.closingMessage = closingMessage;
    }
}
