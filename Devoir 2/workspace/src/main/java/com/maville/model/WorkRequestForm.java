package com.maville.model;

import com.maville.controller.services.TextUtil;

public class WorkRequestForm {
    private String title;
    private String description;
    private Project.TypeOfWork projectType;
    private String expectedDate;

    public WorkRequestForm(String title, String description, String projectType, String expectedDate) {
        this.title = title;
        this.description = description;
        this.projectType = parseProjectType(projectType);
        this.expectedDate = expectedDate;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Project.TypeOfWork getProjectType() { return projectType; }
    public String getExpectedDate() { return expectedDate; }

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
