package fr.esgi.color_run.servlet;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.business.User;
import fr.esgi.color_run.repository.impl.CourseRepositoryImpl;
import fr.esgi.color_run.repository.impl.UserRepositoryImpl;
import fr.esgi.color_run.service.CourseService;
import fr.esgi.color_run.service.UserService;
import fr.esgi.color_run.service.impl.CourseServiceImpl;
import fr.esgi.color_run.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "addRaceServlet", urlPatterns = "/addRace")
public class AdminAddRaceServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() {
        courseService = new CourseServiceImpl(new CourseRepositoryImpl());
    }

    /* ------------- GET : formulaire ------------- */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/templates/public/admin/adminAddRace.html")
                .forward(req, res);
    }

    /* ---------- POST : soumission du formulaire « Ajouter un utilisateur » ---------- */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String nom      = req.getParameter("nom");
        String desc     = req.getParameter("description");
        String dateStr  = req.getParameter("date");           // format ISO 2025-07-05T10:00
        String lieu     = req.getParameter("lieu");
        int maxPart     = Integer.parseInt(req.getParameter("nombreMaxParticipants"));
        String prixStr  = req.getParameter("prix");   // "20" ou "20.00"
        float prix      = Float.parseFloat(prixStr);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date    = LocalDateTime.parse(req.getParameter("date"), fmt);


        Course c = new Course(null, nom, desc, date, lieu, maxPart, prix);
        try {
            courseService.ajouterCourse(c);

        /* 3) PRG : redirection vers la liste */
            res.sendRedirect(req.getContextPath() + "/courses");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/templates/public/admin/adminAddRace.html")
                    .forward(req, res);
        }
    }
}


