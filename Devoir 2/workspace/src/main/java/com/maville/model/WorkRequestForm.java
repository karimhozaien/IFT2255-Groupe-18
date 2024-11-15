package com.maville.model;

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
        return null;
    }
}
