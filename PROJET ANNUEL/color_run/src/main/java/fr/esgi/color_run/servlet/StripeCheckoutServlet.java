package fr.esgi.color_run.servlet;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.stripe.model.checkout.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/create-checkout-session")
public class StripeCheckoutServlet extends HttpServlet {

    @Override public void init() {
        // clé secrète -> variable d'env ou fichier de conf
        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        /* -------- récup données -------- */
        long courseId    = Long.parseLong(req.getParameter("courseId")); // ex. 5
        String courseNom = req.getParameter("courseNom");                // Color Run Paris
        long amountCents = Long.parseLong(req.getParameter("amount"));   // 2500

        /* -------- crée la session -------- */
        try {
            Map<String, Object> prodData = Map.of("name", courseNom);
            Map<String, Object> priceData = Map.of(
                    "currency", "eur",
                    "unit_amount", amountCents,
                    "product_data", prodData);

            Map<String, Object> lineItem = Map.of(
                    "price_data", priceData,
                    "quantity", 1);

            Map<String, Object> params = new HashMap<>();
            params.put("mode", "payment");
            params.put("line_items", List.of(lineItem));
            params.put("client_reference_id", String.valueOf(courseId));
            params.put("success_url", req.getScheme() + "://" +
                    req.getServerName() + ":" + req.getServerPort() +
                    req.getContextPath() + "/courses/" + courseId + "/success");
            params.put("cancel_url", req.getHeader("referer")); // page détail

            Session session = Session.create(params);

            res.setContentType("application/json");
            res.getWriter().write("{\"id\":\"" + session.getId() + "\"}");

        } catch (StripeException e) {
            throw new ServletException(e);
        }
    }
}

