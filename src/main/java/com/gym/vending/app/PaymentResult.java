package com.gym.vending.app;

public class PaymentResult {
    private final boolean success;
    private final String message;
    private final long responseTime;

    public PaymentResult(boolean success, String message, long responseTime) {
        this.success = success;
        this.message = message;
        this.responseTime = responseTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public long getResponseTime() {
        return responseTime;
    }
} 