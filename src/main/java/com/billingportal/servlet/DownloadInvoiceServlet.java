package com.billingportal.servlet;

import com.billingportal.dao.InvoiceDAO;
import com.billingportal.model.Invoice;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/download-invoice")
public class DownloadInvoiceServlet extends HttpServlet {
    private InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Grab the invoice ID passed from the JSP button link
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Invoice ID");
            return;
        }

        int invoiceId = Integer.parseInt(idParam);

        // 2. Fetch the concrete invoice data from your MySQL database
        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId);
        if (invoice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
            return;
        }

        // 3. Configure the response headers to trigger a direct PDF browser download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Invoice_INV-" + invoiceId + ".pdf");

        // 4. Generate the PDF document layout using OpenPDF
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Set up clean document typography/fonts
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Font headingFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL);

            // Document Header
            Paragraph title = new Paragraph("B2B CLIENT SUBSCRIPTION INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(25);
            document.add(title);

            // Summary Meta Information
            document.add(new Paragraph("Invoice Reference ID: INV-" + invoice.getInvoiceId(), headingFont));
            document.add(new Paragraph("Client Account ID: " + invoice.getClientId(), normalFont));
            document.add(new Paragraph("Subscription Contract ID: " + invoice.getSubscriptionId(), normalFont));
            document.add(new Paragraph("Generated Stamp: " + invoice.getGeneratedAt(), normalFont));
            document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------", normalFont));

            // Line Item Billing Layout Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15);
            table.setSpacingAfter(25);

            table.addCell(new Paragraph("Billing Description Segment", headingFont));
            table.addCell(new Paragraph("Amount Due (USD)", headingFont));

            table.addCell(new Paragraph("Subscription Operations Fee (" + invoice.getBillingPeriod() + ")", normalFont));
            table.addCell(new Paragraph("$" + invoice.getAmount(), normalFont));

            document.add(table);

            // Document Footer / Terms
            Paragraph total = new Paragraph("Total Amount Paid / Settled: $" + invoice.getAmount(), headingFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            Paragraph footer = new Paragraph("\n\nThank you for choosing our platform service!", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } catch (Exception e) {
            throw new ServletException("Error creating PDF generation stream", e);
        }
    }
}