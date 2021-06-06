package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Kidong Kim
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] result = new int[A.length + B.length];
        System.arraycopy(B,0,result,0, B.length);
        System.arraycopy(A,0,result,B.length,A.length);
        return result;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] result = new int[A.length-len];
        int index;
        for (index = 0; index < A.length; index++) {
            if (A[index] == start) {
                break;
            }
        }
        System.arraycopy(A,0,result,0,index);
        System.arraycopy(A,index+len,result,index,A.length-index-len);
        return result;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }
        int[] indexes = new int[A.length+1];
        int count = indexes[0] = 0;
        for (int index = 1; index < A.length; index++) {
            if (A[index - 1] >= A[index]) {
                count++;
                indexes[count] = index;
            }
        }
        count++;
        indexes[count] = A.length;

        int[][] result = new int[count][];
        for (int i = 1; i < count+1; i++) {
            int len = (indexes[i] - indexes[i-1]);
            result[i-1] = new int[len];
            System.arraycopy(A,indexes[i-1],result[i-1],0,len);
        }
        return result;
    }
}
