package com.company;

public class PriceChangeEvent {
    int changeInPrice;
    StockExchange reference;

    public PriceChangeEvent(int changeInPrice, StockExchange reference) {
        this.changeInPrice = changeInPrice;
        this.reference = reference;
    }

    public void setChangeInPrice(int changeInPrice) {
        this.changeInPrice = changeInPrice;
    }

    public void setReference(StockExchange reference) {
        this.reference = reference;
    }

    public int getChangeInPrice() {
        return changeInPrice;
    }

    public StockExchange getReference() {
        return reference;
    }
}
