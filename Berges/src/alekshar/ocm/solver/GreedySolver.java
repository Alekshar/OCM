package alekshar.ocm.solver;

import alekshar.ocm.model.Problem;
import alekshar.ocm.model.Ship;
import alekshar.ocm.model.Solution;

public class GreedySolver implements ISolver{

	@Override
	public Solution solve(Problem problem) {
		int time = 0;
		int bankLength = problem.getBankLength();
		int currentBank = 0;
		
		Solution solution = new Solution(problem);
		shipsLoop : for(Ship ship : problem.getShips()){
			time = ship.getArrival();
			currentBank = 0;
			while(true){
				if(solution.manage(ship, currentBank, time)){
					continue shipsLoop;
				}
				currentBank++;
				if(currentBank+ship.getLength() > bankLength){
					time++;
					currentBank = 0;
				}
			}
		}
		solution.print();
		return solution;
	}

}
