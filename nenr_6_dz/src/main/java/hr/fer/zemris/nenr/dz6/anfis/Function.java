package hr.fer.zemris.nenr.dz6.anfis;

public class Function {

	public static double valueAt(double x, double y) {
		double result = Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5 * x * y + 3;
		result *= Math.pow(Math.cos(x / 5d), 2);
		return result;
	}
}
