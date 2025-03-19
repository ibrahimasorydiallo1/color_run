package fr.esgi.color_run.business;

import java.time.LocalDateTime;

// Classe Course
public class Course {
    private int id;
    private String nom;
    private String description;
    private LocalDateTime date;
    private String lieu;
    private int nombreMaxParticipants;
    private float prix;

    // Constructeur
    public Course(int id, String nom, String description, LocalDateTime date, String lieu, int nombreMaxParticipants, float prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.prix = prix;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public int getNombreMaxParticipants() { return nombreMaxParticipants; }
    public void setNombreMaxParticipants(int nombreMaxParticipants) { this.nombreMaxParticipants = nombreMaxParticipants; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", nombreMaxParticipants=" + nombreMaxParticipants +
                ", prix=" + prix +
                '}';
    }
}
