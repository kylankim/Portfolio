package com.company.problem2;

public class StockMonitor implements StockObserver  {

    private int step;
    private int targetAmount;
    private int chagedAmount;
    private String name;
    private static int namecount = 1;

    /** basic constructor takes in target amount of price unit necessary to construct through a parameter */
    public StockMonitor(int targetAmount) {
        this.step = 0;
        this.targetAmount = targetAmount;
        this.name = "StockMonitor " + namecount;
        namecount++;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getNamecount() {
        return namecount;
    }

    public static void setNamecount(int namecount) {
        StockMonitor.namecount = namecount;
    }


    /** takes in the priceChangeEvent to modify the field data and check whether price reached target amount */
    @Override
    public void pricedChanged(PriceChangeEvent x) {
        step++;
        chagedAmount += x.getChangeInPrice();
        System.out.println("is working 3");
    }

    /** Checks whether the price reached the target price set when the class is initiated */
    @Override
    public void run() {
        while(true){
            if(Math.abs(chagedAmount) == targetAmount) {
                System.out.println("It took " + step + " steps to reach the target amount of price unit(" + targetAmount + ")");
                break;
            }
        }
    }
}
