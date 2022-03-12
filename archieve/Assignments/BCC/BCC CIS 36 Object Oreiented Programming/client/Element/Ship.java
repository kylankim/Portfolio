package Element;

public class Ship {
    private int size;
    private String shipName;
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
