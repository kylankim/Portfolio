package GameOfLife.Element;

public class Cell {

    //false for dead and true for alive
    private boolean state= false;

    public Cell () {
        this.state = false;
    }

    public void setState(boolean b) {
        this.state = b;
    }

    public boolean getState() {
        return this.state;
    }

}
