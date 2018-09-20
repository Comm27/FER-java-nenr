package hr.fer.zemris.nenr.dz6.anfis;

import java.util.Random;

public class Rule {
	
	private static final double MAX = 1d;
	private static final double MIN = -1d;
	
	private static Random rand = new Random(System.currentTimeMillis());
	
	private double a, b, c, d;
	private double p, q, r;
	
	private double ag, bg, cg, dg;
	private double pg, qg, rg;
	
	public Rule() {
		initParameters();
	}
	
	private void initParameters() {
		a = getRandomValue();
		b = getRandomValue();
		c = getRandomValue();
		d = getRandomValue();
		p = getRandomValue();
		q = getRandomValue();
		r = getRandomValue();
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}
	
	public double getC() {
		return c;
	}
	
	public double getD() {
		return d;
	}

	private double getRandomValue() {
		return rand.nextDouble() * (MAX - MIN) + MIN;
	}

	public double getAlpha(double x, double y) {
		return A(x) * B(y);
	}
	
	private double A(double x) {
		return sigmValue(a, b, x);
	}
	
	private double B(double y) {
		return sigmValue(c, d, y);
	}
	
	private double sigmValue(double a, double b, double x) {
		return 1.0 / (1.0 + Math.pow(Math.E, b * (x - a)));
	}
	
	public double getZ(double x, double y) {
		return p * x + q * y + r;
	}
	
	public void resetGradients() {
		ag = 0;
		bg = 0;
		cg = 0;
		dg = 0;
		
		pg = 0;
		qg = 0;
		rg = 0;
	}
	
	public void addGradientValue(double error, double x, double y, double zizjSum, double alphaSum) {
		ag += error * (zizjSum / Math.pow(alphaSum, 2)) * b * B(y) * A(x) * (1 - A(x));
		bg += error * (zizjSum / Math.pow(alphaSum, 2)) * (a - x) * B(y) * A(x) * (1 - A(x));
		cg += error * (zizjSum / Math.pow(alphaSum, 2)) * d * A(x) * B(y) * (1 - B(y));
		dg += error * (zizjSum / Math.pow(alphaSum, 2)) * (c - y) * A(x) * B(y) * (1- B(y));
		
		pg += error * getAlpha(x, y) / alphaSum * x;
		qg += error * getAlpha(x, y) / alphaSum * y;
		rg += error * getAlpha(x, y) / alphaSum;
	}

	public void updateParameters(double eta) {
		a = a + eta * ag;
		b = b + eta * bg;
		c = c + eta * cg;
		d = d + eta * dg;
		
		p = p + eta * pg;
		q = q + eta * qg;
		r = r + eta * rg;
	}
}
