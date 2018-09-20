package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.List;

public class Relations {
	
	private static final double DE_DIFF_THRESHOLD = 1E-10;

	public static boolean isUtimesURelation(IFuzzySet relation) {
		IDomain domain = relation.getDomain();
		
		if (domain.getNumberOfComponents() != 2) {
			return false;
		}
		
		IDomain d1 = domain.getComponent(0);
		IDomain d2 = domain.getComponent(1);
		
		if (d1.getCardinality() != d2.getCardinality()) {
			return false;
		}
		
		for (int i = 0, size = d1.getCardinality(); i < size; i++) {
			if (!d1.elementForIndex(i).equals(d2.elementForIndex(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isSymmetric(IFuzzySet relation) {
		if (!isUtimesURelation(relation)) {
			return false;
		}
		
		IDomain domain = relation.getDomain();
		
		for (int i = 0, size = domain.getComponent(0).getCardinality(); i < size; i++) {
			for (int j = 0; j < size; j++) {
				DomainElement el1 = domain.elementForIndex(i * size + j);
				DomainElement el2 = domain.elementForIndex(j * size + i);
				
				if (Math.abs(relation.getValueAt(el1) - relation.getValueAt(el2)) > DE_DIFF_THRESHOLD) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static boolean isReflexive(IFuzzySet relation) {
		if (!isUtimesURelation(relation)) {
			return false;
		}
		
		IDomain domain = relation.getDomain();
		
		for (int i = 0, size = domain.getComponent(0).getCardinality(); i < size; i++) {
			DomainElement el = domain.elementForIndex(i * size + i);
			
			if (Math.abs(1 - relation.getValueAt(el)) > DE_DIFF_THRESHOLD) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isMaxMinTransitive(IFuzzySet relation) {
		if (!isUtimesURelation(relation)) {
			return false;
		}
		
		IDomain domain = relation.getDomain();
		
		for (int i = 0, size = domain.getComponent(0).getCardinality(); i < size; i++) {
			for (int j = 0; j < size; j++) {
				DomainElement el1 = domain.elementForIndex(i * size + j);
				List<Double> minimums = new ArrayList<>();
				
				for (int k = 0; k < size; k++) {
					DomainElement comp1 = domain.elementForIndex(i * size + k);
					DomainElement comp2 = domain.elementForIndex(k * size + j);
					
					minimums.add(Operations.zadehAnd().valueAt(relation.getValueAt(comp1), relation.getValueAt(comp2)));
				}
				
				minimums.sort((d1, d2) -> Double.compare(d2, d1));
				
				if (relation.getValueAt(el1) < minimums.get(0) &&
						Math.abs(relation.getValueAt(el1) - minimums.get(0)) > DE_DIFF_THRESHOLD) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {
		if (r1.getDomain().getNumberOfComponents() != 2 ||
				r2.getDomain().getNumberOfComponents() != 2) {
			throw new UnsupportedOperationException();
		}
		
		IDomain r1Domain = r1.getDomain();
		IDomain r1D1 = r1Domain.getComponent(0);
		IDomain r1D2 = r1Domain.getComponent(1);
		
		IDomain r2Domain = r2.getDomain();
		IDomain r2D1 = r2Domain.getComponent(0);
		IDomain r2D2 = r2Domain.getComponent(1);
		
		IFuzzySet middleDomainsCombined = new MutableFuzzySet(Domain.combine(r1D2, r2D1));
		if (!isUtimesURelation(middleDomainsCombined)) {
			throw new UnsupportedOperationException();
		}
		
		MutableFuzzySet resultRelation = new MutableFuzzySet(Domain.combine(r1D1, r2D2));
		
		for (int i = 0, outer = r2D2.getCardinality(); i < outer; i++) {
			for (int j = 0, middle = r1D1.getCardinality(); j < middle; j++) {
				List<Double> minimums = new ArrayList<>();
				
				for (int k = 0, inner = r1D2.getCardinality(); k < inner; k++) {
					DomainElement e1 = r1Domain.elementForIndex(j * inner + k);
					DomainElement e2 = r2Domain.elementForIndex(k * outer + i);
					
					double v1 = r1.getValueAt(e1);
					double v2 = r2.getValueAt(e2);
					
					minimums.add(v1 < v2 ? v1 : v2);
				}
				
				minimums.sort((o1, o2) -> Double.compare(o2, o1));
				DomainElement calculatedElement = resultRelation.getDomain().elementForIndex(j * outer + i);
				resultRelation.set(calculatedElement, minimums.get(0));
			}
		}
	
		return resultRelation;
	}
	
	public static boolean isFuzzyEquivalence(IFuzzySet relation) {
		if (isReflexive(relation) && isSymmetric(relation) && isMaxMinTransitive(relation)) {
			return true;
		}
		
		return false;
	}
}
