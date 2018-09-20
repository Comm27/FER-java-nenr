package hr.fer.zemris.nenr.dz7.nn;

public class IdentityFunction implements IActivationFunction {

	@Override
	public double valueAt(double input) {
		return input;
	}
}
