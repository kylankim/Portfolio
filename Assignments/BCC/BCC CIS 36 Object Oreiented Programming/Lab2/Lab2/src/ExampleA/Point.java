package ExampleA;

public class Point {

    private double x;
    private double y;
    private double z;


    //constructor of the point
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void setx(double x) {
        this.x = x;
    }

    public void sety(double y) {
        this.y = y;
    }

    public void setz(double z) {
        this.z = z;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public double getz() {
        return z;
    }
}
