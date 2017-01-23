package alekshar.ocm.main;

import java.io.IOException;

import alekshar.ocm.model.Problem;
import alekshar.ocm.model.Solution;
import alekshar.ocm.solver.GreedySolver;
import alekshar.ocm.solver.SimulatedAnnealingSolver;

public class BankMain {
//struct from to nbits
	public static void main(String args[]) throws NumberFormatException, IOException {
		Problem problem = ProblemManager.load(args[0]);

//		Solution solution = new GreedySolver().solve(problem);
		Solution solution = new SimulatedAnnealingSolver(Integer.parseInt(args[2])).solve(problem);
		ProblemManager.writeSolution(solution, args[1]);
	
	}
}
