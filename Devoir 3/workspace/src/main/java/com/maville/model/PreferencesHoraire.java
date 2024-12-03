package com.maville.model;

public class PreferencesHoraire {
    private String street;
    private String neighbourhood;
    private String weekHours;
    private String weekendHours;

    public PreferencesHoraire(String street, String neighbourhood, String weekHours, String weekendHours) {
        this.street = street;
        this.neighbourhood = neighbourhood;
        this.weekHours = weekHours;
        this.weekendHours = weekendHours;
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

    public String getWeekendHours() {
        return weekendHours;
    }

}
