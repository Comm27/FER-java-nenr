package hr.fer.zemris.nenr.dz6.anfis;

public class Sample {

	private double x;
	private double y;
	private double expectedValue;

	public Sample(double x, double y, double expectedValue) {
		this.x = x;
		this.y = y;
		this.expectedValue = expectedValue;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getExpectedValue() {
		return expectedValue;
	}
}
