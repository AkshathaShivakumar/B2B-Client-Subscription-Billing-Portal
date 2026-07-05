package com.billingportal.servlet;

import com.billingportal.dao.InvoiceDAO;
import com.billingportal.model.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/generate-invoice")
public class GenerateInvoiceServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer clientId = (Integer) session.getAttribute("clientId");

        if (clientId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 1. Extract subscription context details sent from the frontend button form
        int subscriptionId = Integer.parseInt(request.getParameter("subscriptionId"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String billingPeriod = request.getParameter("billingPeriod");

        // 2. Build the temporary model entity object
        Invoice newInvoice = new Invoice();
        newInvoice.setClientId(clientId);
        newInvoice.setSubscriptionId(subscriptionId);
        newInvoice.setAmount(amount);
        newInvoice.setBillingPeriod(billingPeriod);

        // 3. Commit the new billing record to your local MySQL database
        boolean success = invoiceDAO.createInvoice(newInvoice);

        if (success) {
            // Success! Send them to the invoices history list to see their new downloadable file
            response.sendRedirect("invoices");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database write failure generating invoice.");
        }
    }
}