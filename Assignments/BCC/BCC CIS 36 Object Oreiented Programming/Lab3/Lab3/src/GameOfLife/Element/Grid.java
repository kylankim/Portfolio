package GameOfLife.Element;

import java.util.ArrayList;

public class Grid {
    ArrayList<Cell[]> grid;
    int row;
    int column;

    //constructor of the Grid
    public Grid(int r, int c) {
        this.row = r;
        this.column = c;
        newGrid(row, column);
    }

    //Constructs each cell array list in the Grid
    public void newGrid(int r, int c) {
        grid = new ArrayList<Cell[]>();
        for (int j = 0; j < r; j++) {
            Cell[] tempList = new Cell[c];
            for (int i = 0; i < c; i++) {
                tempList[i] = new Cell();
            }
            grid.add(tempList);
        }
    }

    public void setCellState(int a, int b, boolean state){
        grid.get(a)[b].setState(state);
    }

    public void setGrid(ArrayList<Cell[]> g){
        this.grid = g;
    }

    public ArrayList<Cell[]> getGrid(){
        return grid;
    }

    public boolean getCellState(int a, int b){
        return  grid.get(a)[b].getState();
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    //Delete the grid
    public void delGrid() {
        grid = null;
    }
}
