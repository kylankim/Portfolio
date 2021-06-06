package BattleShip.Element;

public class Ship {

    private String shipName;

    //width of the ship
    private int size;

    //height of the ship
    private int num;


    public void setSize(int size){
        this.size = size;
    }

    public void setShipName(String shipName){
        this.shipName = shipName;
    }

    public void setNum(int num){
        this.num = num;
    }

    public int getNum(){
        return this.num;
    }

    public String getShipName(){
        return this.shipName;
    }

    public int getSize(){
        return this.size;
    }
}
