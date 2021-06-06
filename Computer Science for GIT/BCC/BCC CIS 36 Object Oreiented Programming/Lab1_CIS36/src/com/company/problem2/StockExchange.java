package com.company.problem2;

import java.util.ArrayList;


public class StockExchange implements StockObserver {

    private int currentPrice;
    private int startingPrice;
    private ArrayList<StockObserver> observerList;
    private StockPricePrinter printer;

    /** basic constructor of the Stockchange objects takes in currentprice and observerlist as a parameter to set the
     * field variable */
    public StockExchange(int currentPrice, ArrayList<StockObserver> observerList) {
        this.startingPrice = currentPrice;
        this.currentPrice = currentPrice;
        this.observerList = observerList;
    }

    /** basic constructor of the Stockchange objects takes in observerlist as a parameter to set the field variable */
    public StockExchange(ArrayList<StockObserver> observerList) {
        this.observerList = observerList;
    }

    /** basic constructor of the Stockchange objects takes in currentprice as a parameter to set the field variable */
    public StockExchange(int currentPrice) {
        this.currentPrice = currentPrice;
        this.startingPrice = currentPrice;
        this.observerList = new ArrayList<>();
    }

    /** according to the pricechangeEvent object, modify the currentPrice of the object  */
    @Override
    public void pricedChanged(PriceChangeEvent x) {
       currentPrice += x.getChangeInPrice();
    }

    /** Randomly choose the price change and construct the pricechangeevent according to the change of the price  */
    public PriceChangeEvent randomPriceChange() {
        int number = (int)(Math.random() * 10);
        PriceChangeEvent tmp;
        if(number >= 5){
            tmp = new PriceChangeEvent(1, this);
        } else {
            tmp = new PriceChangeEvent(-1, this);
        }
        System.out.println("is working 2");
        try {
            Thread.currentThread().sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /** add a StockObserver object to the observerlist */
    public boolean AddObserver(StockObserver X) {
        observerList.add(X);
        System.out.println("successfully added observer");
        return true;
    }

    /** remove a StockObserver object from the observerlist */
    public boolean RemoveObserver(StockObserver X) {
        observerList.remove(X);
        System.out.println("successfully removed observer");
        return true;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public ArrayList<StockObserver> getObserverList() {
        return observerList;
    }

    /** method that works as the description of the program. randomly decide the change of the price, modify each observer and run them */
    @Override
    public void run() {
        StockMonitor sample = new StockMonitor(0);
        while(observerList.size() != 1) {
            PriceChangeEvent a = randomPriceChange();
            pricedChanged(a);
            for (StockObserver x : observerList) {
                x.pricedChanged(a);
                x.run();
                if(x.getClass().equals(sample.getClass())) {
                    sample = (StockMonitor)x;
                    if(sample.getTargetAmount() == Math.abs(startingPrice - currentPrice)) {
                        observerList.remove(x);
                    }
                }
            }
            System.out.println("is working 1");
        }
        observerList.clear();
        return;
    }
}
