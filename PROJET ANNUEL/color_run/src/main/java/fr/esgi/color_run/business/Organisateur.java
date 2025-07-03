package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* ========= Organisateur ========= */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisateur {
    private Long   id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String role; // ex. "chef_de_projet"
}
