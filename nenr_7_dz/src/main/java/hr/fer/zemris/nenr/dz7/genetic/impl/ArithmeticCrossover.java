package hr.fer.zemris.nenr.dz7.genetic.impl;

import hr.fer.zemris.nenr.dz7.genetic.ICrossoverOperator;
import hr.fer.zemris.nenr.dz7.genetic.ISolution;

public class ArithmeticCrossover implements ICrossoverOperator<double[]> {

	@Override
	public ISolution<double[]> crossover(ISolution<double[]> solution1, ISolution<double[]> solution2) {
		double[] repr1 = solution1.getRepresentation();
		double[] repr2 = solution2.getRepresentation();
		
		double[] child = new double[repr1.length];
		
		for (int i = 0; i < repr1.length; i++) {
			child[i] = (repr1[i] + repr2[i]) / 2;
		}
		
		return new DoubleArraySolution(child);
	}
}
