package com.maville.model;

import com.maville.controller.services.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class WorkRequestForm {
    private String id;
    private String title;
    private String description;
    private Project.TypeOfWork projectType;
    private String expectedDate;
    private List<String> submissions;

    public WorkRequestForm(String title, String description, String projectType, String expectedDate) {
        this.id = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.projectType = parseProjectType(projectType);
        this.expectedDate = expectedDate;
        this.submissions = new ArrayList<>();
    }

    public WorkRequestForm(String id, String title, String description, String projectType, String expectedDate, List<String> submissions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.projectType = parseProjectType(projectType);
        this.expectedDate = expectedDate;
        this.submissions = submissions;
    }

    // Getters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Project.TypeOfWork getProjectType() { return projectType; }
    public String getExpectedDate() { return expectedDate; }
    public List<String> getSubmissions() { return submissions; }
    public void setSubmissions(List<String> submissions) { this.submissions = submissions; }

    public void addSubmission(String submission) {
        submissions.add(submission);
    }

    private Project.TypeOfWork parseProjectType(String projectType) {
        // TODO
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

    @Override
    public String toString() {
        return title + ", " + description + ", " + projectType + ", " + expectedDate;
    }
}
