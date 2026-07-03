package com.billingportal.model;

import java.sql.Date;
import java.sql.Timestamp;

/** Plain data holder mirroring one row of the `subscriptions` table. */
public class Subscription {

    private int subscriptionId;
    private int clientId;
    private String tier;
    private String status;
    private Timestamp startedAt;
    private Date currentPeriodEnd;

    public Subscription() {
    }

    public Subscription(int subscriptionId, int clientId, String tier, String status,
                         Timestamp startedAt, Date currentPeriodEnd) {
        this.subscriptionId = subscriptionId;
        this.clientId = clientId;
        this.tier = tier;
        this.status = status;
        this.startedAt = startedAt;
        this.currentPeriodEnd = currentPeriodEnd;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Date getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    public void setCurrentPeriodEnd(Date currentPeriodEnd) {
        this.currentPeriodEnd = currentPeriodEnd;
    }
}
