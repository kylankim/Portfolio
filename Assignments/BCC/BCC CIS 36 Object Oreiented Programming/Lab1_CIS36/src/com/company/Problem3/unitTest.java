package com.company.Problem3;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class unitTest {

    /** check Whether constructor and getter method works properly and prevent duplicate courses to be added in the schedule
     * Math1A is added to the course list
     * Econ100A is added to the course listMath1A
     * cannot be added to the course list (duplicate)
     */
    @Test
    public void constructortest() {
        CourseList dylancourse = new CourseList(new ArrayList<>());
        dylancourse.addCourse("Math1A");
        dylancourse.addCourse("Econ100A");

        Student A = new Student("Kidong", "Kim", 1234, "hyo05065@gmail.com", null);
        Student B = new Student("Jibin", "Jun", 6789,"hyo05065@berkeley.edu",null);
        Student C = new Student("Dylan", "MC", 2749, "dylan@peralta.edu", dylancourse);

        assertEquals(null, A.getCourseList());
        assertEquals("Kidong", A.getFirstName());
        assertEquals("Kim", A.getLastName());
        assertEquals(1234, A.getID());
        assertEquals("hyo05065@gmail.com", A.getEmail());

        assertEquals(null, B.getCourseList());
        assertEquals("Jibin", B.getFirstName());
        assertEquals("Jun", B.getLastName());
        assertEquals(6789, B.getID());
        assertEquals("hyo05065@berkeley.edu", B.getEmail());

        assertEquals(dylancourse, C.getCourseList());
        assertEquals("Dylan", C.getFirstName());
        assertEquals("MC", C.getLastName());
        assertEquals(2749, C.getID());
        assertEquals("dylan@peralta.edu", C.getEmail());

        assertEquals(true, C.getCourseList().searchByCourseName("Math1A"));
        assertEquals(true, C.getCourseList().searchByCourseName("Econ100A"));
        assertEquals(false, C.getCourseList().addCourse("Math1A"));
    }

    /** check whether course constructor works properly and check whether search method workss
     *  */
    @Test
    public void courseConstructorTest(){
        CourseList dylancourse = new CourseList(new ArrayList<>());
        dylancourse.addCourse("Math1A");
        Course math1B = new Course("Math1B");
        dylancourse.addCourse(math1B);
        dylancourse.addCourse("Econ100A");
        assertEquals( true , dylancourse.searchByCourseName("Math1A"));
        assertEquals( true , dylancourse.searchByCourseName("Math1B"));
        assertEquals( true , dylancourse.searchByCourseName("Econ100A"));
    }

    /** check whether courseList is working properly and each method of the class is working
     * */

    @Test
    public void couseTest() {
        CourseList dylancourse = new CourseList(new ArrayList<>());
        dylancourse.addCourse("Math1A");
        dylancourse.addCourse("Econ100A");
        dylancourse.addCourse("CIS36");
        dylancourse.addCourse("CIS40");

        Student dylan = new Student("Dylan", "MC", 2749, "dylan@peralta.edu", dylancourse);

        assertEquals(true, dylan.hasNext());
        assertEquals(0, dylancourse.getIndex());
        assertEquals(4, dylancourse.getArrayCourseList().size());
        assertEquals("Math1A", dylan.next().getName());
        assertEquals("Econ100A", dylan.next().getName());
        assertEquals(2, dylancourse.getIndex());
        dylan.next();
        dylan.next();
        assertEquals(4, dylancourse.getIndex());
        assertEquals(false, dylan.hasNext());

    }

    /**
     * Econ152 is added to the course list
     * Econ121 is added to the course list
     * Korean155 is added to the course list
     * EEP143 is added to the course list
     *
     *
     *
     * Name : Jibin Jun
     * Student ID : 2981373
     * Student Email : Jibinjun@berkeley.edu
     * Courses : Econ152, Econ121, Korean155, EEP143
     *
     * ==================================================
     * */
    @Test
    public void transcriptPrinterTest() {
        CourseList jibincourse = new CourseList(new ArrayList<>());
        jibincourse.addCourse("Econ152");
        jibincourse.addCourse("Econ121");
        jibincourse.addCourse("Korean155");
        jibincourse.addCourse("EEP143");
        System.out.println("\n\n");
        Student Jibin = new Student("Jibin", "Jun", 2981373, "Jibinjun@berkeley.edu", jibincourse);
        TranscriptPrinter A = new TranscriptPrinter();
        A.printTranscript(Jibin);
    }

    /** check whether student iterator works*/
    @Test
    public void studentIteratorTest() {
        CourseList dylancourse = new CourseList(new ArrayList<>());
        dylancourse.addCourse("Math1A");
        dylancourse.addCourse("Econ100A");

        Student Kidong = new Student("Kidong", "Kim", 1234, "hyo05065@gmail.com", new CourseList(new ArrayList<>()));
        Student Jibin = new Student("Jibin", "Jun", 6789,"hyo05065@berkeley.edu",new CourseList(new ArrayList<>()));
        Student Dylan = new Student("Dylan", "MC", 2749, "dylan@peralta.edu", dylancourse);

        StudentrecordManager A = new StudentrecordManager();
        A.addStudent(Kidong);
        A.addStudent(Dylan);
        A.addStudent(Jibin);

        Iterator<Student> B = A.getStudentIterator();
        assertEquals(true, B.hasNext());
        assertEquals("Kidong", B.next().getFirstName());
        assertEquals(2749, B.next().getID());
        B.next();
        assertEquals(false, B.hasNext());
    }


    /** Math1A is added to the course list
     Econ100A is added to the course list
     student Kidong Kim is added to the student list
     student Dylan MC is added to the student list
     student Jibin Jun is added to the student list
     Name : Kidong Kim
     Student ID : 1234
     Student Email : hyo05065@gmail.com
     Courses : X


     ==================================================
     Name : Dylan MC
     Student ID : 2749
     Student Email : dylan@peralta.edu
     Courses : Math1A, Econ100A

     ==================================================
     Name : Jibin Jun
     Student ID : 6789
     Student Email : hyo05065@berkeley.edu
     Courses : X


     ==================================================

     Process finished with exit code 0 */
     @Test
    public void studentTranscriptPrinterByIterator(){
        CourseList dylancourse = new CourseList(new ArrayList<>());
        dylancourse.addCourse("Math1A");
        dylancourse.addCourse("Econ100A");

        Student Kidong = new Student("Kidong", "Kim", 1234, "hyo05065@gmail.com", new CourseList(new ArrayList<>()));
        Student Jibin = new Student("Jibin", "Jun", 6789,"hyo05065@berkeley.edu",new CourseList(new ArrayList<>()));
        Student Dylan = new Student("Dylan", "MC", 2749, "dylan@peralta.edu", dylancourse);

        StudentrecordManager A = new StudentrecordManager();
        A.addStudent(Kidong);
        A.addStudent(Dylan);
        A.addStudent(Jibin);

        Iterator<Student> B = A.getStudentIterator();

        TranscriptPrinter C = new TranscriptPrinter();
        C.printTranscript(B);
    }

}
