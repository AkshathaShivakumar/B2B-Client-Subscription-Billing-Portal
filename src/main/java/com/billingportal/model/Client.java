package com.billingportal.model;

import java.sql.Timestamp;

public class Client {

    private int clientId;
    private String companyName;
    private Timestamp createdAt;

    public Client() {
    }

    public Client(int clientId, String companyName, Timestamp createdAt) {
        this.clientId = clientId;
        this.companyName = companyName;
        this.createdAt = createdAt;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}