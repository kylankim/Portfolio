package com.company.Problem3;

import java.util.ArrayList;
import java.util.Iterator;

public class StudentrecordManager {

    private ArrayList<Student> studentList;
    private ArrayList<Integer> searchData;
    private Student searchedStudent;
    private Iterator<Student> studentIterator;
    private int index;


    /** Constructor of Student Iterator  */
    public Iterator<Student> iteratorMaker() {
        this.index = 0;
        studentIterator = new Iterator<>() {

            @Override
            public boolean hasNext() {
                if(studentList.size() > index) {
                    return true;
                }
                return false;
            }

            @Override
            public Student next() {
                index++;
                return studentList.get(index-1);
            }
        };
        return studentIterator;
    }

    /** Constructor of the class StudentrecordManager class. takes in ArrayList<Student>  */
    public StudentrecordManager(ArrayList<Student> studentList) {
        this.studentList = studentList;
        this.searchData = new ArrayList<>();
        this.searchedStudent = null;
        this.studentIterator = iteratorMaker();
    }

    /** Constructor of the class StudentrecordManager class. */
    public StudentrecordManager() {
        this.studentList = new ArrayList<>();
        this.searchData = new ArrayList<>();
        this.searchedStudent = null;
        this.studentIterator = iteratorMaker();
    }

    /** Adding a student by taking in the dataof student as a parameter */
    public void addStudent(String firstName, String lastName, int ID, String email, CourseList courseList) {
        studentList.add(new Student(firstName, lastName, ID, email, courseList));
        System.out.println("student " + firstName + " " + lastName + " is added to the student list");
    }

    /** removing a student by taking in the dataof student as a parameter */
    public void removeStudent(int ID) {
        searchStudent(ID);
        if (searchedStudent != null) {
            studentList.remove(searchedStudent);
            System.out.println("Student with student ID " + ID + "has been removed from the student list.");
        }
        System.out.println("Student with student ID " + ID + "does not exist in the student list.");
    }

    /** searching a student by taking in the name of a student as a parameter. checks whether it is even
     * a part of the name. Return type of the method is void*/
    private void searchStudent(String Name) {
        searchData = null;
        int index;
        ArrayList<Integer> lastNameSearchRecord = new ArrayList<>();
        for (Student x : studentList) {
            if (x.getLastName().contains(Name) || x.getFirstName().contains(Name)) {
                index = studentList.indexOf(x);
                System.out.println("Student whose name has " + Name + " is in the list and the index of the student is " + x);
                lastNameSearchRecord.add(index);
                searchData = lastNameSearchRecord;
            }
        }
        System.out.println("Cannot find the student whose name has " + Name);
    }

    /** searching a student by taking in the name of a student as a parameter. checks whether it is even
     * a part of the name. Return type of the method is ArraList<></>*/
    public ArrayList<Student> search(String Name){
        searchStudent(Name);
        return studentList;
    }

    /** searching a student by taking in the student ID of a student as a parameter. checks whether it is even
     * a part of the name. Return type of the method is void*/
    public void searchStudent(int ID) {
        searchedStudent = null;
        int index;
        for (Student x : studentList) {
            if (x.getID() == ID) {
                index = studentList.indexOf(x);
                System.out.println("Student is in the list and the index of the student is " + x);
                searchedStudent = studentList.get(index);
            }
        }
        System.out.println("Cannot find the student wwhose student ID is " + ID);
    }

    /** add the course into the Student's course list by searching the Student by ID  */
    public void addcourse(int ID, String courseName) {
        searchStudent(ID);
        if (searchedStudent != null) {
            searchedStudent.getCourseList().getArrayCourseList().add(new Course(courseName));
            System.out.println(courseName + " is successfully added to the student's course list");
        }
    }

    /** remove the course into the Student's course list by searching the Student by ID  */
    public void removesourse(int ID, String courseName) {
        searchStudent(ID);
        if (searchedStudent != null) {
            searchedStudent.getCourseList().getArrayCourseList().remove(courseName);
            System.out.println(courseName + " is successfully removed from the student's course list");
        }
    }

    /** Adding a Student into the StudentRecordManager */
    public boolean addStudent(Student A) {
        if(!studentList.contains(A)) {
            studentList.add(A);
            System.out.println("student " + A.getFirstName() + " " + A.getLastName() + " is added to the student list");
            return true;
        }
        return false;
    }


/** getter method of student reconrd iterator */
    public Iterator<Student> getStudentIterator() {
        return studentIterator;
    }

}
