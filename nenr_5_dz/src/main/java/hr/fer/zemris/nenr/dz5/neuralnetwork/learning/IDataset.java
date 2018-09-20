package hr.fer.zemris.nenr.dz5.neuralnetwork.learning;

import java.util.List;

public interface IDataset {

	public List<List<LearningSample>> getBatchGroups();
	public int numberOfSamples();
}
