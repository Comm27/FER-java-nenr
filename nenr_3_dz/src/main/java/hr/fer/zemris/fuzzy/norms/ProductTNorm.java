package hr.fer.zemris.fuzzy.norms;

import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.Operations;

public class ProductTNorm implements ITNorm {

	@Override
	public double calculate(double... values) {
		double result = 1;
		
		for (double d : values) {
			result *= d;
		}
		
		return result;
	}

	@Override
	public IFuzzySet calculate(IFuzzySet s1, IFuzzySet s2) {
		return Operations.binaryOperation(s1, s2, (v1, v2) -> calculate(v1, v2));
	}
}
