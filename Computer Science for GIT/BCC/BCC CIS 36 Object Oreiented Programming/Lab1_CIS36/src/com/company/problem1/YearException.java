package com.company.problem1;

class YearException extends Exception {

    /** prints out the message if the year entered is not valid */
    public YearException() {
        System.out.println("Year should be selected from 1000 to 3000. Out of range");
    }
}
