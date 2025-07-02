package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private static int compteur = 0; // Sert à générer des IDs uniques

    private Long id;
    private String nom;
    private String description;
    private LocalDateTime date;
    private String lieu;
    private int nombreMaxParticipants;
    private float prix;
}