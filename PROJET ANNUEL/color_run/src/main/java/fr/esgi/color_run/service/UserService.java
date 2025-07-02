package fr.esgi.color_run.service;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.exception.UserDejaPresentException;

import java.sql.SQLException;
import java.util.List;

/**
 * Couche métier pour tout ce qui concerne les utilisateurs.
 */
public interface UserService {

    /** Inscrit un nouvel utilisateur (hash du mot de passe + validations). */
    User inscrire(String prenom, String nom, String email,
                  String plainPwd, String role)
            throws UserDejaPresentException, SQLException;


    User inscrireAdmin(User u)
            throws UserDejaPresentException, SQLException;

    /** Vérifie les identifiants et renvoie l’utilisateur si OK, sinon null. */
    User authentifier(String email, String plainPwd) throws SQLException;

    /** Liste tous les utilisateurs. */
    List<User> lister() throws SQLException;

    /** Cherche un utilisateur par son id. */
    User trouverParId(Long id) throws SQLException;
}
