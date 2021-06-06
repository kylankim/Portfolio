package com.company;

public interface StockObserver extends Runnable{
    void pricedChanged (PriceChangeEvent x);
}
