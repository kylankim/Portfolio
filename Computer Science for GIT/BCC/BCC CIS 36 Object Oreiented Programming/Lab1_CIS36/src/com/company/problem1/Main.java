package com.company.problem1;

import java.util.Scanner;

public class Main {

    /** Enter the Month, Day, Year : 03 32 1998
     Day selected is not in the month of the year
     com.company.problem1.DayException
     have encountered an error please do it again
     Enter the Month, Day, Year : 	at com.company.problem1.calendarDay.checkValidity(calendarDay.java:135)
     at com.company.problem1.calendarDay.<init>(calendarDay.java:22)
     at com.company.problem1.Main.main(Main.java:13)
     02 29 1998
     com.company.problem1.DayException
     at com.company.problem1.calendarDay.checkValidity(calendarDay.java:135)
     at com.company.problem1.calendarDay.<init>(calendarDay.java:22)
     at com.company.problem1.Main.main(Main.java:13)
     Day selected is not in the month of the year
     have encountered an error please do it again
     Enter the Month, Day, Year : 02 29 2000
     you have entered : 02/29/2000
     */

    public static void main(String[] args) {
        Scanner A = new Scanner(System.in);
        calendarDay B;
        boolean checker;
        while (true) {
            System.out.print("Enter the Month, Day, Year in form of (02 03 1998 : ");
            B = new calendarDay(A.nextInt(), A.nextInt(), A.nextInt());
            if (B.availavbility()) {
                System.out.println("you have entered : " + B.toString());
                return;
            }
            System.out.println("have encountered an error please do it again");
        }
    }
}


