package com.mybank.messaging.dto;

//@JsonPropertyOrder({"status"})
public enum CreditCardStatus {
    OPEN("OPEN"),
    BLOCKED("BLOCKED"),
    UNREGISTERED("UNREGISTERED");

    private String status;

    private CreditCardStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
