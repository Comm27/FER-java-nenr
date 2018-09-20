package hr.fer.zemris.fuzzy.norms;

import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.Operations;

public class MaxSNorm implements ISNorm {

	@Override
	public IFuzzySet calculate(IFuzzySet s1, IFuzzySet s2) {
		return Operations.binaryOperation(s1, s2, Operations.zadehOr());
	}	
}
