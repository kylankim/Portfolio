package com.company.problem2;

public class StockCustomer {

    private int currentPrice;
    private int targetAmount;

    /** basic constructor of the Stock customer object that has common field of StockPricePrinter and StockMonitor */
    public StockCustomer(int currentPrice, int targetAmount) {
        this.currentPrice = currentPrice;
        this.targetAmount = targetAmount;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }


}
