package ExampleE;

public class Calculator {
    private double num1;
    private double num2;

    public Calculator(){
    }

    public Calculator(double num1, double num2){
        this.num1 = num1;
        this.num2 = num2;
    }

    public void setNum1(double num) {
        this.num1 = num;
    }

    public void setNum2(double num) {
        this.num2 = num;
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public double plus(double num1, double num2) {
        return num1 + num2;
    }

    public double minus(double num1, double num2) {
        return num1 - num2;
    }

    public double multiply(double num1, double num2) {
        return num1 * num2;
    }

    public double division(double num1, double num2) {
        return num1 / num2;
    }
}
