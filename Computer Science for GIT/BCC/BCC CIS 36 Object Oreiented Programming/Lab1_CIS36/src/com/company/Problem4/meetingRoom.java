package com.company.Problem4;

public class meetingRoom {

    int id;
    private data data;

    /** constructor of the class that constructs an empty room */
    public meetingRoom() {
        this.data = null;
        this.id = Integer.MIN_VALUE;
    }

    /** getter method that returns the data stored in the meeting room */
    public data getData() {
        return data;
    }

    synchronized void put() {
        while(data != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Adding item for thread" + id);
        }
//        setData();
//        setid();
        System.out.println();

    }

    /** setter method for data in the meeting room */
    public void setData(data data) {
        this.data = data;
    }

    /** remove method for data in the meeting room */
    public void removeData(){
        setData(null);
    }

    private void setid(int id) {
        this.id = id;
    }

    /** checks whether the data spot in the room is empty */
    public boolean dataSpaceAvailable(){
        if (data == null) {
            return true;
        }
        return false;
    }
}
