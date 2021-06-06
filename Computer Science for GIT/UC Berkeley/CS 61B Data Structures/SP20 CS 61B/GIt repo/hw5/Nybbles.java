/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Kidong Kim
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N.
     * low bound of (N + 7) / 8 becomes the length of the array.
     * + 7 on N for minimum length of nybble array
     * Unless 0 for the value of N, can create int[] of at least length 1
    * DON'T CHANGE THIS.*/
    public Nybbles(int N) {
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     * Kth integer --> integer at 4*k to 4*(k+1)-1
     * left shift until onl 4 bits of kth integer left and right shift for 28
     * if positive, >> will add 0s but negative, >> will add 1s
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int target = 28 - (4 * (k % 8));
            return _data[k / 8] << target >> 28;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8.
     *  Set the kth integer of the array to 0s
     *  Add the value to the kth integer*/
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            int value = val << (4 * (k % 8)); // 0000 0000 0000 0000 0000 0000 0000 xxxx
            _data[k / 8] &= ~(15 << 4 * (k % 8));
            _data[k /8] |= value;
        }
    }

    /** DON'T CHANGE OR ADD TO THESE.*/
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
