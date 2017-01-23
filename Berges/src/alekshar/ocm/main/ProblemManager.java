package alekshar.ocm.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import alekshar.ocm.model.Problem;
import alekshar.ocm.model.Ship;
import alekshar.ocm.model.Solution;

public class ProblemManager {
	private ProblemManager(){
	}
	
	public static Problem load(String file) throws NumberFormatException, IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		int bankLength = Integer.parseInt(reader.readLine());
		Problem problem = new Problem(bankLength);
		
		Ship.resetCounter();
		int shipsCount = Integer.parseInt(reader.readLine());
		for(int i=0; i<shipsCount; i++){
			String data[] = reader.readLine().split(" ");
			//a0 d0 l0
			int arrival = Integer.parseInt(data[0]);
			int duration = Integer.parseInt(data[1]);
			int length = Integer.parseInt(data[2]);
			Ship ship = new Ship(arrival, duration, length);
			problem.addShip(ship);
		}
		reader.close();
		return problem;
	}
	
	public static void writeSolution(Solution solution, String file) throws IOException{
		FileWriter writer = new FileWriter(new File(file));
		for(int[] step : solution.getSolved()){
			writer.write(step[0]+" "+step[1]+"\n");
		}
		writer.close();
	}
}
