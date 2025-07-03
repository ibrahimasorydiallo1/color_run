package fr.esgi.color_run.configuration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafConfiguration implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // 1. Construire l’application Thymeleaf-Jakarta
        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(sce.getServletContext());


        sce.getServletContext().setAttribute(
                JakartaServletWebApplication.class.getName(), application);

        // 2. Configurer le moteur
        TemplateEngine engine = new TemplateEngine();

        WebApplicationTemplateResolver resolver =
                new WebApplicationTemplateResolver(application);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(true);
        resolver.setCacheTTLMs(3_600_000L);   // 1 h

        engine.setTemplateResolver(resolver);

        // 3. Stocker le moteur dans le contexte
        sce.getServletContext().setAttribute("templateEngine", engine);
    }
}

