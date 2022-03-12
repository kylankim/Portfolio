package enigma;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class RotorTest extends TestCase {
    Permutation P1 = new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", new Alphabet());

    Rotor E1 = new Rotor("E1", P1);
    MovingRotor EM1 = new MovingRotor("EM1", P1, "Q");
    FixedRotor EF1 = new FixedRotor("EF1", P1);
    Reflector ER1 = new Reflector("ER1", P1);


    Permutation P2 = new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (AQ)", new Alphabet());

    Rotor E2 = new Rotor("E2", P2);
    MovingRotor EM2 = new MovingRotor("EM2", P2, "E");
    FixedRotor EF2 = new FixedRotor("EF2", P2);
    Reflector ER2 = new Reflector("ER2", P2);

    @Test
    public void testrotates() {
        assertFalse(E1.rotates());
        assertTrue(EM1.rotates());
        assertFalse(EF1.rotates());
        assertFalse(ER1.rotates());

        assertFalse(E2.rotates());
        assertTrue(EM2.rotates());
        assertFalse(EF2.rotates());
        assertFalse(ER2.rotates());
    }

    @Test
    public void testreflecting() {
        assertFalse(E1.reflecting());
        assertFalse(EM1.reflecting());
        assertFalse(EF1.reflecting());
        assertTrue(ER1.reflecting());

        assertFalse(E2.reflecting());
        assertFalse(EM2.reflecting());
        assertFalse(EF2.reflecting());
        assertTrue(ER2.reflecting());
    }

    @Test
    public void testsetting() {
        assertEquals(0, E1.setting());
        assertEquals(0, EM1.setting());
        assertEquals(0, EF1.setting());
        assertEquals(0, ER1.setting());
        E1.advance();
        EM1.advance();
        EF1.advance();
        ER1.advance();
        assertEquals(0, E1.setting());
        assertEquals(1, EM1.setting());
        assertEquals(0, EF1.setting());
        assertEquals(0, ER1.setting());
        EM1.set(0);
        assertEquals(0, EM1.setting());

        assertEquals(0, E2.setting());
        assertEquals(0, EM2.setting());
        assertEquals(0, EF2.setting());
        assertEquals(0, ER2.setting());
        E2.advance();
        EM2.advance();
        EF2.advance();
        ER2.advance();
        assertEquals(0, E2.setting());
        assertEquals(1, EM2.setting());
        assertEquals(0, EF2.setting());
        assertEquals(0, ER2.setting());
        EM2.set(0);
        assertEquals(0, EM2.setting());
    }

    @Test
    public void testset() {
        E1.set(1);
        EM1.set(1);
        EF1.set(1);
        assertEquals(1, E1.setting());
        assertEquals(1, EM1.setting());
        assertEquals(1, EF1.setting());
        E1.set(0);
        EM1.set(0);
        EF1.set(0);
        assertEquals(0, E1.setting());
        assertEquals(0, EM1.setting());
        assertEquals(0, EF1.setting());

        E2.set(1);
        EM2.set(1);
        EF2.set(1);
        assertEquals(1, E2.setting());
        assertEquals(1, EM2.setting());
        assertEquals(1, EF2.setting());
        E2.set(0);
        EM2.set(0);
        EF2.set(0);
        assertEquals(0, E2.setting());
        assertEquals(0, EM2.setting());
        assertEquals(0, EF2.setting());
    }

    @Test
    public void testatNotch() {
        assertFalse(EM1.atNotch());
        EM1.set('Q');
        assertTrue(EM1.atNotch());
        EM1.set(0);

        assertFalse(EM2.atNotch());
        EM2.set('E');
        assertTrue(EM2.atNotch());
        EM2.set(0);
    }
}