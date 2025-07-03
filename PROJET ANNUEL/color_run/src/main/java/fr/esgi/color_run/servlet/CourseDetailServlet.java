package fr.esgi.color_run.servlet;

import fr.esgi.color_run.business.Course;
import fr.esgi.color_run.repository.impl.CourseRepositoryImpl;
import fr.esgi.color_run.service.CourseService;
import fr.esgi.color_run.service.impl.CourseServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/detailCourse/*")            // /courses/{id}
public class CourseDetailServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() {
        courseService = new CourseServiceImpl(new CourseRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        /* Récupère l’ID dans l’URL après /courses/ */
        String pathInfo = req.getPathInfo();   // ex. /5
        if (pathInfo == null || pathInfo.length() <= 1) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID manquant");
            return;
        }

        Long id;
        try { id = Long.parseLong(pathInfo.substring(1)); }
        catch (NumberFormatException e) {
            res.sendError(400, "ID invalide");
            return;
        }

        try {
            Course course = courseService.trouverParId(id);
            if (course == null) {
                res.sendError(404, "Course inconnue");
                return;
            }

            TemplateEngine engine = (TemplateEngine) getServletContext()
                    .getAttribute("templateEngine");
            JakartaServletWebApplication app =
                    (JakartaServletWebApplication) getServletContext()
                            .getAttribute(JakartaServletWebApplication.class.getName());

            if (app == null) {                    // au cas où il n'aurait pas été stocké
                app = JakartaServletWebApplication.buildApplication(getServletContext());
            }

            WebContext ctx = new WebContext(app.buildExchange(req, res));
            ctx.setVariable("course", course);
            // ctx.setVariable("faqBlocks", faqService.listForCourse(id));

            res.setContentType("text/html;charset=UTF-8");
            engine.process("public/races/course-details", ctx, res.getWriter());

        } catch (SQLException e) {
            throw new ServletException("Erreur BDD course detail", e);
        }
    }
}

