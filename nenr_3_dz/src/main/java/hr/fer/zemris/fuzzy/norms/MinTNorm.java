package hr.fer.zemris.fuzzy.norms;

import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.Operations;

public class MinTNorm implements ITNorm {

	@Override
	public double calculate(double... values) {
		double min = Double.MAX_VALUE;
		
		for (double d : values) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;
	}

	@Override
	public IFuzzySet calculate(IFuzzySet s1, IFuzzySet s2) {
		return Operations.binaryOperation(s1, s2, Operations.zadehAnd());
	}
}
