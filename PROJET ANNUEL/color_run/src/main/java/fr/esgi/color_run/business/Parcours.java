package fr.esgi.color_run.business;

public class Parcours {
    private Long id;
    private String nom;
    private String description;
    private double distanceKm;
    private int nombreZonesCouleur;

    public Parcours() {} // ctor no-args pour tests/mapping

    public Parcours(Long id, String nom, String description, double distanceKm, int nombreZonesCouleur) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.distanceKm = distanceKm;
        this.nombreZonesCouleur = nombreZonesCouleur;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public int getNombreZonesCouleur() { return nombreZonesCouleur; }
    public void setNombreZonesCouleur(int nombreZonesCouleur) { this.nombreZonesCouleur = nombreZonesCouleur; }
}
