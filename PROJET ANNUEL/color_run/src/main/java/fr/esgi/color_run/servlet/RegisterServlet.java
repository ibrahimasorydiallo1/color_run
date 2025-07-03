package fr.esgi.color_run.servlet;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.exception.UserDejaPresentException;
import fr.esgi.color_run.repository.impl.UserRepositoryImpl;
import fr.esgi.color_run.service.UserService;
import fr.esgi.color_run.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registerServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl(new UserRepositoryImpl());
    }

    /* ------------- GET : formulaire ------------- */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/public/auth/register.html")
                .forward(req, res);
    }

    /* ------------- POST : soumission ------------- */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String prenom       = req.getParameter("prenom");
        String nom        = req.getParameter("nom");
        String email           = req.getParameter("email");
        String password        = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Pré-remplissage si erreur
        req.setAttribute("prenom", prenom);
        req.setAttribute("nom",  nom);
        req.setAttribute("email", email);

        /* --- validations côté serveur --- */
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Les mots de passe ne correspondent pas");
            doGet(req, res);
            return;
        }
        if (password.length() < 3) {
            req.setAttribute("error", "Mot de passe trop court (8 caractères min.)");
            doGet(req, res);
            return;
        }

        try {
            // plainPwd enregistré tel quel pour l’instant
            User u = userService.inscrire(prenom, nom, email, password, "coureur");
            // Connexion automatique après inscription
            req.getSession(true).setAttribute("user", u);
            res.sendRedirect(req.getContextPath() + "/");
        } catch (UserDejaPresentException ex) {
            req.setAttribute("error", ex.getMessage());
            doGet(req, res);
        } catch (SQLException e) {
            throw new ServletException("Erreur BDD lors de l'inscription", e);
        }
    }
}
