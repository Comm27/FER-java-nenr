package hr.fer.zemris.nenr.dz5.gui;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.nenr.dz5.neuralnetwork.learning.IDataset;
import hr.fer.zemris.nenr.dz5.neuralnetwork.learning.LearningSample;

public class DrawingDataset implements IDataset {

	private List<LetterData> letterDataList;
	private int letterBatchSize;
	
	public DrawingDataset(List<LetterData> letterData, int letterBatchSize) {
		this.letterDataList = letterData;
		this.letterBatchSize = letterBatchSize;
	}

	@Override
	public List<List<LearningSample>> getBatchGroups() {
		if (letterBatchSize == 1) {
			return stohasticBatch();
		}
		
		return groupBatch();
	}
	
	private List<List<LearningSample>> groupBatch() {
		List<List<LearningSample>> batchGroups = new ArrayList<>();
		
		int index = 0;
		boolean finished = false;
		
		while (true) {
			List<LearningSample> learningSamples = new ArrayList<>();
			
			int currentIndex = 0;
			while (currentIndex < letterBatchSize / letterDataList.size()) {
				if (currentIndex + index >= letterDataList.get(0).getLetterRepresentations().size()) {
					finished = true;
					break;
				}
				
				for (LetterData letterData : letterDataList) {
					double[][] matrix = letterData.getLetterRepresentations().get(currentIndex + index);
					learningSamples.add(new LearningSample(Util.transform(matrix), letterData.getLetterCode()));
				}
				
				currentIndex++;
			}
			
			if (!learningSamples.isEmpty()) {
				batchGroups.add(learningSamples);
			}
			
			index += currentIndex;
			
			if (finished) {
				return batchGroups;
			}
		}
	}

	private List<List<LearningSample>> stohasticBatch() {
		List<List<LearningSample>> batchGroups = new ArrayList<>();
		
		for (LetterData letterData : letterDataList) {
			for (double[][] matrix : letterData.getLetterRepresentations()) {
				List<LearningSample> learningSamples = new ArrayList<>();
				learningSamples.add(new LearningSample(Util.transform(matrix), letterData.getLetterCode()));
				batchGroups.add(learningSamples);
			}
		}
		
		return batchGroups;
	}

	@Override
	public int numberOfSamples() {
		return letterDataList.size() * letterDataList.get(0).getLetterRepresentations().size();
	}	
}