package com.company.problem1;


public class Month {

    /**enum class for monthes */
    enum month {Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sept, Oct, Nov, Dec}

    month monthinschar;

    /** constructor method of Month class by entering int type parameter */
    public Month(int i) {
        month[] tmp = new month[]{month.Jan, month.Feb, month.Mar, month.Apr, month.May, month.Jun, month.Jul,
                month.Aug, month.Sept, month.Oct, month.Nov, month.Dec};
        this.monthinschar = tmp[i-1];
    }

    /** by going through the enum class of month, find the maximum date
     *  of each month. Will return negative number unless maximum date
     *  cannot be found else return the maximum date */
    public int maxday() {
        switch (this.monthinschar) {
            case Apr:
                return 30;

            case Aug:
                return 31;

            case Dec:
                return 31;

            case Feb:
                return 28;

            case Jan:
                return 31;

            case Jul:
                return 31;

            case Jun:
                return 30;

            case Mar:
                return 31;

            case Sept:
                return 30;

            case May:
                return 31;

            case Nov:
                return 30;

            case Oct:
                return 30;

            default:
                System.out.println("Have entered wrong month in char type. Cannot return the max day of the month");
                return -1;
        }
    }
}
