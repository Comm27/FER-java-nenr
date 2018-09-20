package hr.fer.zemris.fuzzy;

public abstract class Domain implements IDomain {

	public static IDomain intRange(int start, int end) {
		return new SimpleDomain(start, end);
	}
	
	public static IDomain combine(IDomain d1, IDomain d2) {
		SimpleDomain[] domains = new SimpleDomain[d1.getNumberOfComponents() + d2.getNumberOfComponents()];
		
		for (int i = 0; i < d1.getNumberOfComponents(); i++) {
			domains[i] = (SimpleDomain) d1.getComponent(i);
		}
		
		for (int i = 0; i < d2.getNumberOfComponents(); i++) {
			domains[i + d1.getNumberOfComponents()] = (SimpleDomain) d2.getComponent(i);
		}
		
		return new CompositeDomain(domains);
	}
}
