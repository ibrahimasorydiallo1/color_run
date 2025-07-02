package fr.esgi.color_run.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // (Optionnel) Vérifier qu’un user est en session
        if (req.getSession(false) == null || req.getSession(false).getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/templates/home.html").forward(req, res);
    }
}

