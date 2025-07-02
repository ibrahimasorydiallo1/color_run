package fr.esgi.color_run.servlet;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.business.User;
import fr.esgi.color_run.exception.CourseDejaPresentException;
import fr.esgi.color_run.repository.impl.CourseRepositoryImpl;
import fr.esgi.color_run.service.CourseService;
import fr.esgi.color_run.service.impl.CourseServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

/**
 * Cette classe aura va pouvoir traiter des requêtes HTTP
 */
@WebServlet(name = "coursesServlet", value = "/courses")
public class CourseServlet extends HttpServlet {
    private CourseService courseService;   // <- champ d’instance

    @Override
    public void init() {
        courseService = new CourseServiceImpl(new CourseRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        /* 1) filtre éventuel ?nom=... */
        String filtreNom = req.getParameter("nom");

        List<Course> courses;
        try {
            courses = (filtreNom == null || filtreNom.isBlank())
                    ? courseService.recupererCourses()
                    : courseService.recupererCourses(filtreNom);
        } catch (SQLException e) {
            throw new ServletException("Erreur BDD courses", e);
        }

        /* 2) rendu Thymeleaf */
        TemplateEngine engine = (TemplateEngine) getServletContext()
                .getAttribute("templateEngine");

        JakartaServletWebApplication app =
                (JakartaServletWebApplication) getServletContext()
                        .getAttribute(JakartaServletWebApplication.class.getName());

        if (app == null) {                       // sécurité
            app = JakartaServletWebApplication.buildApplication(getServletContext());
        }

        WebContext ctx = new WebContext(app.buildExchange(req, res));
        ctx.setVariable("courses", courses);     // ← une seule fois

        res.setContentType("text/html;charset=UTF-8");
        engine.process("/courses", ctx, res.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");               // accents sûrs
        String action = req.getParameter("action");

        try {
            switch (action) {

                /* ------------- AJOUT ------------- */
                case "ajouter" -> {
                    String nom = req.getParameter("nom");          // ← minuscules
                    if (nom == null || nom.isBlank()) {
                        throw new IllegalArgumentException("Nom manquant");
                    }
                    Course nouvelle = new Course(null, nom, null, null, null, 0, 0f);
                    courseService.ajouterCourse(nouvelle);
                }

                /* ------------- MODIF ------------- */
                case "modifier" -> {
                    String idStr = req.getParameter("id");
                    String nom   = req.getParameter("nom");
                    if (idStr == null || nom == null || nom.isBlank()) {
                        throw new IllegalArgumentException("Paramètres id/nom manquants");
                    }
                    Long id = Long.parseLong(idStr);               // bonne conversion
                    Course modif = new Course(id, nom, null, null, null, 0, 0f);
                    courseService.modifierCourse(modif);
                }

                default -> res.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Action inconnue");
            }

            /* PRG - Post/Redirect/Get : on revient sur la liste */
            res.sendRedirect(req.getContextPath() + "/courses");

        } catch (NumberFormatException nfe) {
            res.sendError(400, "Identifiant numérique invalide");
        } catch (CourseDejaPresentException ex) {
            req.setAttribute("error", ex.getMessage());
            doGet(req, res);                                // réaffiche la liste + message
        } catch (Exception ex) {
            throw new ServletException("Erreur lors du traitement de la course", ex);
        }
    }
}