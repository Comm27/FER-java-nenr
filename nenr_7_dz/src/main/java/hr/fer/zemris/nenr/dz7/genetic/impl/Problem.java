package hr.fer.zemris.nenr.dz7.genetic.impl;

import java.util.Random;

import hr.fer.zemris.nenr.dz7.genetic.IProblem;
import hr.fer.zemris.nenr.dz7.genetic.ISolution;
import hr.fer.zemris.nenr.dz7.nn.ANN;

public class Problem implements IProblem<double[]> {
	
	private static final double MIN = -1d;
	private static final double MAX = 1d;
	
	private ANN ann;
	private Dataset dataset;
	private Random rand;
	
	public Problem(ANN ann, Dataset dataset) {
		this.ann = ann;
		this.dataset = dataset;
		rand = new Random();
	}

	@Override
	public double getFitness(ISolution<double[]> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<double[]> solution) {
		ann.setWeights(solution.getRepresentation());
		
		double error = 0;
		
		for (Sample sample : dataset) {
			double[] output = ann.calculateOutput(sample.getInput());
			error += Math.pow(output[0] - sample.getI1(), 2) +
					Math.pow(output[1] - sample.getI2(), 2) +
					Math.pow(output[2] - sample.getI3(), 2);
		}
		
		return error / dataset.size();
	}

	@Override
	public ISolution<double[]> generateRandomSolution() {
		double[] weights = new double[ann.getWeightsCount()];
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = rand.nextDouble() * (MAX - MIN) + MIN;
		}
		
		DoubleArraySolution solution = new DoubleArraySolution(weights);
		solution.setFitness(getFitness(solution));
		solution.setValue(getValue(solution));
		
		return solution;
	}
}
