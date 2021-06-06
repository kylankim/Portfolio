package ExampleE;

public class CalculatorUnitTester {
    private Calculator calc;

    @Initialize
    public void initialize() {
        calc = null;
    }

    @Cleanup
    public void setup(){
        calc = new Calculator();
        calc.setNum1(0);
        calc.setNum2(0);
    }

    @before
    public void finish() {
    }

    @after
    @test
    void testAdd(){
        Calculator c = new Calculator();
        double expected = 30;
        double result = c.multiply(5, 6);
        if (Math.abs(expected - result) < 0.000000001)
            System.out.println("TestAdd succeeded");
        else
            System.out.println("TestAdd failed");
    }
}
