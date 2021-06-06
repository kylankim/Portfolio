package BattleShip.Element;

public class Cell {
    private String shipName;
    private int state;

    //0 for dead, 1 for blank, 2 for ship
    public Cell () {
        this.state = 1;
    }


    public void setState(int b) {
        this.state = b;
    }

    public void setShipName(String shipName){
        this.shipName = shipName;
    }

    public String getShipName(){
        return this.shipName;
    }

    public int getState() {
        return this.state;
    }
}
