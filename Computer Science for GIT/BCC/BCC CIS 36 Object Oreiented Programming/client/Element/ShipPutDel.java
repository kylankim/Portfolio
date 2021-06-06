package Element;


import java.awt.*;


public class ShipPutDel {
    private  Grid grid;
    private TextArea textArea;
    private ShipContainer shipList;

    public ShipPutDel(Grid grid, TextArea textArea, ShipContainer shipList){
        this.grid = grid;
        this.textArea = textArea;
        this.shipList = shipList;
    }


    public boolean put(String pointA,int xVec, int yVec, int shipIndex){
        boolean result = false;
        Grid tempGrid = new Grid();
        tempGrid.getGrid().addAll(grid.getGrid());
        String shipname = shipList.getShipArray().get(shipIndex).getShipName();
        int shipsize = shipList.getShipArray().get(shipIndex).getSize();
        int shipNum = shipList.getShipArray().get(shipIndex).getNum();

        String token[]  = pointA.split(",");
        int pointA_X = Integer.parseInt(token[0]);
        int pointA_Y = (int)(token[1].toUpperCase().charAt(0)- 65);

        System.out.println(pointA+"pointA_Y :" +pointA_Y +" x:"+ xVec + " y:"+yVec +" index:" +shipIndex);
        System.out.println("(pointA_X + (shipsize *xVec) : " + (pointA_X + (shipsize *xVec)) + "(pointA_Y + (shipNum * xVec))" + (pointA_Y + (shipNum * xVec)));
        boolean flag = true;
        if((pointA_X + (shipsize *xVec)) < 25 && 0 < (pointA_X + (shipsize *xVec))){
            System.out.println("fisrst if start");
            if((pointA_Y + (shipNum * xVec)) < 25 && 0 < (pointA_Y + (shipNum * xVec))){
                System.out.println("second if start");
                int x = pointA_X;
                int y = pointA_Y;
                for(int i = 0; i < shipNum;  i++){
                    for(int j = 0; j < shipsize; j++){
                        if(tempGrid.getCellState(x,y) == 1){
                            tempGrid.setCellState(x,y,2);
                            tempGrid.setCellName(x,y,shipname);
                            System.out.println("clone : "+x+","+(char)(y+65)+" ship : " +shipname);
                        }else
                            flag = false;
                        x += xVec;
                    }
                    y += yVec;
                }

            }
            else
                textArea.append("잘못된 좌표입니다. y축 좌표를 조정해주세요.");
        }
        else
            textArea.append("잘못된 좌표입니다. x축 좌표를 조정해주세요.");

        if(flag){
            int x = pointA_X;
            int y = pointA_Y;
            for(int i = 0; i < shipNum;  i++){
                for(int j = 0; j < shipsize; j++){
                    System.out.println("j : " + j + " grid.getCellState(x,y) : " + grid.getCellState(x,y));
                    if(grid.getCellState(x,y) == 1){
                        grid.setCellState(x,y,2);
                        grid.setCellName(x,y,shipname);
                        System.out.println("origin : "+x+","+(char)(y+65)+" ship : " +shipname);
                    }else
                    x += xVec;
                }
                y += yVec;
            }
            shipList.getShipArray().remove(shipIndex);
            result = true;
        }


        return result;
    }

    public boolean ShipDrop(int x, int y){
        boolean result = false;


        return result;
    }
}
