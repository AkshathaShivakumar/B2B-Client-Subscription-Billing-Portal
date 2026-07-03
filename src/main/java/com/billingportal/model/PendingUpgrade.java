package com.billingportal.model;

import java.io.Serializable;

/**
 * Holds the user's in-progress plan selection across the 3-step upgrade
 * flow. Stored directly in HttpSession (see UpgradeStep1Servlet) - it is
 * NOT written to the database until ConfirmUpgradeServlet runs.
 */
public class PendingUpgrade implements Serializable {

    private String currentTier;
    private String newTier;
    private double currentPrice;
    private double newPrice;
    private long requestedAt;

    public PendingUpgrade() {
    }

    public PendingUpgrade(String currentTier, String newTier, double currentPrice, double newPrice) {
        this.currentTier = currentTier;
        this.newTier = newTier;
        this.currentPrice = currentPrice;
        this.newPrice = newPrice;
        this.requestedAt = System.currentTimeMillis();
    }

    public String getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(String currentTier) {
        this.currentTier = currentTier;
    }

    public String getNewTier() {
        return newTier;
    }

    public void setNewTier(String newTier) {
        this.newTier = newTier;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public double getPriceDifference() {
        return newPrice - currentPrice;
    }

    public long getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(long requestedAt) {
        this.requestedAt = requestedAt;
    }
}
