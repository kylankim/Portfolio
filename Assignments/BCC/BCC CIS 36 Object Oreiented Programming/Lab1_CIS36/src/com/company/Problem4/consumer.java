package com.company.Problem4;

public class consumer extends Thread implements Runnable {

    private int id;
    private data data;
    private meetingRoom room;
    private int sleepingtime;

    /** basic constructor of the consumer object */
    public consumer(int id, meetingRoom room, int sleepingtime) {
        this.id = id;
        this.room = room;
        this.data = null;
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
        while (true) {
            sleep();
            while (true) {
                if (room.isRoomEmpty() && !room.dataSpaceAvailable()) {
                    data tmp = room.getData();
                    if(tmp.getId() != id) {
                        System.out.println("Consumer " + id + " enters meeting room.");
                        System.out.println("Consumer " + id + " enters waiting room.");
                    }
                    while(tmp.getId() != id) {
                        if(room.getData() != null) {
                            tmp = room.getData();
                        }
                    }
                    room.setRoomEmpty(false);
                    data = tmp;
                    room.removeData();
                    System.out.println("Consumer " + id + " enters meeting room/ remove data");
                    room.setRoomEmpty(true);
                    break;
                }

            }
        }
    }
}
