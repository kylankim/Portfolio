/** Bit-twiddling class.
 *  @author Kidong Kim
 */
public class LastBit {

    /** Returns the number N!=0 with all but its last (least significant)
     *  1-bit set to 0.
     *
     *  For example
     *      lastBit(3) == lastBit(7) == lastBit(1145) == 1,
     *  and
     *      lastBit(4) == lastBit(12) == lastBit(2052) == 4.
     */
    public static int lastBit(int n) {
        return n & -n; // REPLACE *ONLY* 0 WITH SOLUTION.
                  //DO NOT ADD EXTRA STATEMENTS.
    }

}
