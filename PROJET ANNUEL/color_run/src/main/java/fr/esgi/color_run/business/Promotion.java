package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Promotion ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    private Long   id;
    private String description;
    private String canalUtilise; // ex. "Instagram", "Affichage"
}
