package hr.fer.zemris.nenr.dz7.genetic.impl;

import java.util.Iterator;
import java.util.List;

public class Dataset implements Iterable<Sample> {

	private List<Sample> samples;
	
	public Dataset(List<Sample> samples) {
		this.samples = samples;
	}

	public int size() {
		return samples.size();
	}
	
	@Override
	public Iterator<Sample> iterator() {
		return new SampleIterator();
	}
	
	private class SampleIterator implements Iterator<Sample> {
		
		private int index;
		
		public SampleIterator() {
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index < samples.size();
		}
		
		@Override
		public Sample next() {
			index++;
			return samples.get(index - 1);
		}
	}
}
