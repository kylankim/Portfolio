package com.company.Problem4;

import java.util.ArrayList;

public class Main {

    /** Supplier 2 sleeps for 464ms
     Supplier 9 sleeps for 448ms
     Supplier 6 sleeps for 745ms
     Supplier 3 sleeps for 163ms
     Supplier 8 sleeps for 421ms
     Supplier 4 sleeps for 598ms
     Supplier 9 sleeps for 843ms
     Supplier 8 sleeps for 792ms
     Supplier 4 sleeps for 811ms
     Supplier 2 sleeps for 977ms
     Supplier 1 sleeps for 147ms
     Supplier 0 sleeps for 480ms
     Supplier 5 sleeps for 678ms
     Supplier 3 sleeps for 450ms
     Supplier 1 sleeps for 74ms
     Supplier 5 sleeps for 78ms
     Supplier 6 sleeps for 280ms
     Supplier 7 sleeps for 384ms
     Supplier 7 sleeps for 829ms
     Supplier 0 sleeps for 809ms
     Supplier 1 produces data
     Supplier 1 enters meeting room/leaves data for Consumer 1
     Supplier 1 leaves the meeting room
     Consumer 1 enters meeting room/ remove data
     Supplier 1 sleeps for 147ms
     Supplier 5 produces data
     Supplier 5 enters meeting room/leaves data for Consumer 5
     Supplier 5 leaves the meeting room
     Consumer 3 enters meeting room.
     Consumer 3 enters waiting room.
     Consumer 6 enters meeting room.
     Consumer 6 enters waiting room.
     Consumer 1 enters meeting room.
     Consumer 1 enters waiting room.
     Consumer 9 enters meeting room.
     Consumer 9 enters waiting room.
     Consumer 2 enters meeting room.
     Consumer 2 enters waiting room.
     Consumer 0 enters meeting room.
     Consumer 0 enters waiting room.
     Consumer 4 enters meeting room.
     Consumer 4 enters waiting room.
     Consumer 5 enters meeting room/ remove data
     Supplier 5 sleeps for 678ms
     Supplier 6 produces data
     Supplier 6 enters meeting room/leaves data for Consumer 6
     Supplier 6 leaves the meeting room
     Consumer 7 enters meeting room.
     Consumer 7 enters waiting room.
     Consumer 8 enters meeting room.
     Consumer 8 enters waiting room.
     Consumer 5 enters meeting room.
     Consumer 5 enters waiting room. */

    public static void main(String[] args) {
        meetingRoom a = new meetingRoom();
        ArrayList<Thread> b = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            b.add(new Supplier(i,a, (int) (Math.random()*1000)));
            b.add(new consumer(i,a, (int) (Math.random()*1000)));
        }
        synchronized (a) {
            for (Thread x : b) {
                x.start();
            }
        }
    }
}
