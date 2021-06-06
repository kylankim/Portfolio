package com.company.Problem5;


import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class priorityQueueUnitTest {

    /** checks whether it is possible to add and remove the object */
    @Test
    public void addAndRemoveTest(){
        PriorityQueue A = new PriorityQueue();
        A.add("5000");
        A.add(5002);
        A.Add('a');
        assertEquals(true, A.contains("5000"));
        assertEquals(true, A.contains(5002));
        assertEquals(true, A.contains('a'));
        assertEquals(false, A.contains("50004"));

        A.remove("5000");
        assertEquals(false, A.contains("5000"));
        A.clear();
        assertEquals(true, A.isEmpty());
    }

    /** checks whether it is possible to use iterator methods and can return an iterator */
    @Test
    public void iteratorTest(){
        PriorityQueue A = new PriorityQueue();
        A.add("Dylan");
        A.add(5002);
        A.add('a');
        Iterator B = A.getIterator();
        assertEquals(true, B.hasNext());
        assertEquals("Dylan", B.next());
        B.next();
        B.next();
        assertEquals(false, B.hasNext());
    }

    /** checks whether it is possible to return the object into the arrays */
    @Test
    public void toArrayTest(){
        PriorityQueue A = new PriorityQueue();
        A.add("Dylan");
        A.add(5002);
        A.add('a');
        Object[] tmp = A.toArray();
        assertEquals(3, tmp.length);
        assertEquals("Dylan", tmp[0]);
        assertEquals(String.valueOf('a'), tmp[2]);
    }



    /** checks whether offer, poll, peek and element methods works */

    /** java.lang.NullPointerException
     at com.company.Problem5.PriorityQueue.element(PriorityQueue.java:129)
     at com.company.Problem5.priorityQueueUnitTest.peekElementPollTest(priorityQueueUnitTest.java:58)
     at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     */

     @Test
    public void peekElementPollTest(){
        PriorityQueue A = new PriorityQueue();
        assertEquals(null, A.peek());
//        assertEquals(new NullPointerException(), A.element());

        A.add("Dylan");
        A.add(5002);
        A.add('a');
        A.offer("CIS36");
        assertEquals(false, A.offer(new NullPointerException()));
        A.remove("CIS36");


        assertEquals("Dylan", A.peek());
        assertEquals("Dylan", A.peek());

        assertEquals("Dylan", A.element());
        assertEquals("Dylan", A.element());

        assertEquals("Dylan", A.poll());
        assertEquals(5002, A.poll());
        assertEquals('a', A.poll());
        assertEquals(null, A.poll());
    }

    /** Checks whether Remove method works or not */
    @Test
    public void RemoveTest() {
         PriorityQueue<String> A = new PriorityQueue<>();
         A.add("happy");
         A.addAll(List.of("birthday","John"));
         assertEquals("happy", A.Remove());
    }
}
