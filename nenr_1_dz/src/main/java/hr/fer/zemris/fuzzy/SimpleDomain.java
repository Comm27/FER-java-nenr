package hr.fer.zemris.fuzzy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleDomain extends Domain {

	private int first;
	private int last;
	
	public SimpleDomain(int first, int last) {
		if (!(first < last)) {
			throw new IllegalArgumentException("Domain bounds not valid; were: f: " + first + ", l: " + last);
		}
		
		this.first = first;
		this.last = last;
	}
	
	@Override
	public DomainElement elementForIndex(int index) {
		if (index < 0 || index >= getCardinality()) {
			throw new IndexOutOfBoundsException();
		}
		
		return DomainElement.of(first + index);
	}
	
	@Override
	public int indexOfElement(DomainElement domainElement) {
		if (domainElement.getNumberOfComponents() != 1) {
			throw new NoSuchElementException();
		}
		
		int element = domainElement.getComponentValue(0);
		if (element < first || element >= last) {
			throw new NoSuchElementException();
		}
		
		return element - first;
	}

	@Override
	public int getCardinality() {
		return last - first;
	}

	@Override
	public IDomain getComponent(int index) {
		if (index != 0) {
			throw new IndexOutOfBoundsException();
		}
		
		return this;
	}

	@Override
	public int getNumberOfComponents() {
		return 1;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new SimpleDomainIterator();
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getLast() {
		return last;
	}

	private class SimpleDomainIterator implements Iterator<DomainElement> {
		
		private int currentValue;
		
		public SimpleDomainIterator() {
			currentValue = first;
		}

		@Override
		public boolean hasNext() {
			if (currentValue < last) {
				return true;
			}
			
			return false;
		}

		@Override
		public DomainElement next() {
			currentValue++;
			return new DomainElement(new int[]{ currentValue - 1 });
		}
	}
}
