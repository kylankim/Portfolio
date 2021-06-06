package com.company.Problem3;

import java.util.ArrayList;


public class CourseList {

    private ArrayList<Course> coursearrayList;
    private int index;


    /** Constructor of the list type object class of array lists. */
    public CourseList(ArrayList<Course> courseList) {
        this.coursearrayList = courseList;
    }

    /** Constructor of the list type object class of array lists. returns emty list */
    public CourseList() {
        coursearrayList = new ArrayList<>();
    }
    /** setting the first column of the list using the course list whose class is arraylist as a parameter. */
    public void setCourseList(ArrayList<Course> courseList) {
        this.index = 0;
        this.coursearrayList = courseList;
    }

    /** getter method of the array list type course list. */
    public ArrayList<Course> getArrayCourseList() {
        return coursearrayList;
    }

    /** adds a course to the course list by taking in the course object as a parameter.
     *  Checks whether the course is in the list or not and prints out the result */
    public boolean addCourse(Course course) {
        for (Course X : coursearrayList) {
            if(X.getName() == course.getName()) {
                System.out.println(course.getName() + " cannot be added to the course list (duplicate)");
                return false;
            }
        }
        coursearrayList.add(course);
        System.out.println(course.getName() + " is added to the course list");
        return true;
    }

    /** adds a course to the course list by taking in string type course nmae as a parameter.
     *  Checks whether the course is in the list or not and prints out the result */
    public boolean addCourse(String courseName) {
        return addCourse(new Course(courseName));
    }

    /** searcjong a course  the course list by taking in the String tyoe coursename as a parameter. */

    public boolean searchByCourseName(String courseName) {
        for(Course x : coursearrayList) {
            if(x.getName() == courseName){
                return true;
            }
        }
        return false;
    }


    /** getter method of the index */
    public int getIndex() {
        return index;
    }

    /** setter method of the index */
    public void setIndex(int index) {
        this.index = index;
    }
}

