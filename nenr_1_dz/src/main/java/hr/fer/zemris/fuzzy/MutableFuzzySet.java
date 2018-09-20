package hr.fer.zemris.fuzzy;

public class MutableFuzzySet implements IFuzzySet {

	private IDomain domain;
	private double[] memberships;
	
	public MutableFuzzySet(IDomain domain) {
		this.domain = domain;
		memberships = new double[domain.getCardinality()];
	}

	@Override
	public IDomain getDomain() {
		return domain;
	}

	@Override
	public double getValueAt(DomainElement domainElement) {
		return memberships[domain.indexOfElement(domainElement)];
	}
	
	public MutableFuzzySet set(DomainElement domainElement, double value) {
		memberships[domain.indexOfElement(domainElement)] = value;
		return this;
	}
}
