import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {


    @Test
    public void testMaxValue() {
        int[][] a = {{1,2,3},{4,5,6},{7,8,9}};
        assertEquals(9, MultiArr.maxValue(a));
        int[][] b = {{7,12,4},{1,5,2}};
        assertEquals(12, MultiArr.maxValue(b));
        int[][] c = {{1},{2,3},{4,7}};
        assertEquals(7,MultiArr.maxValue(c));
        //TODO: Your code hire!
    }

    @Test
    public void testAllRowSums() {
        int[][] a = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] b = {{7,12,4},{1,5,2}};
        int[][] c = {{1},{2,3},{4,7}};
        int[] result1 = {6,15,24};
        int[] result2 = {23, 8};
        int[] result3 = {1,5,11};
        assertTrue(compareArray(MultiArr.allRowSums(a),result1));
        assertTrue(compareArray(MultiArr.allRowSums(b),result2));
        assertTrue(compareArray(MultiArr.allRowSums(c),result3));
        //TODO: Your code here!
    }

    public boolean compareArray(int[] a, int[] b){
        if (a.length == b.length) {
            int i = 0;
            while(a.length > i) {
                if (a[i] != b[i]) {
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
