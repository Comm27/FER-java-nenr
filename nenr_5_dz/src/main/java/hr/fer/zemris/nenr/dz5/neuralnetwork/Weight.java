package hr.fer.zemris.nenr.dz5.neuralnetwork;

public class Weight implements Cloneable {

	private double value;

	public Weight(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public Weight copy() {
		return new Weight(value);
	}
}
