package com.billingportal.servlet;

import com.billingportal.dao.InvoiceDAO;
import com.billingportal.model.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/invoices")
public class InvoiceListServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 1. TEMPORARY MOCK LOGIN
        // This simulates that Client #1 (Acme Corp) is logged in.
        // We will remove this line later once Member A finishes the real login filter!
        session.setAttribute("clientId", 1);

        // 2. Get the logged-in client's ID from the session
        Integer clientId = (Integer) session.getAttribute("clientId");

        if (clientId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 3. Fetch the invoices from the database using your DAO
        List<Invoice> clientInvoices = invoiceDAO.getInvoicesByClientId(clientId);

        // 4. Send the invoice list data to the JSP file
        request.setAttribute("invoiceList", clientInvoices);

        // 5. Forward the user to your invoices JSP view page
        request.getRequestDispatcher("/invoices.jsp").forward(request, response);
    }
}