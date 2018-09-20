package hr.fer.zemris.nenr.dz5.neuralnetwork.learning;

public class LearningSample {

	private double[] input;
	private double[] expectedOutput;
	
	public LearningSample(double[] input, double[] expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public double[] getInput() {
		return input;
	}
	
	public double[] getExpectedOutput() {
		return expectedOutput;
	}
}
