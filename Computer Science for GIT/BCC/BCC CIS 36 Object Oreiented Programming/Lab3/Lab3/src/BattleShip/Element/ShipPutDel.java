package BattleShip.Element;

import java.awt.*;
import java.util.ArrayList;

public class ShipPutDel {
    private  Grid grid;
    private TextArea textArea;
    private ShipContainer shipList;


    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setShipList(ShipContainer shipList){
        this.shipList = shipList;
    }

    //Combining all data received as a parameter
    public ShipPutDel(Grid grid, TextArea textArea, ShipContainer shipList){
        this.grid = grid;
        this.textArea = textArea;
        this.shipList = shipList;
    }

    //Assigning a ship to the cells after checking the cells for ship assigned is empty
    public void put(String point, int xVec , int yVec, int shipIndex){
        if (shipList.getShipArray().size() != 0) {
            String shipname = shipList.getShipArray().get(shipIndex).getShipName();
            int shipsize = shipList.getShipArray().get(shipIndex).getSize();
            int shipNum = shipList.getShipArray().get(shipIndex).getNum();

            String token[] = point.split(",");
            int point_X = (int) (token[0].toUpperCase().charAt(0) - 64);
            int point_Y = Integer.parseInt(token[1]);

            System.out.println("pointX :" + point_X + " pointY" + point_Y);
            ArrayList<Cell[]> templist = new ArrayList<Cell[]>();
            templist.addAll(grid.getGrid());

            boolean flag = true;
//            System.out.println("(point_X + (shipsize * xVec)) : " + (point_X + (shipsize * xVec)));
//            System.out.println("(point_Y + (shipNum * yVec)) : " + (point_Y + (shipNum * yVec)));
            if (0 < (point_X + (shipsize * xVec)) && (point_X + (shipsize * xVec)) < 25) {
                if (0 < (point_Y + (shipNum * yVec)) && (point_Y + (shipNum * yVec) < 25)) {
                    int curr_x = point_X;
                    int curr_y = point_Y;
                    for (int i = 0; i < shipNum; i++) {
                        for (int j = 0; j < shipsize; j++) {
                            if (grid.getCellState(curr_x, curr_y) == 1) {
                            } else {
                                flag = false;
                                textArea.append("----------------\n");
                                textArea.append((char) (curr_x + 65) + "," + curr_y + " There is a ship in  assigned cells .\n");
                                textArea.append("----------------\n");
                            }
                            curr_x += xVec;
                        }
                        curr_x = point_X;
                        curr_y += yVec;
                    }
                } else {
                    textAreaStr("Please enter the Y coordinate exactly.");
                }
            } else {
                textAreaStr("Please enter the X coordinate exactly.");
            }

            if (flag) {
                if (0 < (point_X + (shipsize * xVec)) && (point_X + (shipsize * xVec)) < 25) {
                    if (0 < (point_Y + (shipNum * yVec)) && (point_Y + (shipNum * yVec) < 25)) {
                        int curr_x = point_X;
                        int curr_y = point_Y;
                        for (int i = 0; i < shipNum; i++) {
                            for (int j = 0; j < shipsize; j++) {
                                if (grid.getCellState(curr_x, curr_y) == 1) {
                                    grid.setCellState(curr_x, curr_y, 2);
                                    grid.setCellName(curr_x, curr_y, shipname);
                                    System.out.println("Origin :: X :" + (char) (curr_x + 65) + " Y :" + curr_y);
                                }
                                curr_x += xVec;
                            }
                            curr_x = point_X;
                            curr_y += yVec;
                        }
                        shipList.getShipArray().remove(shipIndex);
                    }

                }

            }
        } else {
            textAreaStr("placed all the ships.");
        }
    }

    //check whether cell is empty and kills the ship if ship drops.
    public void ShipDrop(String point){
        String token[] = point.split(",");
        int point_X = (int) (token[0].toUpperCase().charAt(0) - 64);
        int point_Y = Integer.parseInt(token[1]);
        String shipName = grid.getCellName(point_X,point_Y);

        if(grid.getCellState(point_X,point_Y) == 1){
            grid.setCellState(point_X,point_Y,0);
        }
        else if(grid.getCellState(point_X,point_Y) == 2){
            for(int i = 0; i < 25; i++){
                for(int j = 0; j < 25; j++){
                    if(grid.getCellName(j,i) == shipName){
                        grid.setCellState(j,i,0);
                    }
                }
            }
        }else if(grid.getCellState(point_X,point_Y) == 0){
           textArea.append("Already dead");
        }
        textAreaStr("Drop "+shipName);
    }

    //text shown in the text area
    private void textAreaStr(String str){
        textArea.append("----------------\n");
        textArea.append(str+"\n");
        textArea.append("----------------\n");
    }
}
