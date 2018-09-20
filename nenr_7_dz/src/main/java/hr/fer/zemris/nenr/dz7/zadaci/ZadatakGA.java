package hr.fer.zemris.nenr.dz7.zadaci;

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

public class ZadatakGA {

	public static void main(String[] args) {
		
		Dataset dataset = Util.loadDataset("zad7-dataset.txt");
		if (dataset == null) {
			System.out.println("Greška pri učitavanju dataseta.");
			return;
		}
		
		ANN ann = new ANN(
				new int[]{ 2, 8, 3 },
				new IActivationFunction[]{ new SigmoidFunction() });
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
						-0.00001,
						200000);
		
		double[] weights = alg.run().getRepresentation();
		ann.setWeights(weights);
		
		printStats(ann, dataset);
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
