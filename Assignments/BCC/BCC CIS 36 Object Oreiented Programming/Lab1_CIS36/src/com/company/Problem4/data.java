package com.company.Problem4;

public class data {

    private int id;
    private String data;


    /** constructor for data object. */
    public data(int id, String data) {
        this.id = id;
        this.data = data;
    }

    /** getter method for data's id field. */
    public int getId() {
        return id;
    }

    /** getter method for data's string type data field. */
    public String getData() {
        return data;
    }

    /** setter method for data's string type data field. */
    public void setData(String data) {
        this.data = data;
    }
}
