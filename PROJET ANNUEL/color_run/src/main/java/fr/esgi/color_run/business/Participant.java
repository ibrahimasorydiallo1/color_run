package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Participant ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    private Long   id;
    private String nom;
    private String prenom;
    private String email;
    private String statutPaiement; // ex. "payé", "en_attente", "annulé"
}
