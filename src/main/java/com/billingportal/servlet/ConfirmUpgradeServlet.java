package com.billingportal.servlet;

import com.billingportal.dao.SubscriptionDAO;
import com.billingportal.model.PendingUpgrade;
import com.billingportal.model.Subscription;
import com.billingportal.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/upgrade/confirm")
public class ConfirmUpgradeServlet extends HttpServlet {

    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        PendingUpgrade pendingUpgrade = (PendingUpgrade) session.getAttribute("pendingUpgrade");

        if (pendingUpgrade == null) {
            response.sendRedirect(request.getContextPath() + "/upgrade/step1");
            return;
        }

        int clientId = SessionUtil.getCurrentClientId(request);

        try {
            // This is the actual write to MySQL - cancels the old row, inserts the new one.
            Subscription newSubscription = subscriptionDAO.upgrade(clientId, pendingUpgrade.getNewTier());

            // Done with the in-progress selection now that it's persisted.
            session.removeAttribute("pendingUpgrade");

            request.setAttribute("newSubscription", newSubscription);
            request.getRequestDispatcher("/upgradeConfirmed.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Failed to save upgraded subscription", e);
        }
    }
}
