package com.billingportal.dao;

import com.billingportal.model.Invoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    // Database connection details
    private String jdbcURL = "jdbc:mysql://localhost:3306/billing_portal?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "user_golu055";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 1. Fetch all invoices for a specific company (for your list dashboard)
    public List<Invoice> getInvoicesByClientId(int clientId) {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoices WHERE client_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setInvoiceId(rs.getInt("invoice_id"));
                inv.setClientId(rs.getInt("client_id"));
                inv.setSubscriptionId(rs.getInt("subscription_id"));
                inv.setAmount(rs.getDouble("amount"));
                inv.setBillingPeriod(rs.getString("billing_period"));
                inv.setGeneratedAt(rs.getTimestamp("generated_at"));
                invoices.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    // 2. Fetch a single invoice by its exact ID (Crucial for your PDF downloader)
    public Invoice getInvoiceById(int invoiceId) {
        String query = "SELECT * FROM invoices WHERE invoice_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Invoice inv = new Invoice();
                inv.setInvoiceId(rs.getInt("invoice_id"));
                inv.setClientId(rs.getInt("client_id"));
                inv.setSubscriptionId(rs.getInt("subscription_id"));
                inv.setAmount(rs.getDouble("amount"));
                inv.setBillingPeriod(rs.getString("billing_period"));
                inv.setGeneratedAt(rs.getTimestamp("generated_at"));
                return inv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 3. Generate and save a brand-new invoice record to the database
    public boolean createInvoice(Invoice invoice) {
        String query = "INSERT INTO invoices (client_id, subscription_id, amount, billing_period, generated_at) VALUES (?, ?, ?, ?, ?)";
        boolean rowInserted = false;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, invoice.getClientId());
            ps.setInt(2, invoice.getSubscriptionId());
            ps.setDouble(3, invoice.getAmount());
            ps.setString(4, invoice.getBillingPeriod());

            // Sets the creation timestamp to the current system time
            ps.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));

            rowInserted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }
}