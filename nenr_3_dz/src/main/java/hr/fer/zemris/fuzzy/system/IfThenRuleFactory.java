package hr.fer.zemris.fuzzy.system;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.norms.ITNorm;

public class IfThenRuleFactory implements IIfThenRuleFactory {

	private ITNorm tNorm;
	
	public IfThenRuleFactory(ITNorm tNorm) {
		this.tNorm = tNorm;
	}
	
	@Override
	public IIfThenRule createRule(IFuzzySet actionSet, IFuzzySet ... inputSets) {
		List<IfRule> agreementRules = new ArrayList<>();
		
		for (IFuzzySet ifSet : inputSets) {
			agreementRules.add(new IfRule(ifSet));
		}
		
		return new IfThenRule(actionSet, agreementRules, tNorm);
	}
}
