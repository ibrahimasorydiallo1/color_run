package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Sponsor ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sponsor {
    private Long   id;
    private String nom;
    private float  montantDon;
    private String typeParticipation; // ex. "matériel", "financier"
}


