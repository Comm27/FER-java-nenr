package hr.fer.zemris.nenr.dz7.genetic.impl;

import java.util.Random;

import hr.fer.zemris.nenr.dz7.genetic.IMutationOperator;
import hr.fer.zemris.nenr.dz7.genetic.ISolution;


public class MutationAdd implements IMutationOperator<double[]> {

	private double sigma;
	private double mutationThreshold;
	private Random random;
	
	public MutationAdd(double sigma, double mutationThreshold) {
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
		}
		
		return mutatedSolution;
	}
}
