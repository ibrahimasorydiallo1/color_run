package fr.esgi.color_run.business;

import java.time.LocalDateTime;

public class Course {
    private static int compteur = 0; // Sert à générer des IDs uniques

    private int id;
    private String nom;
    private String description;
    private LocalDateTime date;
    private String lieu;
    private int nombreMaxParticipants;
    private float prix;

    // 🔁 Constructeur complet avec ID défini manuellement
    public Course(int id, String nom, String description, LocalDateTime date, String lieu, int nombreMaxParticipants,
            float prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.prix = prix;
    }

    // ➕ Constructeur sans ID : incrémente automatiquement
    public Course(String nom, String description, LocalDateTime date, String lieu, int nombreMaxParticipants,
            float prix) {
        this.id = ++compteur;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.prix = prix;
    }

    // ➕ Constructeur minimal (nom seul) avec ID automatique
    public Course(String nom) {
        this.id = ++compteur;
        this.nom = nom;
    }

    // 🔻 Méthode pour décrémenter l’ID si suppression
    public static void decrementerCompteur() {
        if (compteur > 0) {
            compteur--;
        }
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getNombreMaxParticipants() {
        return nombreMaxParticipants;
    }

    public void setNombreMaxParticipants(int nombreMaxParticipants) {
        this.nombreMaxParticipants = nombreMaxParticipants;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

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
