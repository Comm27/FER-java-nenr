package hr.fer.zemris.fuzzy;

public class Primjer4 {

	public static void main(String[] args) {
		
		IDomain d1 = new SimpleDomain(1, 4);
		IDomain d2 = new SimpleDomain(1, 5);
		IDomain d3 = new SimpleDomain(1, 3);
		IDomain d4 = new SimpleDomain(1, 2);
		
		IFuzzySet a = new MutableFuzzySet(Domain.combine(d1, d2))
				.set(DomainElement.of(1, 1), 0.1)
				.set(DomainElement.of(1, 2), 0.7)
				.set(DomainElement.of(1, 3), 0.5)
				.set(DomainElement.of(1, 4), 0.1)
				.set(DomainElement.of(2, 1), 0.5)
				.set(DomainElement.of(2, 2), 1.0)
				.set(DomainElement.of(2, 3), 0.9)
				.set(DomainElement.of(2, 4), 0.4)
				.set(DomainElement.of(3, 1), 0.2)
				.set(DomainElement.of(3, 2), 0.1)
				.set(DomainElement.of(3, 3), 0.6)
				.set(DomainElement.of(3, 4), 0.9);
		
		IFuzzySet b = new MutableFuzzySet(Domain.combine(d2, d3))
				.set(DomainElement.of(1, 1), 1.0)
				.set(DomainElement.of(1, 2), 0.2)
				.set(DomainElement.of(2, 1), 0.7)
				.set(DomainElement.of(2, 2), 0.5)
				.set(DomainElement.of(3, 1), 0.3)
				.set(DomainElement.of(3, 2), 0.9)
				.set(DomainElement.of(4, 1), 0.0)
				.set(DomainElement.of(4, 2), 0.4);
		
		IFuzzySet c = new MutableFuzzySet(Domain.combine(d4, d2))
				.set(DomainElement.of(1, 1), 0.7)
				.set(DomainElement.of(1, 2), 0.9)
				.set(DomainElement.of(1, 3), 1.0)
				.set(DomainElement.of(1, 4), 0.3);
		
		IFuzzySet AoB = Relations.compositionOfBinaryRelations(a, b);
		for (DomainElement de : AoB.getDomain()) {
			System.out.println(de + " -> " + AoB.getValueAt(de));
		}
		
		System.out.println(":::::::::::::::::::::::::::::");
		
		IFuzzySet CoB = Relations.compositionOfBinaryRelations(c, b);
		for (DomainElement de : CoB.getDomain()) {
			System.out.println(de + " -> " + CoB.getValueAt(de));
		}
		
		
	}
}
