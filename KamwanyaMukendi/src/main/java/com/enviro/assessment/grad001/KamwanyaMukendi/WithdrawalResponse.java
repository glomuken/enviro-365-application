package com.enviro.assessment.grad001.KamwanyaMukendi;

public class WithdrawalResponse {
    private double previousBalance;
    private double withdrawalAmount;
    private double currentBalance;
    private String email;

    // Getters and

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public String getEmail() {
        return email;
    }
}

