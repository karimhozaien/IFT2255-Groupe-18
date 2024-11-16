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
            System.out.println(typeOfWork.toString());
            projectType = TextUtil.removeAccents(projectType);
            if (typeOfWork.toString().toLowerCase().contains(projectType.toLowerCase())) {
                return typeOfWork;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return title + ", " + description + ", " + projectType + ", " + expectedDate;
    }
}
