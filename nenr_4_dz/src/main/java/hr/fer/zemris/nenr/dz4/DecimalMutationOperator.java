package hr.fer.zemris.nenr.dz4;

import java.util.Random;

import hr.fer.zemris.algorithms.genetic.IMutationOperator;
import hr.fer.zemris.algorithms.genetic.ISolution;


public class DecimalMutationOperator implements IMutationOperator<double[]> {

	private double sigma;
	private double mutationThreshold;
	private Random random;
	
	public DecimalMutationOperator(double sigma, double mutationThreshold) {
		this.sigma = sigma;
		this.mutationThreshold = mutationThreshold;
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public ISolution<double[]> mutate(ISolution<double[]> solution) {
		ISolution<double[]> mutatedSolution = solution.copy();
		double[] mutatedValues = mutatedSolution.getRepresentation();
		
		for (int i = 0; i < mutatedValues.length; i++) {
			if (random.nextDouble() > mutationThreshold) {
				continue;
			}
			
			mutatedValues[i] += random.nextGaussian() * sigma;
			
			if (mutatedValues[i] < Function.MIN_VALUE) {
				mutatedValues[i] = Function.MIN_VALUE;
			} else if (mutatedValues[i] > Function.MAX_VALUE) {
				mutatedValues[i] = Function.MAX_VALUE;
			}
		}
		
		return mutatedSolution;
	}
}
