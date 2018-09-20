package hr.fer.zemris.fuzzy.system;

import java.util.List;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.norms.ITNorm;

public class IfThenRule implements IIfThenRule {

	private IFuzzySet fuzzyAction;
	private List<IfRule> ifRules;
	private ITNorm tNorm;
	
	public IfThenRule(IFuzzySet rule, List<IfRule> ifRules, ITNorm tNorm) {
		this.fuzzyAction = rule;
		this.ifRules = ifRules;
		this.tNorm = tNorm;
	}

	@Override
	public IFuzzySet getRuleConclusion(List<DomainElement> domainElements) {
		double ruleAgreement = getRuleAgreement(domainElements);
	
		MutableFuzzySet fuzzyActionScaled = new MutableFuzzySet(fuzzyAction.getDomain());
		
		for (DomainElement domainElement : fuzzyAction.getDomain()) {
			fuzzyActionScaled.set(domainElement, tNorm.calculate(ruleAgreement, fuzzyAction.getValueAt(domainElement)));
		}
		
		return fuzzyActionScaled;
	}
	
	private double getRuleAgreement(List<DomainElement> domainElements) {
		double[] ifRuleAgreements = new double[domainElements.size()];
		
		for (int i = 0, size = domainElements.size(); i < size; i++) {
			ifRuleAgreements[i] = ifRules.get(i).measureOfAgreement(domainElements.get(i));
		}
		
		 return tNorm.calculate(ifRuleAgreements);
	}
}