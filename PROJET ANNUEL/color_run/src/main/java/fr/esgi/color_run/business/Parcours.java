package fr.esgi.color_run.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Classe Parcours
public class Parcours {
    private Long id;
    private String description;
    private double distanceKm;
    private int nombreZonesCouleur;

    // Constructeur
    public Parcours(Long id, String description, double distanceKm, int nombreZonesCouleur) {
        this.id = id;
        this.description = description;
        this.distanceKm = distanceKm;
        this.nombreZonesCouleur = nombreZonesCouleur;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public int getNombreZonesCouleur() { return nombreZonesCouleur; }
    public void setNombreZonesCouleur(int nombreZonesCouleur) { this.nombreZonesCouleur = nombreZonesCouleur; }

}
