package hr.fer.zemris.nenr.dz5.neuralnetwork;

public class IdentityFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return input;
	}
}
