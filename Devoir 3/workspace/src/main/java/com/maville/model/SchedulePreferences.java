package com.maville.model;

import java.util.UUID;

/**
 * Classe représentant les préférences d'horaire d'un utilisateur pour un projet ou une rue spécifique.
 */
public class SchedulePreferences {
    private String id;
    private String street;
    private String neighbourhood;
    private String weekHours;

    public SchedulePreferences(String id, String street, String neighbourhood, String weekHours) {
        this.id = id;
        this.street = street;
        this.neighbourhood = neighbourhood;
        this.weekHours = weekHours;
    }

    /**
     * Constructeur pour initialiser une instance de préférences d'horaire.
     *
     * @param street        La rue concernée par les préférences.
     * @param neighbourhood Le quartier concerné par les préférences.
     * @param weekHours     Les heures hebdomadaires sous forme de chaîne formatée.
     */
    public SchedulePreferences(String street, String neighbourhood, String weekHours) {
        this.id = UUID.randomUUID().toString();
        this.street = street;
        this.neighbourhood = neighbourhood;
        this.weekHours = weekHours;
    }

    /**
     * Retourne l'identifiant unique de la préférence.
     *
     * @return L'identifiant unique.
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne la rue associée aux préférences.
     *
     * @return La rue concernée.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Retourne le quartier associé aux préférences.
     *
     * @return Le quartier concerné.
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Retourne les heures hebdomadaires formatées.
     *
     * @return Les heures hebdomadaires.
     */
    public String getWeekHours() {
        return weekHours;
    }

    public void setWeekHours(String weekHours) {
        this.weekHours = weekHours;
    }
}
