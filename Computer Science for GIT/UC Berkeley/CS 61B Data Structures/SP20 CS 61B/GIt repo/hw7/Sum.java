import java.util.Arrays;

/** HW #7, Two-sum problem.
 * @author Kidong Kim
 */
public class Sum {
    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        A = Arrays.copyOf(A, A.length);
        Arrays.sort(A);
        for (int tmp = 0; tmp < B.length; tmp++) {
            int tmp2 = Arrays.binarySearch(A, m - B[tmp]);
            if (tmp2 >= 0 && tmp2 < A.length && A[tmp2]+B[tmp]==m) {
                return true;
            }
        }
        return false;
    }
}
