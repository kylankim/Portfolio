import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        IntList A = IntList.list(3, 2, 1);
        IntList B = IntList.list(1, 2, 3);
        assertEquals(IntList.dcatenate(A,B), IntList.list(1,2,3,3,2,1));

        IntList C = IntList.list();
        IntList D = IntList.list(1, 2, 5);
        assertEquals(IntList.dcatenate(C,D), IntList.list(1,2,5));

        IntList E = IntList.list();
        IntList F = IntList.list();
        assertEquals(IntList.dcatenate(E,F), IntList.list());

    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {
        IntList G = IntList.list(1,2,3,4,5,6,7,9);
        assertEquals(IntList.subTail(G, 2), IntList.list(3,4,5,6,7,9));
        assertEquals(IntList.subTail(G, 1), IntList.list(2,3,4,5,6,7,9));
        assertEquals(IntList.subTail(G, 5), IntList.list(6,7,9));
        assertEquals(IntList.subTail(G, 0), IntList.list(1,2,3,4,5,6,7,9));
    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {
        IntList J = IntList.list(1,2,3,4,5,6,7,9);
        assertEquals(IntList.sublist(J, 2, 4), IntList.list(3,4,5,6));
        assertEquals(IntList.sublist(J, 2, 0), IntList.list());
        assertEquals(IntList.sublist(J, 2, 5), IntList.list(3,4,5,6,7));

    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() {
        IntList G = IntList.list(1,2,3,4,5,6,7,9);
        assertEquals(IntList.dsublist(G, 2, 4), IntList.list(3,4,5,6));

        IntList H = IntList.list(1,2,3,4);
        assertEquals(IntList.dsublist(H, 2, 0), IntList.list());

        IntList I = IntList.list(1,2,3,4,5,6,7,9);
        assertEquals(IntList.dsublist(I, 2, 5), IntList.list(3,4,5,6,7));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
