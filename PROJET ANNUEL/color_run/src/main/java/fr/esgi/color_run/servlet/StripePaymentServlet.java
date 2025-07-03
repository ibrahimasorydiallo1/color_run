package fr.esgi.color_run.servlet;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "stripePaymentServlet", value = "/create-payment-intent")
public class StripePaymentServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        Stripe.apiKey = "sk_test_51RaCSbB0671vfetGwURJcgE6c3UbM5B2zHTvJ74j1l3HFcsgqKQXvSsdkhlQroq6qE2x4cpQq1da8HpE4Y92S6HM007yyoSh5b"; // ta clé secrète de test
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupère le prix de la course
        String priceParam = request.getParameter("amount"); // en centimes
        long amount = Long.parseLong(priceParam);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount); // en centimes d'euro (25€ = 2500)
        params.put("currency", "eur");
        params.put("automatic_payment_methods", Map.of("enabled", true));

        try {
            PaymentIntent intent = PaymentIntent.create(params);

            response.setContentType("application/json");
            response.getWriter().write("{\"clientSecret\": \"" + intent.getClientSecret() + "\"}");
        } catch (StripeException e) {
            throw new ServletException(e);
        }
    }
}