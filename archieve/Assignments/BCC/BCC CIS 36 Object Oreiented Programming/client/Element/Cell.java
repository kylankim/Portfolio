package Element;

public class Cell {
	private String shipName;
	private int shipIndex;
	private int state;
	/*
	 * @Params state == 0  : Dead
	 * 		   state == 1  : blank
	 * 		   state == 2  : Ship
	 */


	public Cell () {
		this.state = 1;
	}


	public void setState(int b) {
		this.state = b;
	}
	public void setShipName(String shipName){
		this.shipName = shipName;
	}
	public void setShipIndex(int index){
		this.shipIndex = index;
	}


	public String getShipName(){
		return this.shipName;
	}
	public int getShipIndex(){
		return this.shipIndex;
	}
	public int getState() {
		return this.state;
	}
	
}
