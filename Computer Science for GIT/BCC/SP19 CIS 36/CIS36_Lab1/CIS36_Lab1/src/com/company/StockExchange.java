package com.company;
import java.util.Timer;

public class StockExchange implements StockObserver {

    private int priceUnit;


    @Override
    public void pricedChanged(PriceChangeEvent x) {

    }


    public void randomPriceChange() {
//        Timer a = new Timer();
        int number = (int) Math.random() * 10;
        if(number >= 5){
            priceUnit++;
        }
        priceUnit--;
    }

    public void AddObserver() {

    }

    public void RemoveObserver() {

    }

    @Override
    public void run() {
        randomPriceChange();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
