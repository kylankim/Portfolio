import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        CompoundInterest A = new CompoundInterest();
        assertEquals(1,A.numYears(2021));
        assertEquals(2,CompoundInterest.numYears(2022));
        assertEquals(10, CompoundInterest.numYears(2030));
        assertEquals(0,CompoundInterest.numYears(2020));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(100, CompoundInterest.futureValue(100,12, 2020),tolerance);
        assertEquals(112, CompoundInterest.futureValue(100,12, 2021), tolerance);
        assertEquals(125.44, CompoundInterest.futureValue(100,12, 2021+1), tolerance);
        assertEquals(140.4928, CompoundInterest.futureValue(100,12, 2022+1), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(100, CompoundInterest.futureValueReal(100,12, 2019+1,3),tolerance);
        assertEquals(108.64, CompoundInterest.futureValueReal(100,12, 2020+1,3),tolerance);
        assertEquals(118.026496, CompoundInterest.futureValueReal(100,12, 2021+1,3),tolerance);
        assertEquals(128.2239852544, CompoundInterest.futureValueReal(100,12, 2022+1,3),tolerance);

    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(100, CompoundInterest.totalSavings(100,2019+1,10), tolerance);
        assertEquals(210, CompoundInterest.totalSavings(100,2020+1,10), tolerance);
        assertEquals(16550, CompoundInterest.totalSavings(5000,2021+1,10), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(100, CompoundInterest.totalSavingsReal(100,2020,10,3), tolerance);
        assertEquals(207, CompoundInterest.totalSavingsReal(100,2021,10,3), tolerance);
        assertEquals(321.49, CompoundInterest.totalSavingsReal(100,2022,10,3), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        //System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
