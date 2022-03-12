package kr.co.GameWorld.Element;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grid {
	ArrayList<Cell[]> grid;
	int row;
	int column;

	public Grid(int r, int c) {
		this.row = r;
		this.column = c;
		newGrid(row, column);
	}
	
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

	public boolean getCellState(int a, int b){
		return  grid.get(a)[b].getState();
	}

	
	public ArrayList<Cell[]> getGrid(){
		return grid;
	}
	public void setGrid(ArrayList<Cell[]> g){
		this.grid = g;
	}
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public void delGrid() {
		grid = null;
	}
}
