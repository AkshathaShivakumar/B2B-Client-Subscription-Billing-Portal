package com.billingportal.servlet;

import com.billingportal.dao.SubscriptionDAO;
import com.billingportal.model.Subscription;
import com.billingportal.model.TierCatalog;
import com.billingportal.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/subscription")
public class ViewSubscriptionServlet extends HttpServlet {

    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int clientId = SessionUtil.getCurrentClientId(request);

        try {
            Subscription subscription = subscriptionDAO.getActiveSubscription(clientId);

            if (subscription == null) {
                request.setAttribute("errorMessage",
                        "No active subscription found for your account. Contact support.");
            } else {
                request.setAttribute("subscription", subscription);
                request.setAttribute("tierInfo", TierCatalog.get(subscription.getTier()));
            }

            request.getRequestDispatcher("/viewSubscription.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Failed to load subscription", e);
        }
    }
}
