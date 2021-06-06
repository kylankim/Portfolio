package com.company;

public class StockMonitor extends StockCustomer implements StockObserver  {

    private int step;
    private int currentPrice;
    private int targetPrice;

    public StockMonitor(int targetPrice) {
        this.step = 0;
        this.currentPrice = 0;
        this.targetPrice = targetPrice;
    }

    @Override
    public void pricedChanged(PriceChangeEvent x) {

    }

    @Override
    public void run() {
        while(currentPrice != targetPrice) {
            // run the method from StockExchange
            step++;
        }
        System.out.println("It took " + step + "steps to reach the target price(" + targetPrice + ")");
    }
}
