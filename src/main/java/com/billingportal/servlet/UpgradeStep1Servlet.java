package com.billingportal.servlet;

import com.billingportal.dao.SubscriptionDAO;
import com.billingportal.model.PendingUpgrade;
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

@WebServlet("/upgrade/step1")
public class UpgradeStep1Servlet extends HttpServlet {

    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAO();

    /** Shows the list of tiers the client can switch to. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int clientId = SessionUtil.getCurrentClientId(request);

        try {
            Subscription current = subscriptionDAO.getActiveSubscription(clientId);
            if (current == null) {
                request.setAttribute("errorMessage", "No active subscription found.");
                request.getRequestDispatcher("/upgradeStep1.jsp").forward(request, response);
                return;
            }

            request.setAttribute("currentSubscription", current);
            request.setAttribute("allTiers", TierCatalog.all());
            request.getRequestDispatcher("/upgradeStep1.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Failed to load current subscription", e);
        }
    }

    /** Handles the tier the client picked, stashes it in session, moves to step 2. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int clientId = SessionUtil.getCurrentClientId(request);
        String selectedTier = request.getParameter("selectedTier");

        if (selectedTier == null || !TierCatalog.isValidTier(selectedTier)) {
            request.setAttribute("errorMessage", "Please choose a valid plan.");
            doGet(request, response);
            return;
        }

        try {
            Subscription current = subscriptionDAO.getActiveSubscription(clientId);
            if (current == null) {
                request.setAttribute("errorMessage", "No active subscription found.");
                doGet(request, response);
                return;
            }

            if (selectedTier.equals(current.getTier())) {
                request.setAttribute("errorMessage", "You're already on the " + selectedTier + " plan.");
                doGet(request, response);
                return;
            }

            double currentPrice = TierCatalog.get(current.getTier()).getMonthlyPrice();
            double newPrice = TierCatalog.get(selectedTier).getMonthlyPrice();

            PendingUpgrade pendingUpgrade =
                    new PendingUpgrade(current.getTier(), selectedTier, currentPrice, newPrice);

            // Stored in session only - nothing hits the database until ConfirmUpgradeServlet.
            request.getSession().setAttribute("pendingUpgrade", pendingUpgrade);

            response.sendRedirect(request.getContextPath() + "/upgrade/step2");

        } catch (SQLException e) {
            throw new ServletException("Failed to prepare upgrade", e);
        }
    }
}
