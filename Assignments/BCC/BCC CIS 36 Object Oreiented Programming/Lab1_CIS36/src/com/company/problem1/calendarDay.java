package com.company.problem1;
import java.lang.Comparable;


public class calendarDay implements Comparable {
    private int month;
    private int day;
    private int year;

    /** calendatDay constructor by passing the string of data in form of 03/29/1998 as a parameter.
     * checks whether it is a valid date and throws Exception */
    public calendarDay(String date) {
        String[] temp = date.split("/");
        this.month = Integer.parseInt(temp[0]);
        this.day = Integer.parseInt(temp[1]);
        this.year = Integer.parseInt(temp[2]);
        checkValidity();
    }

    /** calendatDay constructor by passing three int types of data in form of 03, 29, 1998 as a parameter.
     *  checks whether it is a valid date and throws Exception */
    public calendarDay(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
        checkValidity();
    }

    /** calendatDay assigner by passing the calenderDay object as a parameter.
     * checks whether it is a valid date and throws Exception */
    public calendarDay(calendarDay X) {
        this.month = X.getMonth();
        this.day = X.getDay();
        this.year = X.getYear();
        checkValidity();
    }

    /** getter method for the month of the calendarDay object */
    public int getMonth() {
        return month;
    }

    /** getter method for the day of the calendarDay object */
    public int getDay() {
        return day;
    }

    /** getter method for the year of the calendarDay object */
    public int getYear() {
        return year;
    }

    /** setter method for the month of the calendarDay object.
     * checks whether it is a valid date and throws Exception */
    public void setMonth(int month) {
        this.month = month;
        checkValidity();
    }

    /** setter method for the day of the calendarDay object.
     * checks whether it is a valid date and throws Exception */
    public void setDay(int day) {
        this.day = day;
        checkValidity();
    }

    /** setter method for the year of the calendarDay object.
     * checks whether it is a valid date and throws Exception */
    public void setYear(int year) {
        this.year = year;
        checkValidity();
    }

    /** copy constructor of the calendarDay object, needs parameter of a calenderDay object to create a copy */
    public calendarDay copyCalendarDay() {
      return new calendarDay(this.getMonth(), this.getDay(), this.getYear());
    }

    /** checks whether each object is equal. return true if two objects are equal else false */
    @Override
    public boolean equals(Object obj) {
        if(this.toString().equals(obj.toString())) {
            System.out.println(obj.toString() + " is equal to " + this.toString());
            return true;
        }
        System.out.println(obj.toString() + " is not equal to " + this.toString());
        return false;
    }

    /** transform calendarDay type object into string type */
    @Override
    public String toString() {
        String X = "";
        int day = this.getDay();
        int month = this.getMonth();
        int year = this.getYear();
        if(month < 10 && day >= 10) {
            X += "0" + month + "/" + day + "/" + year;
        } else if(day < 10 && month >= 10) {
            X += month + "/" + "0" + day + "/" + year;
        } else if(month < 10 && day < 10) {
            X += "0" + month + "/" + "0" + day + "/" + year;
        } else {
            X += month + "/" + day + "/" + year;
        }
        //System.out.println("the string type of calendarday is " + X);
        return X;
    }

    /** finds out the last date of each month of the year */
    public int getMaxday(int month){
        int maxday = new Month(month).maxday();
        if(this.getYear() % 4 == 0 && month == 2) {
            maxday++;
        }
        return maxday;
    }

    /** checks whether date entered is valid and return boolean type result without throwing exception */
    public boolean availavbility(){
        int day = this.getDay();
        int month = this.getMonth();
        int maxDay = getMaxday(month);
        int year = this.getYear();

        if (month > 12 || month <= 0 || year > 3000 || year < 1000 || day <= 0 || day > maxDay) {
            return false;
        }
        return true;
    }

    /** check whether the date entered is a valid date. */
    public boolean checkValidity(){
        int day = this.getDay();
        int month = this.getMonth();
        int maxDay = getMaxday(month);
        int year = this.getYear();

        if(month > 12 || month <= 0) {
            try {
                throw new MonthException();
            } catch (MonthException e) {
                e.printStackTrace();
                return false;
            }
        } else if(year > 3000 || year < 1000) {
            try {
                throw new YearException();
            } catch (YearException e) {
                e.printStackTrace();
                return false;
            }
        } else if (day <= 0 || day > maxDay) {
            try {
                throw new DayException();
            } catch (DayException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /** return 1 for younger and 0 for same and -1 for older one */
    @Override
    public int compareTo(Object o) {
        calendarDay X = (calendarDay) o;
        if(this.getYear() < X.getYear()) {
            System.out.println(this.toString() + " came before " + X.toString());
            return -1;
        } else if (this.getYear() == X.getYear() && this.getMonth() < X.getMonth()) {
            System.out.println(this.toString() + " came before " + X.toString());
            return -1;
        } else if (this.getYear() == X.getYear() && this.getMonth() == X.getMonth() && this.getDay() < X.getDay()){
            System.out.println(this.toString() + " came before " + X.toString());
            return -1;
        } else if(X.toString().equals(this.toString())) {
            System.out.println(X.toString() + " and " + this.toString() + " is tne same date " );
            return 0;
        }
        System.out.println(this.toString() + " comes after " + X.toString());
        return 1;
    }
}
