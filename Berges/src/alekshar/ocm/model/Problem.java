package alekshar.ocm.model;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	private int bankLength;
	private List<Ship> ships = new ArrayList<Ship>();

	public Problem(int bankLength){
		this.bankLength = bankLength;
	}
	
	public void addShip(Ship ship){
		this.ships.add(ship);
	}
	
	public void print(){
		System.out.println("Length : "+bankLength);
		for(Ship ship : ships){
			System.out.println(ship);
		}
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public int getBankLength() {
		return bankLength;
	}
}
