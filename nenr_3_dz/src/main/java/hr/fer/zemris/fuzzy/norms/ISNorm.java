package hr.fer.zemris.fuzzy.norms;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface ISNorm {

	public IFuzzySet calculate(IFuzzySet s1, IFuzzySet s2);
}
