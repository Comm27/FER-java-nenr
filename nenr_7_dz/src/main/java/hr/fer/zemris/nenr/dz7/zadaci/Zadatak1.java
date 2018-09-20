package hr.fer.zemris.nenr.dz7.zadaci;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.nenr.dz7.nn.ANN;

public class Zadatak1 {

	public static void main(String[] args) {
		ANN ann = new ANN(new int[]{1, 1}, null);
		ann.fullyConnectFeedforward();
		
		setParamS(ann, 0.25);
		writeData(ann, "zad_1_s025.txt");
		
		setParamS(ann, 4d);
		writeData(ann, "zad_1_s4.txt");
		
		setParamS(ann, 1d);
		writeData(ann, "zad_1_s1.txt");
	}

	private static void setParamS(ANN ann, double s) {
		double[] weights = ann.getWeights();
		weights[0] = 2;
		weights[1] = s;
		ann.setWeights(weights);
	}
	
	private static void writeData(ANN ann, String file) {
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(file))) {
			
			for (double x = -8d; x <= 10d; x += 0.2) {
				double value = ann.calculateOutput(new double[]{ x })[0];
				bw.write(x + " " + value + System.lineSeparator());
			}
			
		} catch (IOException e) {
			System.out.println("Ne mogu zapisati u file.");
			return;
		}
	}
}
