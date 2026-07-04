package com.billingportal.dao;

import com.billingportal.model.Usage;

import java.sql.*;
import java.util.*;

public class UsageDAO {

    /**
     * Returns total usage per metric type for a given client.
     * e.g. { "API_CALLS": 4500, "STORAGE_MB": 1200 }
     */
    public Map<String, Integer> getUsageByClient(int clientId) throws SQLException {
        Map<String, Integer> usage = new LinkedHashMap<>();
        String sql = "SELECT metric_type, SUM(amount) AS total " +
                     "FROM usage_records WHERE client_id = ? GROUP BY metric_type";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usage.put(rs.getString("metric_type"), rs.getInt("total"));
                }
            }
        }
        return usage;
    }

    /**
     * Returns the raw list of individual usage records for a client
     * (useful later if you want a history table, not just totals).
     */
    public List<Usage> getUsageHistory(int clientId) throws SQLException {
        List<Usage> history = new ArrayList<>();
        String sql = "SELECT * FROM usage_records WHERE client_id = ? ORDER BY recorded_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usage u = new Usage();
                    u.setUsageId(rs.getInt("usage_id"));
                    u.setClientId(rs.getInt("client_id"));
                    u.setMetricType(rs.getString("metric_type"));
                    u.setAmount(rs.getInt("amount"));
                    u.setRecordedAt(rs.getTimestamp("recorded_at"));
                    history.add(u);
                }
            }
        }
        return history;
    }

    /**
     * Inserts one usage record — used by the simulator/seed servlet.
     */
    public void insertUsageRecord(int clientId, String metricType, int amount) throws SQLException {
        String sql = "INSERT INTO usage_records (client_id, metric_type, amount) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            stmt.setString(2, metricType);
            stmt.setInt(3, amount);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Returns the total number of usage records stored for a client.
     * Used as a simplified real "storage" metric — grows as more
     * usage events are logged, rather than being randomly generated.
     */
    public int getRecordCount(int clientId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM usage_records WHERE client_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }
}