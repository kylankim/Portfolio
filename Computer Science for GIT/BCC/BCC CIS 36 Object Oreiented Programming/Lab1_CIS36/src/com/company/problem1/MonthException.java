package com.company.problem1;

class MonthException extends Exception {

    /** prints out the message if the month entered is not valid */
    public MonthException() {
        System.out.println("Month should be selected from 1 to 12. Out of range");
    }
}
