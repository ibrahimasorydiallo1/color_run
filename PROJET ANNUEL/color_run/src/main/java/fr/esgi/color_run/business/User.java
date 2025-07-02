package fr.esgi.color_run.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor            // (Long id, String prenom, String nom,
public class User {            //  String email, String password, String role)
    private Long   id;
    private String prenom;
    private String nom;
    private String email;
    private String password;
    private String role;
}
