package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface IIfThenRuleFactory {

	public IIfThenRule createRule(IFuzzySet actionSet, IFuzzySet ... inputSets);
}
