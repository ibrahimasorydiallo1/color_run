package fr.esgi.color_run.servlet;

import fr.esgi.color_run.business.User;
import fr.esgi.color_run.repository.impl.UserRepositoryImpl;
import fr.esgi.color_run.service.UserService;
import fr.esgi.color_run.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class AdminUsersServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl(new UserRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            List<User> users = userService.lister();
            req.setAttribute("users", users);
        } catch (SQLException e) {
            throw new ServletException("Erreur BDD utilisateurs", e);
        }

        /* --- rendu Thymeleaf --- */
        TemplateEngine engine = (TemplateEngine) getServletContext()
                .getAttribute("templateEngine");

        JakartaServletWebApplication app =
                (JakartaServletWebApplication) getServletContext()
                        .getAttribute(JakartaServletWebApplication.class.getName());

        if (app == null) {                    // au cas où il n'aurait pas été stocké
            app = JakartaServletWebApplication.buildApplication(getServletContext());
        }

        WebContext ctx = new WebContext(app.buildExchange(req, res));

        ctx.setVariable("users", req.getAttribute("users"));

        res.setContentType("text/html;charset=UTF-8");
        engine.process("public/admin/adminUsers", ctx, res.getWriter());     // rendu Thymeleaf
    }
}
