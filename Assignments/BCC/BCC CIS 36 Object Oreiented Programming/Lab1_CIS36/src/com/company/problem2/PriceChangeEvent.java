package com.company.problem2;

import com.company.problem2.StockExchange;

public class PriceChangeEvent {
    private int changeInPrice;
    private StockExchange stockexchange;

    /** basic constructor of the priceChangeEvent takes in the change of price and the regference of the StockExchange */
    public PriceChangeEvent(int changeInPrice, StockExchange X) {
        this.changeInPrice = changeInPrice;
        this.stockexchange = X;
    }

    public StockExchange getStockexchange() {
        return stockexchange;
    }

    public void setChangeInPrice(int changeInPrice) {
        this.changeInPrice = changeInPrice;
    }

    public int getChangeInPrice() {
        return changeInPrice;
    }
}
