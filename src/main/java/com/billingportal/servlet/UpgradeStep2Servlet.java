package com.billingportal.servlet;

import com.billingportal.model.PendingUpgrade;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/upgrade/step2")
public class UpgradeStep2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PendingUpgrade pendingUpgrade =
                (PendingUpgrade) request.getSession().getAttribute("pendingUpgrade");

        if (pendingUpgrade == null) {
            // Nobody picked a tier yet (e.g. user bookmarked this URL) - send them back to step 1.
            response.sendRedirect(request.getContextPath() + "/upgrade/step1");
            return;
        }

        request.setAttribute("pendingUpgrade", pendingUpgrade);
        request.getRequestDispatcher("/upgradeStep2.jsp").forward(request, response);
    }
}
