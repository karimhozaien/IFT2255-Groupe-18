package com.maville.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Notification {
    private String id;
    private String description;
    private List<String> residentsId;
    private List<String> seenResidents;

    public Notification() {
        this.residentsId = new ArrayList<>();
        this.seenResidents = new ArrayList<>();
    }

    // Constructeur avec description
    public Notification(String description) {
        this.id = java.util.UUID.randomUUID().toString();
        this.description = description;
        this.residentsId = new ArrayList<>();
        this.seenResidents = new ArrayList<>();
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getResidents() { return residentsId; }
    public void setResidents(List<String> residentsId) { this.residentsId = residentsId; }
    public List<String> getSeenResidents() { return seenResidents; }
    public void setSeenResidents(List<String> seenResidents) { this.seenResidents = seenResidents; }

    /**
     * Ajoute un résident à la liste des résidents concernés par la notification.
     *
     * <p>Cette méthode vérifie si le résident n'est pas déjà présent dans la liste avant de l'ajouter.</p>
     *
     * @param residentId Le résident à ajouter.
     * @throws IllegalArgumentException Si le résident est {@code null}.
     */
    public void addResident(String residentId) {
        if (!this.residentsId.contains(residentId)) {
            this.residentsId.add(residentId);
        }
    }

    /**
     * Supprime un résident de la liste des résidents concernés par la notification.
     *
     * <p>Si le résident n'est pas présent dans la liste, la méthode ne fait rien.</p>
     *
     * @param residentId Le résident à supprimer.
     * @throws IllegalArgumentException Si le résident est {@code null}.
     */
    public void removeResident(String residentId) {
        this.residentsId.remove(residentId);
    }

    // Méthode pour afficher les informations de la notification
    public void printNotification() {
        System.out.println("ID: " + this.id);
        System.out.println("Description: " + this.description);
        System.out.println("Résidents concernés:");
        for (String residentId : residentsId) {
            System.out.println(residentId);
            System.out.println("----------------------");
        }
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