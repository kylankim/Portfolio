package com.company.Problem4;

import static org.junit.Assert.*;
import org.junit.Test;

public class unitTest {

    /** basic constructor and getter/setter method unit test
     * Supplier 1 produces data */
    @Test
    public void constructorTest() {
        data a = new data(1234, "hi");
        assertEquals(1234,a.getId());
        assertEquals("hi", a.getData());

        meetingRoom b = new meetingRoom();

        assertEquals(null, b.getData());
        assertEquals(true, b.dataSpaceAvailable());
        assertEquals(true, b.isRoomEmpty());

        Supplier c = new Supplier(1, b, 350);
        c.dataRenewal();
        assertEquals(1, c.getData().getId());
        assertEquals(1, c.getId());

        consumer d = new consumer(2,b,350);
        assertEquals(2, d.getId());
    }

    /** Checks each method in the data object class works */
    @Test
    public void meetingRoomTest() {
        meetingRoom b = new meetingRoom();
        assertEquals(true, b.dataSpaceAvailable());
        b.setData(new data(1, "huhu"));
        assertEquals("huhu", b.getData().getData());
        assertEquals(false, b.dataSpaceAvailable());
        b.removeData();
        assertEquals(true, b.dataSpaceAvailable());
    }


}
