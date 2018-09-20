package hr.fer.zemris.fuzzy.ship;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.system.IDefuzzyfier;

public class COADefuzzyfier implements IDefuzzyfier {

	@Override
	public int defuzzyfie(IFuzzySet fuzzySet) {
		IDomain domain = fuzzySet.getDomain();
		
		double upperSum = 0;
		double lowerSum = 0;
		
		for (DomainElement domainElement : domain) {
			upperSum += domainElement.getComponentValue(0) * fuzzySet.getValueAt(domainElement);
			lowerSum += fuzzySet.getValueAt(domainElement);
		}
		
		return (int) (upperSum / lowerSum);
	}	
}
