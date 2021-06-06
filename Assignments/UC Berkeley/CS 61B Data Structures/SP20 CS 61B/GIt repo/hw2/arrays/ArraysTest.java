package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Kidong Kim
 */

public class ArraysTest {
    /** FIXME
     */

    @Test
    public void catenateTest() {
        int[] sample1 = new int[] {0, 1, 2};
        int[] sample2 = new int[] {1, 5, 6, 2};
        int[] sample3 = new int[] {9, 2, 2, 2};
        int[] sample4 = new int[] {0};
        int[] sample5 = new int[] {0, 0};

        assertArrayEquals(Arrays.catenate(sample4,sample4), sample5);
        assertArrayEquals(Arrays.catenate(sample1,sample2), new int[] { 1, 5, 6, 2, 0, 1, 2});
        assertArrayEquals(Arrays.catenate(sample3, sample5), new int[] {0, 0, 9, 2, 2, 2});
    }

    @Test
    public void removeTest() {
        int[] sample1 = new int[] {0, 1, 2};
        int[] sample2 = new int[] {1, 5, 6, 2};
        int[] sample3 = new int[] {9, 2, 2, 2};
        int[] sample4 = new int[] {0};
        int[] sample6 = Arrays.catenate(sample1, sample2);
        int[] sample7 = Arrays.catenate(sample3, sample1);
        int[] sol1 = new int[] {1, 5, 6, 2, 0, 1, 2};
        int[] sol2 = new int[] {1, 5, 0, 1, 2};

        assertArrayEquals(Arrays.catenate(sample1, sample2), Arrays.remove(sample6,0,0));
        assertArrayEquals(sol2, Arrays.remove(sample6,6,2));
        assertArrayEquals(new int[]{}, Arrays.remove(sample4,0,1));
        assertArrayEquals(Arrays.remove(sample4,0,1), Arrays.remove(sample7, sample7[0], sample7.length));
        assertArrayEquals(sol1, Arrays.remove(sample6,0,0));
    }

    @Test
    public void naturalRunsTest() {
        int[][] A =  Arrays.naturalRuns(new int[] {1, 3, 7, 5, 4, 6, 9, 10});
        int[] sample1 = new int[] {1, 3, 7, 5, 4, 6, 9, 10, 10, 11};
        int[][] sol1 = new int[][] {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        int[] sample2 = new int[] {1, 6, 4, 2, 5, 7, 4, 2, 1, 6, 2, 1};
        int[][] sol2 = new int[][] {{1, 6}, {4}, {2, 5, 7}, {4}, {2}, {1,6}, {2}, {1}};
        int[] sample3 = new int[] {0, 0, 0, 0};
        int[][] sol3 = new int[][] {{0}, {0}, {0}, {0}};
        int[] empty = new int[]{};

        assertArrayEquals(A, new int[][] {{1, 3, 7}, {5}, {4, 6, 9, 10}});
        assertArrayEquals(Arrays.naturalRuns(sample1), sol1);
        assertArrayEquals(Arrays.naturalRuns(sample2), sol2);
        assertArrayEquals(Arrays.naturalRuns(sample3), sol3);
        assertNull(Arrays.naturalRuns(empty));

    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
