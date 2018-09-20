package hr.fer.zemris.fuzzy.norms;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface ITNorm {

	public double calculate(double ... values);
	public IFuzzySet calculate(IFuzzySet s1, IFuzzySet s2);
}
