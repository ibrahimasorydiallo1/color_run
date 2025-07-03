package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Bénévole ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Benevole {
    private Long   id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String role; // ex. "ravitaillement", "sécurité"…
}