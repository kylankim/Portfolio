import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    static final double tolerance = 0.01;
    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers
        assertEquals(0, 0); */
        assertEquals(1, CompoundInterest.numYears(2019));
        assertEquals(2, CompoundInterest.numYears(2020));
        assertEquals(4, CompoundInterest.numYears(2022));
        assertEquals(-1, CompoundInterest.numYears(2017));
    }

    @Test
    public void testFutureValue() {
        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2020), tolerance);
        assertEquals(179.56, CompoundInterest.futureValue(100, 34, 2020), tolerance);
        assertEquals(112, CompoundInterest.futureValue(100, 12, 2019), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        assertEquals(11.8026496, CompoundInterest.futureValueReal(10, 12, 2020, 3), tolerance);
        assertEquals(168.948004, CompoundInterest.futureValueReal(100,34, 2020, 3), tolerance);
        assertEquals(107.52, CompoundInterest.futureValueReal(100, 12, 2019, 4), tolerance);
    }


    @Test
    public void testTotalSavings() {
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2020, 10), tolerance);
        assertEquals(3000, CompoundInterest.totalSavings(3000,2018,10), tolerance);
        assertEquals(6300, CompoundInterest.totalSavings(3000, 2019, 10), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        assertEquals(3000, CompoundInterest.totalSavingsReal(3000, 2018, 10,3), tolerance);
        assertEquals(6201, CompoundInterest.totalSavingsReal(3000, 2019, 10, 3), tolerance);
        assertEquals(7779.3856, CompoundInterest.totalSavingsReal(2500, 2020, 8, 4), tolerance);

    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
