package kr.co.GameWorld.Element;

import java.util.ArrayList;
import java.util.HashMap;

public class Check {
    private Grid grid;

    public Check(Grid g){
        this.grid = g;
    }

    public Grid check(){
        ArrayList<HashMap<String,Integer>> point = new ArrayList<HashMap<String,Integer>>();
        int row = grid.getRow();
        int column = grid.getColumn();

        for(int i = 0; i < row; i++){
            for(int j = 0; j< column; j++){
                int count = 0;

                if(j+1 < column){
                    if(grid.getCellState(i,j+1))
                        count +=1;
                }

                if(j-1 > 0){
                    if(grid.getCellState(i,j-1))
                        count +=1;
                }

                if(i+1 < row){
                    if(grid.getCellState(i+1,j))
                        count +=1;
                }

                if(i-1 > 0){
                    if(grid.getCellState(i-1,j))
                        count +=1;
                }

                if(grid.getCellState(i,j)){
                    if(count > 3 || count < 2){
                        HashMap<String,Integer> tempHash = new HashMap<String,Integer>();
                        tempHash.put("row",i);
                        tempHash.put("column",j);

                        point.add(tempHash);
                    }
                }else{
                    if(count == 3){
                        HashMap<String,Integer> tempHash = new HashMap<String,Integer>();
                        tempHash.put("row",i);
                        tempHash.put("column",j);
                        point.add(tempHash);
                    }
                }
            }
        }
        System.out.println("포인트 크기"+point.size()+"\n로우");
        for(int i = 0; i< point.size(); i++){
            int hash_row = point.get(i).get("row");
            int hash_column = point.get(i).get("column");
            if(grid.getCellState(hash_row,hash_column)){
                grid.setCellState(hash_row,hash_column,false);
                System.out.println(hash_row+","+hash_column+" 0으로");
            }else{
                grid.setCellState(hash_row,hash_column,true);
                System.out.println(hash_row+","+hash_column+" 1으로");
            }


        }

        return grid;
    }
}
