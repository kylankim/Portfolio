package com.company.problem1;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;


public class calendarDayUnitTest {


    /** checks whether constructor works properly */
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

    /** checks whether getter and setter method works properly */
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


    /** checks whether compareTo method works properly
     * 03/29/1998 and 03/29/1998 is tne same date
     * 12/25/2000 comes after 03/29/1998 */
    @Test
    public void compareToTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(A);
        assertEquals(A.compareTo(B), 0);
        A.setDay(25);
        A.setMonth(12);
        A.setYear(2000);
        assertEquals(A.compareTo(B), 1);
    }


    /** checks whether toString method works properly
     * the string type of calendarday is 03/29/1998
     * the string type of calendarday is 01/01/2010
     * (have commented the sout method in the toString method,
     * will print out the line above as uncomment the sout method */
    @Test
    public void toStringTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(01,01,2010);
        assertEquals("03/29/1998", A.toString());
        assertEquals("01/01/2010", B.toString());
    }


    /** checks whether copy constructor method works properly
     * 03/29/1998 and 03/29/1998 is tne same date
     * 01/01/2010 and 01/01/2010 is tne same date */
    @Test
    public void copyConstructorTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(01,01,2010);
        calendarDay C = A.copyCalendarDay();
        calendarDay D = B.copyCalendarDay();
        A.compareTo(C);
        B.compareTo(D);
    }


    /** try month, day, year exception whether it works or not when construct or modify the calendarDay
     * Day selected is not in the month of the year
     * com.company.problem1.DayException
     * 	at com.company.problem1.calendarDay.checkValidity(calendarDay.java:135)
     * 	at com.company.problem1.calendarDay.<init>(calendarDay.java:15)
     * 	at com.company.problem1.calendarDayUnitTest.checkExceptionTest(calendarDayUnitTest.java:90)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	<...></...>
     * com.company.problem1.YearException
     * 	at com.company.problem1.calendarDay.checkValidity(calendarDay.java:128)
     * 	at com.company.problem1.calendarDay.setYear(calendarDay.java:56)
     * 	at com.company.problem1.calendarDayUnitTest.checkExceptionTest(calendarDayUnitTest.java:92)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
     * 	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
     * 	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
     * 	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
     * Year should be selected from 1000 to 3000. Out of range
     * 	<...></...> */
    @Test
    public void checkExceptionTest(){
        calendarDay A = new calendarDay("03/32/1998");
        calendarDay B = new calendarDay(01,01,2010);
        B.setYear(999);
        B.toString();
    }

    /** checks whether Arrays.sort method works properly
     * 03/19/1998 came before 01/01/2010
     * 12/03/2015 comes after 03/19/1998
     * 12/03/2015 comes after 01/01/2010
     * 12/31/1001 came before 01/01/2010
     * 12/31/1001 came before 03/19/1998
     * 1 : 12/31/1001
     * 2 : 03/19/1998
     * 3 : 01/01/2010
     * 4 : 12/03/2015 */
    @Test
    public void sortingTest() {
        calendarDay[] random = {new calendarDay(01,01,2010),
                new calendarDay(03,19,1998), new calendarDay(12,03,2015), new calendarDay(12,31,1001)};
        Arrays.sort(random);
        int i = 1;
        for (calendarDay X : random) {
            System.out.println(i + " : " + X.toString());
            i++;
        }

    }

///** checks whether copyCalender method works properly */
//    @Test
//    public void copyCalendarDayTest() {
//        calendarDay A = new calendarDay("03/29/1998");
//        calendarDay C = A.copyCalendarDay();
//
//        assertEquals(29, C.getDay());
//        assertEquals(03, C.getMonth());
//        assertEquals(1998, C.getYear());
//
//        calendarDay B = new calendarDay(01,01,2010);
//        C = B.copyCalendarDay();
//        assertEquals(01, C.getDay());
//        assertEquals(01, C.getMonth());
//        assertEquals(2010, C.getYear());
//    }

    /** checks whether equals method works properly
     * 11/28/2019 is not equal to 03/29/1998
     * 03/29/1998 is not equal to 11/28/2019
     * 03/29/1998 is equal to 03/29/1998 */
    @Test
    public void equalsTest() {
        calendarDay A = new calendarDay("03/29/1998");
        calendarDay B = new calendarDay(11, 28, 2019);
        calendarDay C = A.copyCalendarDay();

        assertEquals(false, A.equals(B));
        assertEquals(false, B.equals(C));
        assertEquals(true, A.equals(C));
    }

    /** checks whether maxday method works properly */
    @Test
    public void maxday(){
        Month a = new Month(2);
        assertEquals(Month.month.Feb, a.monthinschar);
        assertEquals(28, a.maxday());

        Month b = new Month(12);
        assertEquals(Month.month.Dec, b.monthinschar);
        assertEquals(31, b.maxday());

        calendarDay c = new calendarDay(02, 19, 2011);
        assertEquals(Month.month.Feb, new Month(c.getMonth()).monthinschar);
        assertEquals(28, c.getMaxday(c.getMonth()));

        calendarDay d = new calendarDay(02, 19, 2012);
        assertEquals(Month.month.Feb, new Month(d.getMonth()).monthinschar);
        assertEquals(29, d.getMaxday(d.getMonth()));
    }
}
