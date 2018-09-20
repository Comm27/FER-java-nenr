package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {

	private IDomain domain;
	private IIntUnaryFunction unaryFunction;
	
	public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction unaryFunction) {
		this.domain = domain;
		this.unaryFunction = unaryFunction;
	}

	@Override
	public IDomain getDomain() {
		return domain;
	}

	@Override
	public double getValueAt(DomainElement domainElement) {
		return unaryFunction.valueAt(domain.indexOfElement(domainElement));
	}
}
