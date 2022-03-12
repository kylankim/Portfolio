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

    Alphabet A_Y =  new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXY");
    Permutation E1 = new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)",
            new Alphabet());
    Permutation E2 = new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (AQ)",
            new Alphabet());
    Permutation E3 = new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV)" , A_Y);
    Permutation E4 = new Permutation("", new Alphabet());
    Permutation E5 = new Permutation("(ABCDEFGHI)", new Alphabet("ABCDEFGHI"));
    Permutation E6 = new Permutation("(AB) (CDEFHI)", new Alphabet("ABCDEFGHI"));
    Permutation E7 = new Permutation("",new Alphabet(""));

    String Alp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String ES1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    String ES2 = "QJDKSIRUXBLHWTMCAGZNPYFVOE";

    @Test
    public void testConstructor() {
        System.out.println("# of Cycles: " + E2.get_cycleList().size());
        System.out.println();
        for (String tmp : E2.get_cycleList()) {
            System.out.println("Permutation : " + tmp + " | Size : " + tmp.length());
        }
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
        assertEquals(E1.alphabet().size(),E1.size());
    }

    @Test
    public void testpermute() {
        for (int i = 0; i < E1.size(); i++) {
            assertEquals(E1.alphabet().toInt(ES1.charAt(i)), E1.permute(i));
            assertEquals(E1.alphabet().toInt(ES2.charAt(i)), E2.permute(i));
        }
        assertEquals('E', E1.permute('A'));
        assertEquals('A', E1.permute('U'));
        assertEquals('J', E1.permute('Z'));
        assertEquals('S', E1.permute('S'));
        assertEquals('C', E3.permute('Y'));
        assertEquals('J', E3.permute('J'));
        assertEquals('D', E3.permute('G'));
        assertEquals('I', E3.permute('V'));
        assertEquals(4, E4.permute(4));
        assertEquals('A', E5.permute('I'));
        assertEquals(6, E5.permute(5));
        assertEquals('A', E6.permute('B')); // Transfer to LAb6
    }

    @Test
    public void testinvert() {
        for (int i = 0; i < E1.size(); i++) {
            assertEquals(E1.alphabet().toInt(Alp.charAt(i)), E1.alphabet().toInt(E1.invert(ES1.charAt(i))));
            assertEquals(E2.alphabet().toInt(Alp.charAt(i)), E2.alphabet().toInt(E2.invert(ES2.charAt(i))));
        }
        assertEquals('A', E1.invert('E'));
        assertEquals('U', E1.invert('A'));
        assertEquals('Z', E1.invert('J'));
        assertEquals('S', E1.invert('S'));
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
    public void testderangement() {
        assertFalse(E1.derangement());
        assertTrue(E2.derangement());
        assertFalse(E3.derangement());
        assertTrue(E5.derangement());
        assertFalse(E6.derangement());
        assertTrue(E7.derangement());
    }
	
}
