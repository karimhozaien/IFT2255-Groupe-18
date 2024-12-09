package com.maville.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant une notification dans le système Maville.
 * Une notification est associée à un ou plusieurs résidents et peut être marquée comme vue.
 */
public class Notification {
    private String id;
    private String description;
    private List<String> residentsId;
    private List<String> seenResidents;

    // Constructeur sans description (utilisé lors du fetch DB)
    public Notification() {
        this.residentsId = new ArrayList<>();
        this.seenResidents = new ArrayList<>();
    }

    /**
     * Constructeur pour créer une notification avec une description.
     * Génère automatiquement un identifiant unique.
     *
     * @param description La description de la notification.
     */
    public Notification(String description) {
        this.id = java.util.UUID.randomUUID().toString();
        this.description = description;
        this.residentsId = new ArrayList<>();
        this.seenResidents = new ArrayList<>();
    }

    // Getters et Setters
    /**
     * Retourne l'identifiant unique de la notification.
     *
     * @return L'identifiant unique de la notification.
     */
    public String getId() { return id; }

    /**
     * Définit l'identifiant unique de la notification.
     *
     * @param id L'identifiant unique à définir.
     */
    public void setId(String id) { this.id = id; }

    /**
     * Retourne la description de la notification.
     *
     * @return La description de la notification.
     */
    public String getDescription() { return description; }

    /**
     * Définit la description de la notification.
     *
     * @param description La description à définir.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Retourne la liste des identifiants des résidents concernés par la notification.
     *
     * @return La liste des identifiants des résidents.
     */
    public List<String> getResidents() { return residentsId; }

    /**
     * Définit la liste des résidents concernés par la notification.
     *
     * @param residentsId La liste des identifiants des résidents à définir.
     */
    public void setResidents(List<String> residentsId) { this.residentsId = residentsId; }

    /**
     * Retourne la liste des résidents ayant vu la notification.
     *
     * @return La liste des résidents ayant vu la notification.
     */
    public List<String> getSeenResidents() { return seenResidents; }

    /**
     * Définit la liste des résidents ayant vu la notification.
     *
     * @param seenResidents La liste des résidents à définir.
     */
    public void setSeenResidents(List<String> seenResidents) { this.seenResidents = seenResidents; }

    /**
     * Ajoute un résident à la liste des résidents concernés par la notification.
     *
     * <p>Vérifie que l'identifiant n'est pas déjà présent dans la liste.</p>
     *
     * @param residentId L'identifiant du résident à ajouter.
     */
    public void addResident(String residentId) {
        if (!this.residentsId.contains(residentId)) {
            this.residentsId.add(residentId);
        }
    }

    /**
     * Supprime un résident de la liste des résidents concernés par la notification.
     *
     * @param residentId L'identifiant du résident à supprimer.
     */
    public void removeResident(String residentId) {
        this.residentsId.remove(residentId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}