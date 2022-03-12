package Element;

import java.util.ArrayList;

public class ShipContainer {
    private ArrayList<Ship> shipArray;

    public ShipContainer(){
        shipArray = new ArrayList<Ship>();
        for(int i =0; i <5; i++)
            shipArray.add(new Ship());

        shipArray.get(0).setShipName("aircraft carrier");
        shipArray.get(0).setSize(5);
        shipArray.get(0).setNum(1);

        shipArray.get(1).setShipName("battle ship");
        shipArray.get(1).setSize(4);
        shipArray.get(1).setNum(2);

        shipArray.get(2).setShipName("submarine");
        shipArray.get(2).setSize(3);
        shipArray.get(2).setNum(2);

        shipArray.get(3).setShipName("destroyer");
        shipArray.get(3).setSize(3);
        shipArray.get(3).setNum(2);

        shipArray.get(4).setShipName("patrol boat");
        shipArray.get(4).setSize(2);
        shipArray.get(4).setNum(4);
    }

    public ArrayList<Ship> getShipArray() {
        return shipArray;
    }
}
