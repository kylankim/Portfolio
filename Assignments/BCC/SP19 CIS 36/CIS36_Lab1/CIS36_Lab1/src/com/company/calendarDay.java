package com.company;
import java.lang.Comparable;


class DayException extends Exception {
    public DayException() {
        System.out.println("Day selected is not in the month");
    }
}


class MonthException extends Exception {
    public MonthException() {
        System.out.println("Month should be selected from 1 to 12. Out of range");
    }
}


class YearException extends Exception {
    public YearException() {
        System.out.println("Year should be selected from 1000 to 3000. Out of range");
    }
}

public class calendarDay implements Comparable {
    private int month;
    private int day;
    private int year;

    public calendarDay(String date) {
        String[] temp = date.split("/");
        this.month = Integer.parseInt(temp[0]);
        this.day = Integer.parseInt(temp[1]);
        this.year = Integer.parseInt(temp[2]);
        checkValidity();
    }

    public calendarDay(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
        checkValidity();
    }

    public calendarDay(calendarDay X) {
        this.month = X.getMonth();
        this.day = X.getDay();
        this.year = X.getYear();
        checkValidity();
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
        checkValidity();
    }

    public void setDay(int day) {
        this.day = day;
        checkValidity();
    }

    public void setYear(int year) {
        this.year = year;
        checkValidity();
    }

    public calendarDay copyCalendarDay() {
        calendarDay result = new calendarDay(this.getMonth(), this.getDay(), this.getYear());
        return result;
    } // need to make a test

//    @Override
//    public boolean equals(calendarDay X) {
//        if(this.getDay() == X.getDay() && this.getMonth() == X.getMonth() && this.getYear() == this.getYear()) {
//            System.out.println(X.toString() + " is equal to " + this.toString());
//            return true;
//        }
//        System.out.println(X.toString() + " is not equal to " + this.toString());
//        return false;
//    }

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
        System.out.println("the string type of calendarday is " + X);
        return X;
    }

    /** check whether the date is in the month. for example 29th is distributed once a four year. */
    public void checkValidity(){
        int day = this.getDay();
        int month = this.getMonth();
        int year = this.getYear();

        if(month > 12 || month <= 0) {
            try {
                throw new MonthException();
            } catch (MonthException e) {
                e.printStackTrace();
            }
        } else if(year > 3000 || year < 1000) {
            try {
                throw new YearException();
            } catch (YearException e) {
                e.printStackTrace();
            }
        } else if(month == 2 && year % 4 == 0) {
            if(day <= 0 || day > 28) {
                try {
                    throw new DayException();
                } catch (DayException e) {
                    e.printStackTrace();
                }
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day <= 0 || day > 30) {
                try {
                    throw new DayException();
                } catch (DayException e) {
                    e.printStackTrace();
                }
            }
        } else if (day <= 0 || day > 31) {
            try {
                throw new DayException();
            } catch (DayException e) {
                e.printStackTrace();
            }
        }
    }



    /** return 1 for true and 0 for false */
    @Override
    public int compareTo(Object o) {
        calendarDay X = (calendarDay) o;
        if(this.getDay() == X.getDay() && this.getMonth() == X.getMonth() && this.getYear() == X.getYear()) {
            System.out.println(X.toString() + " is equal to " + this.toString());
            return 1;
        }
        System.out.println(X.toString() + " is not equal to " + this.toString());
        return 0;
    }
}
