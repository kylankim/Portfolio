package com.company;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;


public class calendarDayUnitTest {

    @Test
    public void constructorTest() {
        calendarDay A = new calendarDay("03/29/1998");
        assertEquals(A.getDay(), 29);
        assertEquals(A.getMonth(), 03);
        assertEquals(A.getYear(), 1998);

        calendarDay B = new calendarDay(A);
        assertEquals(B.getDay(), 29);
        assertEquals(B.getMonth(), 03);
        assertEquals(B.getYear(), 1998);

        calendarDay C = new calendarDay(03,29,1998);
        assertEquals(C.getDay(), 29);
        assertEquals(C.getMonth(), 03);
        assertEquals(C.getYear(), 1998);

    }

    @Test
    public void mutatorTest(){
        calendarDay A = new calendarDay("03/29/1998");
        A.setDay(25);
        A.setMonth(12);
        A.setYear(2000);
        assertEquals(A.getDay(), 25);
        assertEquals(A.getMonth(), 12);
        assertEquals(A.getYear(), 2000);
    }

    @Test
    public void compareToTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(A);
        assertEquals(A.equals(B), 1);
        A.setDay(25);
        A.setMonth(12);
        A.setYear(2000);
        assertEquals(A.equals(B), 0);
    }

    @Test
    public void toStringTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(01,01,2010);
        assertEquals("03/29/1998", A.toString());
        assertEquals("01/01/2010", B.toString());
    }

    @Test
    public void copyConstructorTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(01,01,2010);
        calendarDay C = A.copyCalendarDay();
        calendarDay D = B.copyCalendarDay();
        A.compareTo(C);
        B.compareTo(D);
    }

    /** try month, day, year exception whether it works or not when construct or modify the calendarDay */
    @Test
    public void checkExceptionTest(){
        calendarDay A = new calendarDay("03/32/1998");
        calendarDay B = new calendarDay(01,01,2010);
        B.setYear(999);
        B.toString();
    }

    @Test
    public void sortingTest() {
        calendarDay[] random = {new calendarDay(01,01,2010),
                new calendarDay(03,19,1998), new calendarDay(12,03,2015)};
        Arrays.sort(random);
        int i = 1;
        for (calendarDay X : random) {
            System.out.println(i + " : " + X.toString());
            i++;
        }

    }
}
