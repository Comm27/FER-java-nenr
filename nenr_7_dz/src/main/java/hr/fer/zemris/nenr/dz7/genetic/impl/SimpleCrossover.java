package hr.fer.zemris.nenr.dz7.genetic.impl;

import java.util.Random;

import hr.fer.zemris.nenr.dz7.genetic.ICrossoverOperator;
import hr.fer.zemris.nenr.dz7.genetic.ISolution;

public class SimpleCrossover implements ICrossoverOperator<double[]> {

	private double threshold;
	private Random rand;
	
	public SimpleCrossover(double threshold) {
		this.threshold = threshold;
		rand = new Random();
	}
	
	@Override
	public ISolution<double[]> crossover(ISolution<double[]> solution1, ISolution<double[]> solution2) {
		double[] repr1 = solution1.getRepresentation();
		double[] repr2 = solution2.getRepresentation();
		
		double[] child = new double[repr1.length];
		
		for (int i = 0; i < repr1.length; i++) {
			if (rand.nextDouble() > threshold) {
				child[i] = repr1[i];
			} else {
				child[i] = repr2[i];
			}
		}
		
		return new DoubleArraySolution(child);
	}
}
