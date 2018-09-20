package hr.fer.zemris.nenr.dz6.anfis;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

	private static final int MIN = -4;
	private static final int MAX = 4;
	
	private List<List<Sample>> batchGroups;
	private int size;
	
	public Dataset(boolean batch) {
		formBatchGroups(getSamples(), batch);
		initSize();
	}

	private List<Sample> getSamples() {
		List<Sample> samples = new ArrayList<>();
		
		for (int i = MIN; i <= MAX; i++) {
			for (int j = MIN; j <= MAX; j++) {
				samples.add(new Sample((double) i, (double) j, Function.valueAt((double) i, (double) j)));
			}
		}
		
		return samples;
	}
	
	private void formBatchGroups(List<Sample> samples, boolean batch) {
		batchGroups = new ArrayList<>();
		
		if (batch) {
			batchGroups.add(samples);
			return;
		}
		
		for (Sample sample : samples) {
			List<Sample> singleSampleGroup = new ArrayList<>();
			singleSampleGroup.add(sample);
			batchGroups.add(singleSampleGroup);
		}
	}
	
	private void initSize() {
		int size = 0;
		
		for (List<Sample> list : batchGroups) {
			for (@SuppressWarnings("unused") Sample sample : list) {
				size++;
			}
		}
		
		this.size = size;
	}
	
	public List<List<Sample>> getBatchGroups() {
		return batchGroups;
	}
	
	public int getSize() {
		return size;
	}
}
