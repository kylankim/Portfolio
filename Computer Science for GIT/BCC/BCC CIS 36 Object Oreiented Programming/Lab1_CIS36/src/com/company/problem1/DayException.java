package com.company.problem1;

class DayException extends Exception {

    /** prints out the message if the day entered is not valid */
    public DayException() {
        System.out.println("Day selected is not in the month of the year");
    }
}
