package hr.fer.zemris.fuzzy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositeDomain extends Domain {

	private SimpleDomain[] components;
	
	public CompositeDomain(SimpleDomain[] components) {
		this.components = components;
	}
	
	@Override
	public DomainElement elementForIndex(int index) {
		return elementForIndex(components, index);
	}
	
	private static DomainElement elementForIndex(SimpleDomain[] components, int index) {
		if (index >= claculateCardinality(components)) {
			throw new IndexOutOfBoundsException();
		}
		
		int[] values = new int[components.length];
		
		for (int i = 0; i < values.length; i++) {
			
			int smallerCompsCardinality = 1;
			for (int j = i + 1; j < values.length; j++) {
				smallerCompsCardinality *= components[j].getCardinality();
			}
			
			int indexInCurrentDomain = index / smallerCompsCardinality % components[i].getCardinality();
			values[i] = components[i].elementForIndex(indexInCurrentDomain).getComponentValue(0);
		}
		
		return new DomainElement(values);
	}
	
	@Override
	public int indexOfElement(DomainElement domainElement) {
		if (domainElement.getNumberOfComponents() != components.length) {
			throw new NoSuchElementException();
		}
		
		int[] indexes = new int[components.length];
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = components[i].indexOfElement(DomainElement.of(domainElement.getComponentValue(i)));
		}
		
		int index = 0;
		
		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] == 0) {
				continue;
			}
			
			int smallerCompsCard = 1;
			for (int j = i + 1; j < indexes.length; j++) {
				smallerCompsCard *= components[j].getCardinality();
			}
			
			if (i == indexes.length - 1) {
				index += indexes[i];
			} else {
				index += (indexes[i]) * smallerCompsCard;
			}
		}
		
		return index;
	}

	@Override
	public int getCardinality() {
		return claculateCardinality(components);
	}
	
	private static int claculateCardinality(SimpleDomain[] components) {
		if (components.length == 0) {
			return 0;
		}
		
		int cardinality = 1;
		
		for (int i = 0; i < components.length; i++) {
			cardinality *= components[i].getCardinality();
		}
		
		return cardinality;
	}

	@Override
	public IDomain getComponent(int index) {
		if (index < 0 || index >= components.length) {
			throw new IndexOutOfBoundsException();
		}
		
		return components[index];
	}

	@Override
	public int getNumberOfComponents() {
		return components.length;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new CompositeDomainIterator(components);
	}
	
	private static class CompositeDomainIterator implements Iterator<DomainElement> {
		
		private int index;
		private int cardinality;
		private SimpleDomain[] components;

		public CompositeDomainIterator(SimpleDomain[] components) {
			this.index = 0;
			cardinality = CompositeDomain.claculateCardinality(components);
			this.components = components;
		}

		@Override
		public boolean hasNext() {
			if (index < cardinality) {
				return true;
			}
			
			return false;
		}

		@Override
		public DomainElement next() {
			index++;
			return CompositeDomain.elementForIndex(components, index - 1);
		}
	}
}
