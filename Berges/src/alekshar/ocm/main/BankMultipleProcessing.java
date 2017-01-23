package alekshar.ocm.main;

import java.io.FileWriter;
import java.io.IOException;

import alekshar.ocm.model.Problem;
import alekshar.ocm.model.Solution;
import alekshar.ocm.solver.GreedySolver;
import alekshar.ocm.solver.SimulatedAnnealingSolver;

public class BankMultipleProcessing {
	static String[] probs = {
			"i004_007A",
			"i004_007B",
			"i005_010",
			"i010_050",
			"i020_100",
			"i050_500"
	};
	static int nbit = 100;

	public static void main(String[] args) throws NumberFormatException, IOException {
		FileWriter writer = new FileWriter("output.csv");
		writer.write("problem,#,bestDelay,calculationTime\n");
		for(String prob : probs){
			Problem problem = ProblemManager.load("data/"+prob);
			for(int i=1; i<=10; i++){
				long time = System.currentTimeMillis();
				Solution solution = new SimulatedAnnealingSolver(nbit).solve(problem);
				time = System.currentTimeMillis() - time;
				writer.write(prob+","+i+","+solution.calculateDelay()+","+time+"\n");
				writer.flush();
			}
		}
	}

}
