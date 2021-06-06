package com.company.Problem3;


import java.util.ArrayList;
import java.util.Iterator;

public class Student implements Iterator<Course> {

    private String firstName;
    private String lastName;
    private int ID;
    private String Email;
    private CourseList courseList;


    /** Constructor of the Student class. takes in student data amd courselist as a parameter */

    public Student(String firstName, String lastName, int ID, String email, CourseList courseList) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.Email = email;
        this.courseList = courseList;
    }

    /** Constructor of the Student class. takes in student data. has an empty course list as a parameter */
    public Student(String firstName, String lastName, int ID, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.Email = email;
        this.courseList = new CourseList(new ArrayList<Course>());
    }

    /** setter method of the course list */
    public void setCourse(CourseList course) {
        this.courseList = course;
    }

    /** getter method of the course list */
    public CourseList getCourseList() {
        return courseList;
    }

    /** getter method of the first name */
    public String getFirstName() {
        return firstName;
    }

    /** getter method of the last name */
    public String getLastName() {
        return lastName;
    }

    /** getter method of the Student ID */
    public int getID() {
        return ID;
    }
    /** getter method of the Student Email */
    public String getEmail() {
        return Email;
    }

    /** setter method of the first name */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** setter method of the last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** setter method of the Student ID */
    public void setID(int ID) {
        this.ID = ID;
    }

    /** setter method of the Student Email */
    public void setEmail(String email) {
        Email = email;
    }

    /** iterator method that checks whether the class has more course in the course list */
    @Override
    public boolean hasNext() {
        if(courseList.getIndex() < courseList.getArrayCourseList().size()) {
            return true;
        }
        return false;
    }

    /** iterator method that returns the course from the course list */
    @Override
    public Course next() {
        int tmp = courseList.getIndex();
        courseList.setIndex(tmp + 1);
        return courseList.getArrayCourseList().get(tmp);
    }

}