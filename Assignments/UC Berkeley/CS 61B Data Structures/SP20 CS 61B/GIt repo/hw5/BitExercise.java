/** A collection of bit twiddling exercises.
 *  @author Kidong Kim
 */

public class BitExercise {
    
    /** Fill in the function below so that it returns 
    * the value of the argument x with all but its last 
    * (least significant) 1-bit set to 0.
    * For example, 100 in binary is 0b1100100, so lastBit(100)
    * should return 4, which in binary is 0b100.
    */
    public static int lastBit(int x) {
        return x & (-x);
    }

    /** Fill in the function below so that it returns 
    * True iff x is a power of two, otherwise False.
    * For example: 2, 32, and 8192 are powers of two.
     * Make only the last bit remains and all others to zero.
    */
    public static boolean powerOfTwo(int x) {
        return (x & (x-1)) == 0;
    }
    
    /** Fill in the function below so that it returns 
    * the absolute value of x WITHOUT USING ANY IF 
    * STATEMENTS OR CALLS TO MATH.
    * For example, absolute(1) should return 1 and 
    * absolute(-1) should return 1.
    */
    public static int absolute(int x) {
        // copoy and paste it the mask
        int mask = ((x >> 31) ^ x); // mask == x
        return mask - (x >> 31);
    }
}