package hr.fer.zemris.nenr.dz4;

import java.util.Random;

import hr.fer.zemris.algorithms.genetic.ICrossoverOperator;
import hr.fer.zemris.algorithms.genetic.ISolution;

public class BLXAlphaCrossover implements ICrossoverOperator<double[]> {

	private double alpha;
	private Random random;
	
	public BLXAlphaCrossover(double alpha) {
		this.alpha = alpha;
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public ISolution<double[]> crossover(ISolution<double[]> solution1, ISolution<double[]> solution2) {
		double[] s1Repr = solution1.getRepresentation();
		double[] s2Repr = solution2.getRepresentation();
		
		double[] ciMin = new double[s1Repr.length];
		double[] ciMax = new double[s1Repr.length];
		double[] ii = new double[s1Repr.length];
		
		for (int i = 0; i < ciMin.length; i++) {
			ciMin[i] = s1Repr[i] < s2Repr[i] ? s1Repr[i] : s2Repr[i];
			ciMax[i] = s1Repr[i] < s2Repr[i] ? s2Repr[i] : s1Repr[i];
			ii[i] = ciMax[i] - ciMin[i];
		}
		
		double[] childRepr = new double[s1Repr.length];
		
		for (int i = 0; i < childRepr.length; i++) {
			double maxValue = ciMax[i] + ii[i] * alpha;
			double minValue = ciMin[i] - ii[i] * alpha;
			
			childRepr[i] = random.nextDouble() * (maxValue - minValue) + minValue;
			
			if (childRepr[i] < Function.MIN_VALUE) {
				childRepr[i] = Function.MIN_VALUE;
			} else if (childRepr[i] > Function.MAX_VALUE) {
				childRepr[i] = Function.MAX_VALUE;
			}
		}
		
		return new DoubleArraySolution(childRepr);
	}
}
