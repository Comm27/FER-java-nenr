package hr.fer.zemris.nenr.dz6.anfis;

import java.util.ArrayList;
import java.util.List;

public class ANFIS {

	private Dataset dataset;
	private List<Rule> rules;

	List<double[]> epochErrors;
	private int epochs;
	private double eta;
	
	public ANFIS(int numberOfRules, boolean batch, int epochs, double eta) {
		initRules(numberOfRules);
		dataset = new Dataset(batch);
		
		epochErrors = new ArrayList<>();
		this.epochs = epochs;
		this.eta = eta;
	}

	private void initRules(int numberOfRules) {
		rules = new ArrayList<>();
		
		for (int i = 0; i < numberOfRules; i++) {
			rules.add(new Rule());
		}
	}
	
	public void learn() {
		List<List<Sample>> batchGroups = dataset.getBatchGroups();
		
		for (int i = 0; i < epochs; i++) {
			for (List<Sample> group : batchGroups) {
				
				for (Rule rule : rules) {
					rule.resetGradients();
				}
				
				for (Sample sample : group) {
					double predicted = predict(sample.getX(), sample.getY());
					double error = sample.getExpectedValue() - predicted;
					double x = sample.getX();
					double y = sample.getY();
					
					for (Rule rule : rules) {
						double z = rule.getZ(x, y);
						double zizj = getAlphaSum(x, y) * z - getAlphaZSum(x, y);
						double alphaSum = getAlphaSum(x, y);
						
						rule.addGradientValue(
								error,
								x,
								y,
								zizj,
								alphaSum);
					}
				}
				
				for (Rule rule : rules) {
					rule.updateParameters(eta);
				}
			}
			
			System.out.println("Epoch: " + i + " -> " + getError());
			epochErrors.add(new double[]{ i, getError() });
		}
	}
	
	public double predict(double x, double y) {
		double numerator = 0;
		double denominator = 0;
		
		for (@SuppressWarnings("unused") Rule rule : rules) {
			numerator += getAlphaZSum(x, y);
			denominator += getAlphaSum(x, y);
		}
		
		return numerator / denominator;
	}
	
	private double getAlphaZSum(double x, double y) {
		double sum = 0;
		
		for (Rule rule : rules) {
			sum += rule.getAlpha(x, y) * rule.getZ(x, y);
		}
		
		return sum;
	}
	
	private double getAlphaSum(double x, double y) {
		double sum = 0;
		
		for (Rule rule : rules) {
			sum += rule.getAlpha(x, y);
		}
		
		return sum;
	}
	
	public double getError() {
		double error = 0;
		
		for (List<Sample> group : dataset.getBatchGroups()) {
			for (Sample sample : group) {
				error += 0.5 * Math.pow(sample.getExpectedValue() - predict(sample.getX(), sample.getY()), 2);
			}
		}
		
		return error / dataset.getSize();
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public List<double[]> getEpochErrors() {
		return epochErrors;
	}
	
	public List<double[]> get3DErrors() {
		List<double[]> points = new ArrayList<>();
		
		for (List<Sample> group : dataset.getBatchGroups()) {
			for (Sample sample : group) {
				double[] point = new double[3];
				point[0] = sample.getX();
				point[1] = sample.getY();
				point[2] = Math.pow(sample.getExpectedValue() - predict(sample.getX(), sample.getY()), 2);
				points.add(point);
			}
		}
		
		return points;
	}
}