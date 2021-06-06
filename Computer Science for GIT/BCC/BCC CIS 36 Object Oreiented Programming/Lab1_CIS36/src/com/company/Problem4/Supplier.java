package com.company.Problem4;

public class Supplier extends Thread implements Runnable {

    private int id;
    private data data;
    private meetingRoom room;
    private int sleepingtime;

    /** basic constructor of the Supplier object */
    public Supplier (int id, meetingRoom room, int sleepingtime) {

        this.id = id;
        this.data = new data(id,null);
        this.room = room;
        this.sleepingtime = sleepingtime;
    }

    /** getter method og the id field of the object */
    public long getId() {
        return id;
    }

    /** setter method og the id field of the object */
    public void setId(int id) {
        this.id = id;
    }

    /** getter method og the data field of the object */
    public data getData() {
        return data;
    }

    /** setter method og the data field of the object */
    public void setData(data data) {
        this.data = data;
    }


    public void dataRenewal() {
        data.setData("data id (" + id + ") : " +( (int) Math.random() * 1000));
        System.out.println("Supplier " + id + " produces data");
    }

    /** Makes the thread sleep for some amount of time */
    public void sleep(){
        try {
            System.out.println("Supplier " + id + " sleeps for " + sleepingtime + "ms");
            Thread.currentThread().sleep(sleepingtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /** What this thread will do while working */
    @Override
    public void run() {
        sleep();
    }
}
