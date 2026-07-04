package com.billingportal.model;

import java.sql.Timestamp;

public class Usage {

    private int usageId;
    private int clientId;
    private String metricType;
    private int amount;
    private Timestamp recordedAt;

    public Usage() {
    }

    public Usage(int clientId, String metricType, int amount) {
        this.clientId = clientId;
        this.metricType = metricType;
        this.amount = amount;
    }

    public int getUsageId() {
        return usageId;
    }

    public void setUsageId(int usageId) {
        this.usageId = usageId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }
}
