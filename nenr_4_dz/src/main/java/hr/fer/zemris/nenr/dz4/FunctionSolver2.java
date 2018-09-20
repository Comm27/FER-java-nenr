package hr.fer.zemris.nenr.dz4;

import java.util.List;

import hr.fer.zemris.algorithms.genetic.EliminationGA;
import hr.fer.zemris.algorithms.genetic.IOptAlgorithm;
import hr.fer.zemris.algorithms.genetic.IProblem;
import hr.fer.zemris.algorithms.genetic.TournamentSelOp;

public class FunctionSolver2 {

	public static void main(String[] args) {
		
		List<double[]> datasetList = Util.loadDataset("zad4-dataset2.txt");
		if (datasetList == null) {
			System.out.println("Cant load dataset.");
			return;
		}
		
		IProblem<double[]> problem = new Function(datasetList);
		
		IOptAlgorithm<double[]> algorithm =
				new EliminationGA<>(
						20,									//population size
						new TournamentSelOp<>(5, true),		//selection operator
						new BLXAlphaCrossover(2.0), 		//crossover operator
						new DecimalMutationOperator(2, 0.2),	//mutation operator
						problem,							//problem
						0.1,								//fitness threshold
						50000);								//max generations
		
		algorithm.run();
	}
}
