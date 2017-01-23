package alekshar.ocm.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
	private Match[] shipManagement;
	private List<LocMatch>[] locationManagement;
	private int bankLength;
	private int shipsCount;

	@SuppressWarnings("unchecked")
	public Solution(int bankLength, int shipsCount){
		this.bankLength = bankLength;
		this.shipsCount = shipsCount;
		this.shipManagement = new Match[shipsCount];
		this.locationManagement = new ArrayList[bankLength];
		for(int i=0; i<bankLength; i++){
			this.locationManagement[i] = new ArrayList<LocMatch>();
		}
	}
	
	public Solution(Problem problem) {
		this(problem.getBankLength(), problem.getShips().size());
	}

	public Ship[] forceManage(Ship ship, int location, int time) {
		if(location+ship.getLength()-1 >= bankLength){
			return null;
		}

		Set<Ship> conflicts =  new HashSet<Ship>();
		int timeFrom = time;
		int timeTo = time+ship.getDuration()-1;
		for(int loc = location; loc<location+ship.getLength(); loc++){
			for(LocMatch locmatch : locationManagement[loc]){
				if(locmatch.ship.equals(ship)){
					continue; //ignore same ship replacement
				}
				//if start before and finish after an occupied period
				if(timeFrom <= locmatch.timeFrom && locmatch.timeTo <= timeTo){
					conflicts.add(locmatch.ship);
					continue;
				}
				//if start or end in an occupied period : impossible to do
				if(locmatch.timeFrom <= timeFrom && timeFrom <= locmatch.timeTo){
					conflicts.add(locmatch.ship);
					continue;
				}
				if(locmatch.timeFrom <= timeTo && timeTo <= locmatch.timeTo){
					conflicts.add(locmatch.ship);
					continue;
				}
			}
		}

		truelyManage(ship, location, time);
		return conflicts.toArray(new Ship[conflicts.size()]);
	}
	
	public boolean manage(Ship ship, int location, int time){
		if(location+ship.getLength()-1 >= bankLength){
			return false;
		}
		int timeFrom = time;
		int timeTo = time+ship.getDuration()-1;
		for(int loc = location; loc<location+ship.getLength(); loc++){
			for(LocMatch locmatch : locationManagement[loc]){
				if(locmatch.ship.equals(ship)){
					continue; //ignore same ship replacement
				}
				//if start before and finish after an occupied period
				if(timeFrom <= locmatch.timeFrom && locmatch.timeTo <= timeTo){
					return false;
				}
				//if start or end in an occupied period : impossible to do
				if(locmatch.timeFrom <= timeFrom && timeFrom <= locmatch.timeTo){
					return false;
				}
				if(locmatch.timeFrom <= timeTo && timeTo <= locmatch.timeTo){
					return false;
				}
			}
		}

		truelyManage(ship, location, time);
		return true;
	}
	
	private void truelyManage(Ship ship, int location, int time) {
		clear(ship);
	
		for(int loc = location; loc<location+ship.getLength(); loc++){
			locationManagement[loc].add(new LocMatch(ship, time, time+ship.getDuration()-1));	
		}
		shipManagement[ship.getId()] = new Match(ship, time, location);
	}

	private void clear(Ship ship) {
		for(List<LocMatch> management : locationManagement){
			for(LocMatch locmatch : management){
				if(locmatch.ship.equals(ship.getId())){
					management.remove(locmatch);
				}
			}
		}
		shipManagement[ship.getId()] = null;
	}

	public Solution clone(){
		Solution newSol = new Solution(bankLength, shipsCount);
		for(Match match : shipManagement){
			newSol.manage(match.ship, match.location, match.time);
		}
		
		return newSol ;
	}


	private class Match{
		private Ship ship;
		private int time;
		private int location;
		public Match(Ship ship, int time, int location) {
			super();
			this.ship = ship;
			this.time = time;
			this.location = location;
		}
	}
	private class LocMatch{
		private Ship ship;
		private int timeFrom;
		private int timeTo;
		public LocMatch(Ship ship, int timeFrom, int timeTo) {
			super();
			this.ship = ship;
			this.timeFrom = timeFrom;
			this.timeTo = timeTo;
		}
	}
	public int getShipsCount() {
		return shipsCount;
	}

	public int[][] getSolved() {
		int[][] solved = new int[shipsCount][2];
		for(int i=0; i<shipsCount; i++){
			Match match = shipManagement[i];
			solved[i][0] = match.time;
			solved[i][1] = match.location;
		}
		return solved;
	}
	
	public int calculateDelay(){
		int delay = 0;
		for(Match match : shipManagement){
			delay+=match.time-match.ship.getArrival();
		}
		return delay;
	}

	public Ship[] getDelayedShips() {
		List<Ship> list =  new ArrayList<Ship>();
		for(Match match : shipManagement){
			if(match.time != match.ship.getArrival()){
				list.add(match.ship);
			}
		}
		return list.toArray(new Ship[list.size()]);
	}
	
	public void print(){
		for(Match match : shipManagement){
			System.out.println(match.ship+" t="+match.time+" p="+match.location);
		}
		System.out.println("delay : "+calculateDelay());
	}

}
