package enigma;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit Test of Alphabet Class.
 *  @author Kidong Kim
 */
public class AlphabetTest extends TestCase {

    Alphabet e1 = new Alphabet();
    Alphabet e2 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXY");
    Alphabet e3 = new Alphabet("");
    Alphabet e4 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ");


    @Test
    public void testsize() {
        assertEquals(26, e1.size());
        assertEquals(25, e2.size());
        assertEquals(0, e3.size());
        assertEquals(26, e4.size());
    }

    @Test
    public void testcontains() {
        assertTrue(e1.contains('A'));
        assertTrue(e1.contains('Z'));
        assertFalse(e1.contains(' '));
        assertTrue(e2.contains('F'));
        assertFalse(e2.contains('Z'));
    }

    @Test
    public void testtoChar() {
        assertEquals('A', e1.toChar(0));
        assertEquals('Z', e1.toChar(25));
        assertEquals('Y', e2.toChar(e2.size()-1));
    }

    @Test
    public void testtoInt() {
        assertEquals(0, e1.toInt('A'));
        assertEquals(1, e2.toInt('B'));
        assertEquals(25, e1.toInt('Z'));
    }
}