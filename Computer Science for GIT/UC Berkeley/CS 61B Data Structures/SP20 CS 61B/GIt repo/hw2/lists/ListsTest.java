package lists;


import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author Kidong Kim
 */

public class ListsTest {
    /** FIXME
     */

    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    @Test
    public void listTest() {
        int[] sample1 = new int[] {1, 3, 7, 5, 4, 6, 9, 10, 10, 11};
        int[][] sol1 = new int[][] {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        int[] sample2 = new int[] {1, 6, 4, 2, 5, 7, 4, 2, 1, 6, 2, 1};
        int[][] sol2 = new int[][] {{1, 6}, {4}, {2, 5, 7}, {4}, {2}, {1,6}, {2}, {1}};
        int[] sample3 = new int[] {0, 0, 0, 0};
        int[][] sol3 = new int[][] {{0}, {0}, {0}, {0}};
        int[] empty = new int[]{};

        IntListList A = IntListList.list(sol1);
        IntListList B = IntListList.list(sol2);
        IntListList C = IntListList.list(sol3);
        assertTrue(A.equals(Lists.naturalRuns(IntList.list(sample1))));
        assertTrue(B.equals(Lists.naturalRuns(IntList.list(sample2))));
        assertTrue(C.equals(Lists.naturalRuns(IntList.list(sample3))));
        assertNull(Lists.naturalRuns(IntList.list(empty)));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
