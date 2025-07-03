package fr.esgi.color_run.servlet;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.repository.impl.UserRepositoryImpl;
import fr.esgi.color_run.service.UserService;
import fr.esgi.color_run.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Contrôleur HTTP “mince” : délègue la logique d’authentification
 * à UserService, afin de garder la Servlet sans règles métier.
 */
@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    /* -------- initialisation (une seule fois) -------- */
    @Override
    public void init() {
        // Ici on câble “en dur” la dépendance,
        // mais tu peux passer par un injection CDI/Spring plus tard.
        userService = new UserServiceImpl(new UserRepositoryImpl());
    }

    /* -------- GET : affiche le formulaire -------- */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/templates/public/auth/login.html")
                .forward(req, res);
    }

    /* -------- POST : soumission du formulaire -------- */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        // délégation au service
        User user;
        try {
            user = userService.authentifier(email, password);
        } catch (Exception e) {
            // log éventuellement puis renvoi vers page d’erreur
            throw new ServletException("Erreur lors de l’authentification", e);
        }

       // après repo.findByEmail
        if (user != null) {            // ---- succès ----
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            System.out.println(">>> Connexion OK pour " + user.getEmail());
            res.sendRedirect(req.getContextPath() + "/");

        } else {                       // ---- échec ----
            req.setAttribute("email", email);
            req.setAttribute("error", "Email ou mot de passe invalide");
            doGet(req, res);           // ré-affiche la page
        }
    }
}
