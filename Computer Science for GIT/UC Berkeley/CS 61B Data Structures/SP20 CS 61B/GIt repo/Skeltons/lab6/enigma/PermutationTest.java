package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author Kidong Kim
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));

        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
        checkPerm("1",UPPER_STRING, "EKMFLGDQVZNTOWYHXUSPAIBRCJ", E1, alpha);
        checkPerm("2",UPPER_STRING, "QJDKSIRUXBLHWTMCAGZNPYFVOE", E2, alpha);
        checkPerm("3",UPPER_STRING,"BDFHJLCPRTXVZNYEIWGAKMUSQO", getNewPermutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", alpha), alpha);
        checkPerm("4", UPPER_STRING, "FSOKANUERHMBTIYCWLQPZXVGJD", getNewPermutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)", alpha), alpha);
        checkPerm("5", UPPER_STRING, "LEYJVCNIXWPBQMDRTAKZGFUHOS", getNewPermutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", alpha), alpha);
        checkPerm("6", "A", "A", getNewPermutation("", getNewAlphabet("A")), getNewAlphabet("A"));
        checkPerm("7", "AB", "BA", getNewPermutation("(AB)", getNewAlphabet("AB")), getNewAlphabet("AB"));
        checkPerm("8", "ABCDEFGHI", "BADEFHGIC", getNewPermutation("(AB) (CDEFHI) ", getNewAlphabet("ABCDEFGHI")), getNewAlphabet("ABCDEFGHI"));
        checkPerm("9", UPPER_STRING, "MJPZTXVIHBNOAKLCWYUESGQFRD", getNewPermutation(" (ZD) (AM) (BJ) (CP) (ET) (FX) (GV) (HI) (KN) (LO) (QW) (RY) (SU) ", getNewAlphabet()), getNewAlphabet());
    }

    Alphabet A_Y =  getNewAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXY");
    String Alp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String ES1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    String ES2 = "QJDKSIRUXBLHWTMCAGZNPYFVOE";
    Permutation E1 = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", getNewAlphabet());
    Permutation E2 = getNewPermutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (AQ)",getNewAlphabet());
    Permutation E3 = getNewPermutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV)" , A_Y);
    Permutation E4 = getNewPermutation("", getNewAlphabet());
    Permutation E5 = getNewPermutation("(ABCDEFGHI)", getNewAlphabet("ABCDEFGHI"));
    Permutation E6 = getNewPermutation("(AB) (CDEFHI)", getNewAlphabet("ABCDEFGHI"));
    Permutation E7 = getNewPermutation("",getNewAlphabet(""));




    @Test
    public void testpermute() {

        for (int i = 0; i < E1.size(); i++) {
            assertEquals(E1.alphabet().toInt(ES1.charAt(i)), E1.permute(i));
            assertEquals(E1.alphabet().toInt(ES2.charAt(i)), E2.permute(i));
        }

        assertEquals('C', E3.permute('Y'));
        assertEquals('J', E3.permute('J'));
        assertEquals('D', E3.permute('G'));
        assertEquals('I', E3.permute('V'));
        assertEquals(4, E4.permute(4));
        assertEquals('A', E5.permute('I'));
        assertEquals(6, E5.permute(5));
        assertEquals('A', E6.permute('B'));
    }

    @Test
    public void testinvert() {

        for (int i = 0; i < E1.size(); i++) {
            assertEquals(E1.alphabet().toInt(Alp.charAt(i)), E1.alphabet().toInt(E1.invert(ES1.charAt(i))));
            assertEquals(E2.alphabet().toInt(Alp.charAt(i)), E2.alphabet().toInt(E2.invert(ES2.charAt(i))));
        }

        assertEquals('Y', E3.invert('C'));
        assertEquals('J', E3.invert('J'));
        assertEquals('G', E3.invert('D'));
        assertEquals('V', E3.invert('I'));
        assertEquals(4, E4.invert(4));
        assertEquals('I', E5.invert('A'));
        assertEquals(5, E5.invert(6));
        assertEquals('B', E6.invert('A'));
    }

    @Test
    public void testsize() {
        assertEquals(26,E1.size());
        assertEquals(26,E2.size());
        assertEquals(25,E3.size());
        assertEquals(26,E4.size());
        assertEquals(9,E5.size());
        assertEquals(9,E6.size());
        assertEquals(0,E7.size());
    }

    @Test
    public void testderangement() {
        assertFalse(E1.derangement());
        assertTrue(E2.derangement());
        assertFalse(E3.derangement());
        assertTrue(E5.derangement());
        assertFalse(E6.derangement());
        assertTrue(E7.derangement());
    }



    // FIXME: Add tests here that pass on a correct Permutation and fail on buggy Permutations.
}
