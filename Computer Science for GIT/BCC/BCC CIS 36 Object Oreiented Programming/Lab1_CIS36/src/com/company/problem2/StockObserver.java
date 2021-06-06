package com.company.problem2;

public interface StockObserver extends Runnable {

    /** void method that takes in the PriceChangedEvent as a parameter */
    void pricedChanged (PriceChangeEvent x);
}
