package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Kidong Kim
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    Alphabet aY =  new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXY");
    Permutation e1 = new Permutation("(AELTPHQXRU) "
            + "(BKNW) (CMOY) (DFG) (IV) (JZ) (S)", new Alphabet());
    Permutation e2 = new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) "
            + "(BJ) (GR) (NT) (AQ)", new Alphabet());
    Permutation e3 = new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV)",
            aY);
    Permutation e4 = new Permutation("", new Alphabet());
    Permutation e5 = new Permutation("(ABCDEFGHI)", new Alphabet("ABCDEFGHI"));
    Permutation e6 = new Permutation("(AB) (CDEFHI)",
            new Alphabet("ABCDEFGHI"));
    Permutation e7 = new Permutation("", new Alphabet(""));

    String alp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String es1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    String es2 = "QJDKSIRUXBLHWTMCAGZNPYFVOE";

    @Test
    public void testConstructor() {
        System.out.println("# of Cycles: " + e2.getCycleList().size());
        System.out.println();
        for (String tmp : e2.getCycleList()) {
            System.out.println("Permutation : "
                    + tmp + " | Size : " + tmp.length());
        }
    }

    @Test
    public void testsize() {
        assertEquals(26, e1.size());
        assertEquals(26, e2.size());
        assertEquals(25, e3.size());
        assertEquals(26, e4.size());
        assertEquals(9, e5.size());
        assertEquals(9, e6.size());
        assertEquals(0, e7.size());
        assertEquals(e1.alphabet().size(), e1.size());
    }

    @Test
    public void testpermute() {
        for (int i = 0; i < e1.size(); i++) {
            assertEquals(e1.alphabet().toInt(es1.charAt(i)), e1.permute(i));
            assertEquals(e1.alphabet().toInt(es2.charAt(i)), e2.permute(i));
        }
        assertEquals('E', e1.permute('A'));
        assertEquals('A', e1.permute('U'));
        assertEquals('J', e1.permute('Z'));
        assertEquals('S', e1.permute('S'));
        assertEquals('C', e3.permute('Y'));
        assertEquals('J', e3.permute('J'));
        assertEquals('D', e3.permute('G'));
        assertEquals('I', e3.permute('V'));
        assertEquals(4, e4.permute(4));
        assertEquals('A', e5.permute('I'));
        assertEquals(6, e5.permute(5));
        assertEquals('A', e6.permute('B'));
    }

    @Test
    public void testinvert() {
        for (int i = 0; i < e1.size(); i++) {
            assertEquals(e1.alphabet().toInt(alp.charAt(i)),
                    e1.alphabet().toInt(e1.invert(es1.charAt(i))));
            assertEquals(e2.alphabet().toInt(alp.charAt(i)),
                    e2.alphabet().toInt(e2.invert(es2.charAt(i))));
        }
        assertEquals('A', e1.invert('E'));
        assertEquals('U', e1.invert('A'));
        assertEquals('Z', e1.invert('J'));
        assertEquals('S', e1.invert('S'));
        assertEquals('Y', e3.invert('C'));
        assertEquals('J', e3.invert('J'));
        assertEquals('G', e3.invert('D'));
        assertEquals('V', e3.invert('I'));
        assertEquals(4, e4.invert(4));
        assertEquals('I', e5.invert('A'));
        assertEquals(5, e5.invert(6));
        assertEquals('B', e6.invert('A'));
    }

    @Test
    public void testderangement() {
        assertFalse(e1.derangement());
        assertTrue(e2.derangement());
        assertFalse(e3.derangement());
        assertTrue(e5.derangement());
        assertFalse(e6.derangement());
        assertTrue(e7.derangement());
    }
}
