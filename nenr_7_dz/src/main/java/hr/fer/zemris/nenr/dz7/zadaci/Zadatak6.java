package hr.fer.zemris.nenr.dz7.zadaci;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;

import hr.fer.zemris.nenr.dz7.genetic.GenerationElitisticGA;
import hr.fer.zemris.nenr.dz7.genetic.ICrossoverOperator;
import hr.fer.zemris.nenr.dz7.genetic.IMutationOperator;
import hr.fer.zemris.nenr.dz7.genetic.IOptAlgorithm;
import hr.fer.zemris.nenr.dz7.genetic.TournamentSelOp;
import hr.fer.zemris.nenr.dz7.genetic.impl.ArithmeticCrossover;
import hr.fer.zemris.nenr.dz7.genetic.impl.BLXAlphaCrossover;
import hr.fer.zemris.nenr.dz7.genetic.impl.Dataset;
import hr.fer.zemris.nenr.dz7.genetic.impl.DoubleArraySolution;
import hr.fer.zemris.nenr.dz7.genetic.impl.MutationAdd;
import hr.fer.zemris.nenr.dz7.genetic.impl.MutationReplace;
import hr.fer.zemris.nenr.dz7.genetic.impl.Problem;
import hr.fer.zemris.nenr.dz7.genetic.impl.Sample;
import hr.fer.zemris.nenr.dz7.genetic.impl.SimpleCrossover;
import hr.fer.zemris.nenr.dz7.genetic.impl.Util;
import hr.fer.zemris.nenr.dz7.nn.ANN;
import hr.fer.zemris.nenr.dz7.nn.IActivationFunction;
import hr.fer.zemris.nenr.dz7.nn.SigmoidFunction;

public class Zadatak6 {

	public static void main(String[] args) {
		
		Dataset dataset = Util.loadDataset("zad7-dataset.txt");
		if (dataset == null) {
			System.out.println("Greška pri učitavanju dataseta.");
			return;
		}
		
		ANN ann = new ANN(
				new int[]{ 2, 6, 4, 3 },
				new IActivationFunction[]{ new SigmoidFunction(), new SigmoidFunction() });
		ann.fullyConnectFeedforward();
		
		IOptAlgorithm<double[]> alg =
				new GenerationElitisticGA<double[]>(
						20,
						new TournamentSelOp<>(4, true),
						new ICrossoverOperator[]{
								new BLXAlphaCrossover(0.5),
								new ArithmeticCrossover(),
								new SimpleCrossover(0.1)},
						new IMutationOperator[]{
								new MutationAdd(0.05, 0.05),
								new MutationReplace(1d, 0.1)
						},
						0.9,
						new Problem(
								ann,
								dataset),
						-0.00000000000000000001,
						350000);
		
		double[] weights = alg.run().getRepresentation();
		
		ann.setWeights(weights);
		
		printStats(ann, dataset);
		
		writeWeightsToFiles(weights);
	}

	private static void writeWeightsToFiles(double[] weights) {
		try (BufferedWriter bwx = Files.newBufferedWriter(Paths.get("zad_6_coord.txt"));
				BufferedWriter bwy = Files.newBufferedWriter(Paths.get("zad_6_s.txt"))) {
			
			int counter = 0;
			
			for (int i = 0; i < 32; i++) {
				if (counter == 0) {
					bwx.write(new Double(weights[i]).toString());
				} else if (counter == 1) {
					bwy.write(new Double(weights[i]).toString());
				} else if (counter == 2) {
					bwx.write(" " + new Double(weights[i]).toString() + System.lineSeparator());
				} else if (counter == 3) {
					bwy.write(" " + new Double(weights[i]).toString() + System.lineSeparator());
				}
				
				counter++;
				if (counter > 3) {
					counter = 0;
				}
			}
			
		} catch (IOException e) {
			System.out.println("Ne mogu zapisati u datoteku.");
			return;
		}
	}
	
	private static void printStats(ANN ann, Dataset dataset) {
		DecimalFormat df = new DecimalFormat("##.###");
		int errors = 0;
		int corrects = 0;
		
		for (Sample sample : dataset) {
			double[] output = ann.calculateOutput(sample.getInput());
			int[] predicted = getPredicted(output);
			String correct;
			
			
			if (Arrays.equals(predicted, sample.getCode())) {
				corrects += 1;
				correct = "Y";
			} else {
				correct = "N";
				errors += 1;
			}
			
			
			System.out.println("Correct? " + correct + " $$$ " +
								"Real -> Predicted: " + sample.getI1() + ":" + sample.getI2() + ":" + sample.getI3() +
								" -> " + predicted[0] + ":" + predicted[1] + ":" + predicted[2] +
								" (" + df.format(output[0]) + ", " + df.format(output[1]) + ", " + df.format(output[2]) + ")");
		}
		
		System.out.println("#####################");
		System.out.println("Broj točno klasificiranih: " + corrects + ", broj pogrešno klasificiranih: " + errors);
	}

	private static int[] getPredicted(double[] output) {
		int[] predicted = new int[output.length];
		
		for (int i = 0; i < output.length; i++) {
			predicted[i] = output[i] > 0.5 ? 1 : 0;
		}
		
		return predicted;
	}
}
