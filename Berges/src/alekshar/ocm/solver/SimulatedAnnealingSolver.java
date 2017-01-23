package alekshar.ocm.solver;

import java.util.Random;

import alekshar.ocm.model.Problem;
import alekshar.ocm.model.Ship;
import alekshar.ocm.model.Solution;

public class SimulatedAnnealingSolver implements ISolver{
	private int nbIterations;
	
	private Solution currSol;
	private int currDelay;
	private Solution bestSol;
	private int bestDelay;

	private Random rand;

	private int bankLength;
	
	public SimulatedAnnealingSolver(int nbIterations) {
		this.nbIterations = nbIterations;
	}
	
	@Override
	public Solution solve(Problem problem) {
		bankLength = problem.getBankLength();
		currSol = new GreedySolver().solve(problem);
		currDelay = currSol.calculateDelay();
		bestSol = currSol.clone();
		bestDelay = currDelay;

		double deltaT = Math.max(currDelay / nbIterations, 1);
		int temperature = currDelay;

		rand = new Random(0);
		process : for(int i=0; i<nbIterations; i++){
			if(temperature == 0){
				break;
			}
		
			Solution currentSolution = currSol.clone();
			System.out.println("-------------------------");
			currSol.print();
			transmute(currentSolution);
			currSol.print();
			
			int newDelay = currentSolution.calculateDelay();
			int delta = newDelay - currDelay;
			if(delta < 0 || rand.nextDouble() < Math.exp(-delta/temperature)){
				currSol = currentSolution;
				currDelay = newDelay;
				if(currDelay < bestDelay){
					bestSol = currentSolution;
					bestDelay = currDelay;
					if(bestDelay == 0){
						break process;
					}
				}
			}
			temperature -= deltaT;
		} 

		System.out.println("------FINALLY----------");
		bestSol.print();
		return bestSol;
	}


	private void transmute(Solution sol) {
		Ship[] delayedShips = sol.getDelayedShips();
		if(delayedShips.length == 0){
			return;
		}
		for(Ship ship : delayedShips){
			System.out.println("delayed ship : "+ship);
		}
		Ship shipToMove = delayedShips[rand.nextInt(delayedShips.length)];
		int moveLocation = rand.nextInt(bankLength-shipToMove.getLength()+1);
		//move selected ship
		System.out.println("transmuting : "+shipToMove+" to "+moveLocation+" at "+shipToMove.getArrival());
		Ship[] conflictingShips = sol.forceManage(shipToMove, moveLocation, shipToMove.getArrival());
		//manage conflicting ships with greedy method
		shipsLoop : for(Ship ship : conflictingShips){
			int time = ship.getArrival();
			int currentBank = 0;
			while(true){
				if(sol.manage(ship, currentBank, time)){
					System.out.println("applying consequence on "+ship+" to "+currentBank+" at "+time);
					continue shipsLoop;
				}
				currentBank++;
				if(currentBank+ship.getLength() > bankLength){
					time++;
					currentBank = 0;
				}
			}
		}
	}

}
