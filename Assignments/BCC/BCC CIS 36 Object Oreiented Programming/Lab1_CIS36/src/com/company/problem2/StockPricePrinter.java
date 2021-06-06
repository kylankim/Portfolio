package com.company.problem2;

public class StockPricePrinter implements StockObserver {

    private int chagedAmount;
    private int currentPrice;
    private int previoudPrice;

    /** basic constructor doesn't take in data necessary through a parameter */
    public StockPricePrinter() {
        this.chagedAmount = 0;
        this.currentPrice = 0;
        this.previoudPrice = 0;
    }

    /** basic constructor takes in data necessary through a parameter */
    public StockPricePrinter(int currentPrice) {
        this.chagedAmount = 0;
        this.previoudPrice = 0;
        this.currentPrice = currentPrice;
    }

    public int getChagedAmount() {
        return chagedAmount;
    }

    public void setChagedAmount(int chagedAmount) {
        this.chagedAmount = chagedAmount;
    }

    public int getPrevioudPrice() {
        return previoudPrice;
    }

    public void setPrevioudPrice(int previoudPrice) {
        this.previoudPrice = previoudPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    /** takes the procechange event as a parameter to modify the data stored in the class */
    @Override
    public void pricedChanged(PriceChangeEvent x) {
        this.chagedAmount = x.getChangeInPrice();
        currentPrice += chagedAmount;
    }

    /** prints out the result if there has been a change in the price */
    @Override
    public void run() {
        while(true){
            if(previoudPrice != currentPrice) {
                System.out.println("The price has been modified by " + chagedAmount + "therefore current price is " + currentPrice);
                previoudPrice = currentPrice;
            }
        }
    }
}
