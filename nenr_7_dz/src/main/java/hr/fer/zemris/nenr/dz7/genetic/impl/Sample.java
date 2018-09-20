package hr.fer.zemris.nenr.dz7.genetic.impl;

public class Sample {

	private double d1;
	private double d2;
	private int i1;
	private int i2;
	private int i3;
	
	public Sample(double d1, double d2, int i1, int i2, int i3) {
		this.d1 = d1;
		this.d2 = d2;
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
	}
	
	public double[] getInput() {
		return new double[]{ d1, d2 };
	}
	
	public int[] getCode() {
		return new int[]{ i1, i2, i3 };
	}
	
	public double getD1() {
		return d1;
	}
	
	public double getD2() {
		return d2;
	}
	
	public int getI1() {
		return i1;
	}
	
	public int getI2() {
		return i2;
	}
	
	public int getI3() {
		return i3;
	}
}
