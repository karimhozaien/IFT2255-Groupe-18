package com.maville.model;

public class SchedulePreferences {
    private String street;
    private String neighbourhood;
    private String weekHours;

    public SchedulePreferences(String street, String neighbourhood, String weekHours) {
        this.street = street;
        this.neighbourhood = neighbourhood;
        this.weekHours = weekHours;
    }

    public String getStreet() {
        return street;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getWeekHours() {
        return weekHours;
    }
}
