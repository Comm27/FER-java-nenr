package hr.fer.zemris.nenr.dz4;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.algorithms.genetic.IProblem;
import hr.fer.zemris.algorithms.genetic.ISolution;

public class Function implements IProblem<double[]> {

	public static final double MIN_VALUE = -4d;
	public static final double MAX_VALUE = 4d;
	
	private List<double[]> dataset;
	private int solutionSize;
	private Random rand;
	
	public Function(List<double[]> dataset) {
		this.dataset = dataset;
		solutionSize = 5;
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public double getFitness(ISolution<double[]> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<double[]> solution) {
		double[] representation = solution.getRepresentation();
		
		double error = 0;
		
		for (double[] ds : dataset) {
			error += getErrorForMeasurement(ds, representation);
		}
		
		return error / dataset.size();
	}

	private double getErrorForMeasurement(double[] ds, double[] representation) {
		double value1 = Math.sin(representation[0] + representation[1] * ds[0]);
		double value2 = representation[2] * Math.cos(ds[0] * (representation[3] + ds[1]));
		double value3 = 1.0 / (1.0 + Math.pow(Math.E, (Math.pow(ds[0] - representation[4], 2))));
	
		return Math.pow(ds[2] - (value1 + value2 * value3), 2);
	}

	@Override
	public ISolution<double[]> generateRandomSolution() {
		double[] solutionValues = new double[solutionSize];
		
		for (int i = 0; i < solutionValues.length; i++) {
			solutionValues[i] = rand.nextDouble() * (MAX_VALUE - MIN_VALUE) - MIN_VALUE;
		}
		
		ISolution<double[]> solution = new DoubleArraySolution(solutionValues, 0d, 0d);
		solution.setFitness(getFitness(solution));
		solution.setValue(getValue(solution));
		
		return solution;
	}
}
