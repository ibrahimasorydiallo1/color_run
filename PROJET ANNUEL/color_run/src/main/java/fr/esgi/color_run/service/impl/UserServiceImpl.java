package fr.esgi.color_run.service.impl;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.exception.UserDejaPresentException;
import fr.esgi.color_run.repository.UserRepository;
import fr.esgi.color_run.service.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Implémentation “métier” : toutes les règles et validations
 * se trouvent ici, la Servlet n’a plus qu’à appeler ces méthodes.
 */
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    /* -------- inscription -------- */
    @Override
    public User inscrire(String prenom, String nom, String email,
                         String plainPwd, String role)
            throws UserDejaPresentException, SQLException {

        if (repo.findByEmail(email) != null) {
            throw new UserDejaPresentException("Email déjà utilisé");
        }

        User nouvel = new User(null, prenom, nom, email, plainPwd, role);
        return repo.save(nouvel);
    }


    @Override
    public User inscrireAdmin(User u)
            throws UserDejaPresentException, SQLException {

        if (repo.findByEmail(u.getEmail()) != null) {
            throw new UserDejaPresentException("Email déjà utilisé");
        }
        // TODO: hash du mot de passe plus tard
        return repo.save(u);
    }


    /* -------- login -------- */
    @Override
    public User authentifier(String email, String plainPwd) throws SQLException {

        if (email == null || plainPwd == null) return null;

        email = email.trim().toLowerCase();

        User u = repo.findByEmail(email);

        // plein texte pour l’instant (hash + BCrypt plus tard)
        return (u != null && plainPwd.equals(u.getPassword())) ? u : null;
    }



    /* -------- liste complète -------- */
    @Override
    public List<User> lister() throws SQLException {
        return repo.findAll();
    }

    /* -------- recherche par id -------- */
    @Override
    public User trouverParId(Long id) throws SQLException {
        return repo.findById(id);
    }
}
