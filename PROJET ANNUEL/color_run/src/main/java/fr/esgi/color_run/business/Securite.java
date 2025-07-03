package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Sécurité ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Securite {
    private Long   id;
    private String descriptionMesures;
    private int    nombreSecouristes;
    private String pointsDeSecours; // liste ou description
}


