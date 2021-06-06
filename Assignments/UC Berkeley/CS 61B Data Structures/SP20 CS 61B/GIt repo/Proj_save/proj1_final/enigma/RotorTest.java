package enigma;

import static org.junit.Assert.*;

import org.junit.Test;

public class RotorTest {
    Permutation p1 = new Permutation("(AELTPHQXRU) (BKNW) (CMOY)"
            + " (DFG) (IV) (JZ) (S)", new Alphabet());

    Rotor e1 = new Rotor("e1", p1);
    MovingRotor em1 = new MovingRotor("em1", p1, "Q");
    FixedRotor ef1 = new FixedRotor("ef1", p1);
    Reflector er1 = new Reflector("er1", p1);


    Permutation p2 = new Permutation("(FIXVYOMW) (CDKLHUP) "
            + "(ESZ) (BJ) (GR) (NT) (AQ)", new Alphabet());

    Rotor e2 = new Rotor("e2", p2);
    MovingRotor em2 = new MovingRotor("em2", p2, "E");
    FixedRotor ef2 = new FixedRotor("ef2", p2);
    Reflector er2 = new Reflector("er2", p2);

    @Test
    public void testrotates() {
        assertFalse(e1.rotates());
        assertTrue(em1.rotates());
        assertFalse(ef1.rotates());
        assertFalse(er1.rotates());

        assertFalse(e2.rotates());
        assertTrue(em2.rotates());
        assertFalse(ef2.rotates());
        assertFalse(er2.rotates());
    }

    @Test
    public void testreflecting() {
        assertFalse(e1.reflecting());
        assertFalse(em1.reflecting());
        assertFalse(ef1.reflecting());
        assertTrue(er1.reflecting());

        assertFalse(e2.reflecting());
        assertFalse(em2.reflecting());
        assertFalse(ef2.reflecting());
        assertTrue(er2.reflecting());
    }

    @Test
    public void testsetting() {
        assertEquals(0, e1.setting());
        assertEquals(0, em1.setting());
        assertEquals(0, ef1.setting());
        assertEquals(0, er1.setting());
        e1.advance();
        em1.advance();
        ef1.advance();
        er1.advance();
        assertEquals(0, e1.setting());
        assertEquals(1, em1.setting());
        assertEquals(0, ef1.setting());
        assertEquals(0, er1.setting());
        em1.set(0);
        assertEquals(0, em1.setting());

        assertEquals(0, e2.setting());
        assertEquals(0, em2.setting());
        assertEquals(0, ef2.setting());
        assertEquals(0, er2.setting());
        e2.advance();
        em2.advance();
        ef2.advance();
        er2.advance();
        assertEquals(0, e2.setting());
        assertEquals(1, em2.setting());
        assertEquals(0, ef2.setting());
        assertEquals(0, er2.setting());
        em2.set(0);
        assertEquals(0, em2.setting());
    }

    @Test
    public void testset() {
        e1.set(1);
        em1.set(1);
        ef1.set(1);
        assertEquals(1, e1.setting());
        assertEquals(1, em1.setting());
        assertEquals(1, ef1.setting());
        e1.set(0);
        em1.set(0);
        ef1.set(0);
        assertEquals(0, e1.setting());
        assertEquals(0, em1.setting());
        assertEquals(0, ef1.setting());

        e2.set(1);
        em2.set(1);
        ef2.set(1);
        assertEquals(1, e2.setting());
        assertEquals(1, em2.setting());
        assertEquals(1, ef2.setting());
        e2.set(0);
        em2.set(0);
        ef2.set(0);
        assertEquals(0, e2.setting());
        assertEquals(0, em2.setting());
        assertEquals(0, ef2.setting());
    }

    @Test
    public void testatNotch() {
        assertFalse(em1.atNotch());
        em1.set('Q');
        assertTrue(em1.atNotch());
        em1.set(0);

        assertFalse(em2.atNotch());
        em2.set('E');
        assertTrue(em2.atNotch());
        em2.set(0);
    }
}
