package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Feedback ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private Long   id;
    private String commentaire;
    private String note; // ou int/float si tu préfères une note numérique
    private Participant auteur; // (optionnel) lien vers le participant
}
