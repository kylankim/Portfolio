package com.company.Problem3;

public class Course {

    private String name;
    private String description;

    /** Constructor of the class takes name and description as parameter */
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** Constructor of the class takes in course name as parameter */
    public Course(String name) {
        this.name = name;
        this.description = null;
    }

    /** getter method about the name of the course */
    public String getName() {
        return name;
    }

    /** getter method about the description of the course */
    public String getDescription() {
        return description;
    }
}
