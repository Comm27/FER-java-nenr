package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class IfRule {

	private IFuzzySet ruleMeaning;
	
	public IfRule(IFuzzySet ruleMeaning) {
		this.ruleMeaning = ruleMeaning;
	}

	public double measureOfAgreement(DomainElement domainElement) {
		return ruleMeaning.getValueAt(domainElement);
	}
}
