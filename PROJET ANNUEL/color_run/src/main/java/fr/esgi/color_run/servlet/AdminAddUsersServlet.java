package fr.esgi.color_run.servlet;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.exception.UserDejaPresentException;
import fr.esgi.color_run.repository.impl.UserRepositoryImpl;
import fr.esgi.color_run.service.UserService;
import fr.esgi.color_run.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "addUsersServlet", urlPatterns = "/addUsers")
public class AdminAddUsersServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl(new UserRepositoryImpl());
    }

    /* ------------- GET : formulaire ------------- */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/public/admin/adminAddUser.html")
                .forward(req, res);
    }

    /* ---------- POST : soumission du formulaire « Ajouter un utilisateur » ---------- */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 1) récupérer les champs du formulaire
        String prenom   = req.getParameter("prenom");
        String nom      = req.getParameter("nom");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String role     = req.getParameter("role");      //  admin / organisateur / coureur

        try {
            /* 2) créer l’objet et enregistrer */
            User nouvel = new User(null, prenom, nom, email, password, role);
            userService.inscrireAdmin(nouvel);           // méthode du service réservée à l’admin

            /* 3) PRG : redirection vers la liste */
            res.sendRedirect(req.getContextPath() + "/admin/adminUsers");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/templates/public/admin/adminAddUser.html")
                    .forward(req, res);
        }
    }
}


