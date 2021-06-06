package kr.co.GameWorld.Element;

public class Cell {
	private boolean state= false;
	/*
	 * @Params state == 0  : Dead
	 * 		   state == 1  : Alive
	 */
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
