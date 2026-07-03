package com.billingportal.dao;

import com.billingportal.model.Subscription;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SubscriptionDAO {

    /** Returns the client's currently ACTIVE subscription, or null if none. */
    public Subscription getActiveSubscription(int clientId) throws SQLException {
        String sql = "SELECT * FROM subscriptions WHERE client_id = ? AND status = 'ACTIVE' "
                + "ORDER BY started_at DESC LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        }
    }

    /**
     * Performs the upgrade as a single transaction:
     *   1. marks the client's current ACTIVE subscription as CANCELLED
     *   2. inserts a new ACTIVE subscription row for the new tier
     * Both steps succeed together or both roll back, so a client is never
     * left with zero active subscriptions or two active ones at once.
     */
    public Subscription upgrade(int clientId, String newTier) throws SQLException {
        String cancelSql = "UPDATE subscriptions SET status = 'CANCELLED' "
                + "WHERE client_id = ? AND status = 'ACTIVE'";
        String insertSql = "INSERT INTO subscriptions (client_id, tier, status, current_period_end) "
                + "VALUES (?, ?, 'ACTIVE', ?)";

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement cancelPs = conn.prepareStatement(cancelSql)) {
                cancelPs.setInt(1, clientId);
                cancelPs.executeUpdate();
            }

            Date periodEnd = Date.valueOf(LocalDate.now().plusMonths(1));
            int newSubscriptionId;
            try (PreparedStatement insertPs = conn.prepareStatement(
                    insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertPs.setInt(1, clientId);
                insertPs.setString(2, newTier);
                insertPs.setDate(3, periodEnd);
                insertPs.executeUpdate();

                try (ResultSet keys = insertPs.getGeneratedKeys()) {
                    keys.next();
                    newSubscriptionId = keys.getInt(1);
                }
            }

            conn.commit();

            Subscription result = new Subscription();
            result.setSubscriptionId(newSubscriptionId);
            result.setClientId(clientId);
            result.setTier(newTier);
            result.setStatus("ACTIVE");
            result.setCurrentPeriodEnd(periodEnd);
            return result;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    private Subscription mapRow(ResultSet rs) throws SQLException {
        return new Subscription(
                rs.getInt("subscription_id"),
                rs.getInt("client_id"),
                rs.getString("tier"),
                rs.getString("status"),
                rs.getTimestamp("started_at"),
                rs.getDate("current_period_end")
        );
    }
}
