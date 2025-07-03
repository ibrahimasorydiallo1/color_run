package fr.esgi.color_run.service.impl;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.exception.UserDejaPresentException;
import fr.esgi.color_run.repository.UserRepository;
import fr.esgi.color_run.service.UserService;
import fr.esgi.color_run.security.PasswordUtil;

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

    /* ---------- inscription (avec hash BCrypt) ---------- */
    @Override
    public User inscrire(String prenom, String nom, String email,
                         String plainPwd, String role)
            throws UserDejaPresentException, SQLException {

        // e-mail unique
        if (repo.findByEmail(email) != null) {
            throw new UserDejaPresentException("Email déjà utilisé");
        }

        /* Hash du mot de passe (BCrypt cost = 12) */
        String hash = PasswordUtil.hash(plainPwd);

        User nouvel = new User(null, prenom, nom, email, hash, role);
        return repo.save(nouvel);
    }

    /* ---------- authentification ---------- */
    @Override
    public User authentifier(String email, String plainPwd) throws SQLException {

        if (email == null || plainPwd == null) return null;

        User u = repo.findByEmail(email.trim().toLowerCase());

        /* Vérifie BCrypt */
        return (u != null && PasswordUtil.verify(plainPwd, u.getPassword())) ? u : null;
    }

    @Override
    public User inscrireAdmin(User u) throws SQLException {
        if (repo.findByEmail(u.getEmail()) != null) {
            throw new UserDejaPresentException("Email déjà utilisé");
        }
        // hash ici :
        u.setPassword(PasswordUtil.hash(u.getPassword()));
        return repo.save(u);
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
