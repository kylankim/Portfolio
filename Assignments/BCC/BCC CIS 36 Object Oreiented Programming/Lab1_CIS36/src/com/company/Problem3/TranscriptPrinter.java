package com.company.Problem3;

import java.util.ArrayList;
import java.util.Iterator;

public class TranscriptPrinter {

    /** printing each field of the Student data */
    public void printTranscript(Student x) {
        ArrayList<Course> courselist = x.getCourseList().getArrayCourseList();
        System.out.println("Name : " + x.getFirstName() + " " + x.getLastName());
        System.out.println("Student ID : " + x.getID());
        System.out.println("Student Email : " + x.getEmail());
        if(x.getCourseList().getArrayCourseList().size() > 0) {
            System.out.print("Courses : " + courselist.get(0).getName());
        } else {
            System.out.println("Courses : X");
        }
        for (int i = 1; i < courselist.size(); i++) {
            System.out.print(", " + courselist.get(i).getName());
        }
        System.out.println("\n");
        System.out.println("==================================================");
    }


    /** transcript printer that takes in iterator as a parameter*/
    public void printTranscript(Iterator<Student> x){
        while(x.hasNext()) {
            printTranscript(x.next());
        }
    }
}
