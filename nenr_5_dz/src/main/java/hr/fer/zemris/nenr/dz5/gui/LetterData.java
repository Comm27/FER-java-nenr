package hr.fer.zemris.nenr.dz5.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LetterData {
	
	private double[] letterCode;
	private List<double[][]> letterRepresentations;
	
	public LetterData(double[] letterCode) {
		this.letterCode = letterCode;
		letterRepresentations = new ArrayList<>();
	}
	
	public double[] getLetterCode() {
		return letterCode;
	}
	
	public List<double[][]> getLetterRepresentations() {
		return letterRepresentations;
	}
	
	public void addLetterRepresentation(double[][] letterRepresentation) {
		letterRepresentations.add(letterRepresentation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(letterCode);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LetterData other = (LetterData) obj;
		if (!Arrays.equals(letterCode, other.letterCode))
			return false;
		return true;
	}
}
