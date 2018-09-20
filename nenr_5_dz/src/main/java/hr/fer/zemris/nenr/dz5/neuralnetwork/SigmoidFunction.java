package hr.fer.zemris.nenr.dz5.neuralnetwork;

public class SigmoidFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return 1d / (1d + Math.pow(Math.E, -input));
	}
}
