package hr.fer.zemris.nenr.dz4;

import java.text.DecimalFormat;
import java.util.Arrays;

import hr.fer.zemris.algorithms.genetic.ISolution;

public class DoubleArraySolution implements ISolution<double[]> {

	private double[] values;
	private double fitness;
	private double value;
	
	public DoubleArraySolution(double[] values) {
		this.values = values;
	}
	
	public DoubleArraySolution(double[] values, double fitness, double value) {
		this(values);
		this.fitness = fitness;
		this.value = value;
	}

	@Override
	public double[] getRepresentation() {
		return values;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public ISolution<double[]> copy() {
		return new DoubleArraySolution(
				Arrays.copyOf(values, values.length),
				fitness,
				value);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("###.###");
		
		sb.append("Value: " + value + "; ");
		
		for (double d : values) {
			sb.append(df.format(d) + ", ");
		}
		
		return sb.toString();
	}
}
