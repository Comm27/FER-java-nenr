package hr.fer.zemris.fuzzy;

public interface IDomain extends Iterable<DomainElement> {

	public int getCardinality();
	public IDomain getComponent(int index);
	public int getNumberOfComponents();
	public int indexOfElement(DomainElement domainElement);
	public DomainElement elementForIndex(int index);
}
